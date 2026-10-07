package com.hospital.service;

import com.hospital.model.Doctor;
import com.hospital.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor addDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public List<Doctor> searchBySpecialist(String specialist) {
        if (specialist != null && !specialist.trim().isEmpty()) {
            return doctorRepository.findBySpecialistStartingWithIgnoreCase(specialist.trim());
        }
        return doctorRepository.findAll();
    }

    public void deleteDoctor(Integer doctorId) {
        doctorRepository.deleteById(doctorId);
    }
}