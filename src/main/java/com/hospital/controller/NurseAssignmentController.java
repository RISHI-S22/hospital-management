package com.hospital.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.model.NurseAssignment;
import com.hospital.service.NurseAssignmentService;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = "*")
public class NurseAssignmentController {

    private final NurseAssignmentService assignmentService;

    public NurseAssignmentController(NurseAssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping
    public ResponseEntity<NurseAssignment> assignNurse(@RequestBody NurseAssignment assignment) {
        return ResponseEntity.ok(assignmentService.assignNurse(assignment));
    }

    @GetMapping("/nurse/{nurseId}")
    public ResponseEntity<List<NurseAssignment>> getForNurse(@PathVariable String nurseId) {
        return ResponseEntity.ok(assignmentService.getAssignmentsByNurse(nurseId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<NurseAssignment> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        return ResponseEntity.ok(assignmentService.updateStatus(id, payload.get("status")));
    }
}