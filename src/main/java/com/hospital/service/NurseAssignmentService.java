package com.hospital.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.model.NurseAssignment;
import com.hospital.repository.NurseAssignmentRepository;

@Service
public class NurseAssignmentService {

    private final NurseAssignmentRepository assignmentRepository;

    public NurseAssignmentService(NurseAssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public NurseAssignment assignNurse(NurseAssignment assignment) {
        assignment.setActivityStatus("Assigned");
        return assignmentRepository.save(assignment);
    }

    public List<NurseAssignment> getAssignmentsByNurse(String nurseId) {
        return assignmentRepository.findByNurseId(nurseId);
    }

    public NurseAssignment updateStatus(Long assignmentId, String status) {
        NurseAssignment a = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));
        a.setActivityStatus(status);
        return assignmentRepository.save(a);
    }
}