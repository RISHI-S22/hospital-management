package com.hospital.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hospital.model.Patient;
import com.hospital.model.User;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.UserRepository;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public PatientService(PatientRepository patientRepository, UserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Patient admitPatient(Patient patient) {
        // Auto-generate unique PAT-xxxx ID and temporary password
        int randomPin = 1000 + new Random().nextInt(9000);
        String generatedId = "PAT-" + randomPin;
        String generatedPass = "pass" + randomPin;

        patient.setPatientId(generatedId);
        patient.setPassword(generatedPass);

        // 1. Persist to addpatient table
        Patient savedPatient = patientRepository.save(patient);

        // 2. Persist to register authentication ledger
        String safeEmail = (patient.getPatientname() != null ? 
                patient.getPatientname().replaceAll("\\s+", "").toLowerCase() : "patient") 
                + randomPin + "@hospital.com";

        User authUser = new User(
                generatedId,
                safeEmail,
                generatedPass,
                "PATIENT",
                patient.getPatientname(),
                patient.getContact(),
                patient.getAddress()
        );
        userRepository.save(authUser);

        return savedPatient;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(String patientId) {
        return patientRepository.findById(patientId);
    }
}