package com.hospital.repository;

import com.hospital.model.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NurseRepository extends JpaRepository<Nurse, Integer> {
    List<Nurse> findByNameContainingIgnoreCase(String name);
}