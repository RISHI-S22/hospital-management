package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.model.NurseAssignment;

public interface NurseAssignmentRepository extends JpaRepository<NurseAssignment, Long> {
    List<NurseAssignment> findByNurseId(String nurseId);
    List<NurseAssignment> findByPatientId(String patientId);
    List<NurseAssignment> findByDoctorId(String doctorId);
}