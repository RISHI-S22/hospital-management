package com.hospital.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.hospital.repository.NurseAssignmentRepository;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = "*")
public class NurseAssignmentController {

    private final NurseAssignmentRepository assignmentRepository;

    public NurseAssignmentController(NurseAssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @PostMapping
    public ResponseEntity<NurseAssignment> createAssignment(@RequestBody NurseAssignment assignment) {
        if (assignment.getActivityStatus() == null || assignment.getActivityStatus().isBlank()) {
            assignment.setActivityStatus("Assigned");
        }
        return ResponseEntity.ok(assignmentRepository.save(assignment));
    }

   @GetMapping("/nurse/{nurseId}")
    public ResponseEntity<List<NurseAssignment>> getAssignmentsByNurse(@PathVariable String nurseId) {
        String queryId = nurseId.trim().toUpperCase();
        String numericId = queryId.replace("NUR-", "").replace("NUR", "").trim();

        // Calculate mapped legacy ID: 201 <-> 1
        int parsed = -1;
        try {
            parsed = Integer.parseInt(numericId);
        } catch (NumberFormatException ignored) {}

        final int num = parsed;
        final String alternateNumeric = (num >= 201 && num <= 212) ? String.valueOf(num - 200)
                                      : (num >= 1 && num <= 12)    ? String.valueOf(num + 200)
                                      : numericId;
        final String alternatePrefixed = "NUR-" + alternateNumeric;

        List<NurseAssignment> all = assignmentRepository.findAll();
        List<NurseAssignment> matches = all.stream().filter(a -> {
            if (a.getNurseId() == null) return false;
            String assigned = a.getNurseId().trim().toUpperCase();
            String assignedNum = assigned.replace("NUR-", "").replace("NUR", "").trim();

            return assigned.equalsIgnoreCase(queryId)
                || assigned.equalsIgnoreCase(alternatePrefixed)
                || assignedNum.equalsIgnoreCase(numericId)
                || assignedNum.equalsIgnoreCase(alternateNumeric);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(matches);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        return assignmentRepository.findById(id).map(assignment -> {
            assignment.setActivityStatus(payload.getOrDefault("status", "Completed"));
            assignmentRepository.save(assignment);
            return ResponseEntity.ok(assignment);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<NurseAssignment>> getAll() {
        return ResponseEntity.ok(assignmentRepository.findAll());
    }
}