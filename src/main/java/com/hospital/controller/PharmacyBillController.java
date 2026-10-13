package com.hospital.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.model.Medicine;
import com.hospital.model.PharmacyBill;
import com.hospital.repository.MedicineRepository;
import com.hospital.repository.PharmacyBillRepository;

@RestController
@RequestMapping("/api/pharmacy-bills")
@CrossOrigin(origins = "*")
public class PharmacyBillController {

    private final PharmacyBillRepository billRepository;
    private final MedicineRepository medicineRepository;

    public PharmacyBillController(PharmacyBillRepository billRepository, MedicineRepository medicineRepository) {
        this.billRepository = billRepository;
        this.medicineRepository = medicineRepository;
    }

    private Optional<Medicine> findMedicineByName(String medicineName) {
        if (medicineName == null || medicineName.isBlank()) {
            return Optional.empty();
        }

        return medicineRepository.findAll()
                .stream()
                .filter(m -> m.getMedicineName() != null && m.getMedicineName().equalsIgnoreCase(medicineName))
                .findFirst();
    }

    @PostMapping("/queue")
    public ResponseEntity<PharmacyBill> queueItem(@RequestBody PharmacyBill bill) {
        bill.setDate(LocalDate.now().toString());
        if (bill.getPaymentStatus() == null || bill.getPaymentStatus().isBlank()) {
            bill.setPaymentStatus("PENDING");
        }
        if (bill.getPaymentMode() == null || bill.getPaymentMode().isBlank()) {
            bill.setPaymentMode("UNPAID");
        }

        // Look up unit price from medicine inventory
        Optional<Medicine> medOpt = findMedicineByName(bill.getMedicineName());
        double price = medOpt.map(Medicine::getUnitPrice).orElse(10.00);
        bill.setUnitPrice(price);
        bill.setTotalAmount(price * (bill.getQuantity() <= 0 ? 1 : bill.getQuantity()));

        return ResponseEntity.ok(billRepository.save(bill));
    }

    @GetMapping
    public ResponseEntity<List<PharmacyBill>> getAllBills() {
        return ResponseEntity.ok(billRepository.findAll());
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<PharmacyBill>> getBillsByPatient(@PathVariable String patientId) {
        return ResponseEntity.ok(billRepository.findByPatientId(patientId));
    }

    @PatchMapping("/{id}/process-payment")
    public ResponseEntity<?> processPayment(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        return billRepository.findById(id).map(bill -> {
            String mode = payload.getOrDefault("paymentMode", "CASH");
            bill.setPaymentStatus("PAID");
            bill.setPaymentMode(mode);

            // Deduct inventory when payment completes
            Optional<Medicine> medOpt = findMedicineByName(bill.getMedicineName());
            medOpt.ifPresent(med -> {
                int newQty = Math.max(0, med.getStockQuantity() - bill.getQuantity());
                med.setStockQuantity(newQty);
                medicineRepository.save(med);
            });

            billRepository.save(bill);
            return ResponseEntity.ok(bill);
        }).orElse(ResponseEntity.notFound().build());
    }
}