package com.hospital.service;

import com.hospital.model.Pharmacist;
import com.hospital.repository.PharmacistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PharmacistService {

    private final PharmacistRepository pharmacistRepository;

    public PharmacistService(PharmacistRepository pharmacistRepository) {
        this.pharmacistRepository = pharmacistRepository;
    }

    public Pharmacist addPharmacist(Pharmacist pharmacist) {
        return pharmacistRepository.save(pharmacist);
    }

    public List<Pharmacist> getAllPharmacists() {
        return pharmacistRepository.findAll();
    }

    public List<Pharmacist> searchByName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            return pharmacistRepository.findByNameContainingIgnoreCase(name.trim());
        }
        return pharmacistRepository.findAll();
    }

    public void deletePharmacist(Integer pharmacistId) {
        pharmacistRepository.deleteById(pharmacistId);
    }
}