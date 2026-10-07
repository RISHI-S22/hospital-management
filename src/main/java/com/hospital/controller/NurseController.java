package com.hospital.controller;

import com.hospital.model.Nurse;
import com.hospital.service.NurseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nurses")
@CrossOrigin(origins = "*")
public class NurseController {

    private final NurseService nurseService;

    public NurseController(NurseService nurseService) {
        this.nurseService = nurseService;
    }

    @PostMapping
    public ResponseEntity<Nurse> addNurse(@RequestBody Nurse nurse) {
        return ResponseEntity.ok(nurseService.addNurse(nurse));
    }

    @GetMapping
    public ResponseEntity<List<Nurse>> getNurses(@RequestParam(required = false) String name) {
        if (name != null && !name.trim().isEmpty()) {
            return ResponseEntity.ok(nurseService.searchByName(name));
        }
        return ResponseEntity.ok(nurseService.getAllNurses());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNurse(@PathVariable Integer id) {
        nurseService.deleteNurse(id);
        return ResponseEntity.ok("Nurse deleted successfully");
    }
}