package com.hospital.service;

import com.hospital.model.Nurse;
import com.hospital.repository.NurseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NurseService {

    private final NurseRepository nurseRepository;

    public NurseService(NurseRepository nurseRepository) {
        this.nurseRepository = nurseRepository;
    }

    public Nurse addNurse(Nurse nurse) {
        return nurseRepository.save(nurse);
    }

    public List<Nurse> getAllNurses() {
        return nurseRepository.findAll();
    }

    public List<Nurse> searchByName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            return nurseRepository.findByNameContainingIgnoreCase(name.trim());
        }
        return nurseRepository.findAll();
    }

    public void deleteNurse(Integer nurseId) {
        nurseRepository.deleteById(nurseId);
    }
}