package com.hospital.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.model.Medicine;
import com.hospital.model.MedicineOrder;
import com.hospital.service.MedicineService;

@RestController
@RequestMapping("/api/medicines")
@CrossOrigin(origins = "*")
public class MedicineController {

    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @PostMapping
    public ResponseEntity<Medicine> saveMedicine(@RequestBody Medicine medicine) {
        return ResponseEntity.ok(medicineService.addOrUpdateMedicine(medicine));
    }

    @GetMapping
    public ResponseEntity<List<Medicine>> getInventory() {
        return ResponseEntity.ok(medicineService.getAllMedicines());
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchase(@RequestBody MedicineOrder order) {
        try {
            return ResponseEntity.ok(medicineService.purchaseMedicine(order));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/orders/{patientId}")
    public ResponseEntity<List<MedicineOrder>> getPatientOrders(@PathVariable String patientId) {
        return ResponseEntity.ok(medicineService.getPatientOrders(patientId));
    }
}