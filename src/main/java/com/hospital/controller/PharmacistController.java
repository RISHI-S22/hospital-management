package com.hospital.controller;

import com.hospital.model.Pharmacist;
import com.hospital.service.PharmacistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacists")
@CrossOrigin(origins = "*")
public class PharmacistController {

    private final PharmacistService pharmacistService;

    public PharmacistController(PharmacistService pharmacistService) {
        this.pharmacistService = pharmacistService;
    }

    @PostMapping
    public ResponseEntity<Pharmacist> addPharmacist(@RequestBody Pharmacist pharmacist) {
        return ResponseEntity.ok(pharmacistService.addPharmacist(pharmacist));
    }

    @GetMapping
    public ResponseEntity<List<Pharmacist>> getPharmacists(@RequestParam(required = false) String name) {
        if (name != null && !name.trim().isEmpty()) {
            return ResponseEntity.ok(pharmacistService.searchByName(name));
        }
        return ResponseEntity.ok(pharmacistService.getAllPharmacists());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePharmacist(@PathVariable Integer id) {
        pharmacistService.deletePharmacist(id);
        return ResponseEntity.ok("Pharmacist deleted successfully");
    }
}