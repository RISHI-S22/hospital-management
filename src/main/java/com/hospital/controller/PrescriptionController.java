

package com.hospital.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
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
import com.hospital.model.Prescription;
import com.hospital.repository.MedicineRepository;
import com.hospital.repository.PharmacyBillRepository;
import com.hospital.repository.PrescriptionRepository;

@RestController
@RequestMapping("/api/prescriptions")
@CrossOrigin(origins = "*")
public class PrescriptionController {

    private final PrescriptionRepository prescriptionRepository;
    private final PharmacyBillRepository pharmacyBillRepository;
    private final MedicineRepository medicineRepository;

    public PrescriptionController(PrescriptionRepository prescriptionRepository,
                                  PharmacyBillRepository pharmacyBillRepository,
                                  MedicineRepository medicineRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.pharmacyBillRepository = pharmacyBillRepository;
        this.medicineRepository = medicineRepository;
    }

    @PostMapping
    public ResponseEntity<?> createPrescription(@RequestBody Prescription prescription) {
        try {
            if (prescription.getDate() == null || prescription.getDate().isBlank()) {
                prescription.setDate(LocalDate.now().toString());
            }
            if (prescription.getStatus() == null || prescription.getStatus().isBlank()) {
                prescription.setStatus("Pending");
            }
            Prescription saved = prescriptionRepository.save(prescription);
            queueToPharmacyBill(saved);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving prescription: " + e.getMessage());
        }
    }

    @PostMapping("/batch")
    public ResponseEntity<?> createBatchPrescriptions(@RequestBody List<Prescription> prescriptions) {
        try {
            if (prescriptions == null || prescriptions.isEmpty()) {
                return ResponseEntity.badRequest().body("No prescription items provided");
            }

            String today = LocalDate.now().toString();
            List<Prescription> savedList = new ArrayList<>();

            for (Prescription p : prescriptions) {
                if (p.getDate() == null || p.getDate().isBlank()) {
                    p.setDate(today);
                }
                if (p.getStatus() == null || p.getStatus().isBlank()) {
                    p.setStatus("Pending");
                }
                Prescription saved = prescriptionRepository.save(p);
                queueToPharmacyBill(saved);
                savedList.add(saved);
            }

            return ResponseEntity.ok(savedList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving batch prescriptions: " + e.getMessage());
        }
    }

    private void queueToPharmacyBill(Prescription saved) {
        try {
            if (pharmacyBillRepository != null) {
                PharmacyBill bill = new PharmacyBill();
                bill.setPatientId(saved.getPatientId());
                bill.setPatientName(saved.getPatientName());
                bill.setItemSource("DOCTOR_PRESCRIPTION (" + (saved.getDoctorName() != null ? saved.getDoctorName() : "Doctor") + ")");
                bill.setMedicineName(saved.getMedicineName());
                bill.setQuantity(1);

                double price = 15.00;
                if (medicineRepository != null && saved.getMedicineName() != null) {
                    Optional<Medicine> medOpt = medicineRepository.findByMedicineName(saved.getMedicineName());
                    if (medOpt.isPresent()) {
                        price = medOpt.get().getUnitPrice();
                    }
                }

                bill.setUnitPrice(price);
                bill.setTotalAmount(price);
                bill.setPaymentStatus("PENDING");
                bill.setPaymentMode("UNPAID");
                bill.setDate(LocalDate.now().toString());
                pharmacyBillRepository.save(bill);
            }
        } catch (Exception ex) {
            System.err.println("Warning: Could not queue pharmacy bill item: " + ex.getMessage());
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Prescription>> getByPatient(@PathVariable String patientId) {
        return ResponseEntity.ok(prescriptionRepository.findByPatientId(patientId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Prescription>> getByDoctor(@PathVariable String doctorId) {
        return ResponseEntity.ok(prescriptionRepository.findByDoctorId(doctorId));
    }

    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        return ResponseEntity.ok(prescriptionRepository.findAll());
    }

    @PatchMapping("/{id}/dispense")
    public ResponseEntity<?> dispensePrescription(@PathVariable Long id) {
        return prescriptionRepository.findById(id).map(rx -> {
            rx.setStatus("Dispensed");
            prescriptionRepository.save(rx);
            return ResponseEntity.ok(rx);
        }).orElse(ResponseEntity.notFound().build());
    }
}