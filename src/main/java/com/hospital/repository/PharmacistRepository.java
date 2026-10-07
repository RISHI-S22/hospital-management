package com.hospital.repository;

import com.hospital.model.Pharmacist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PharmacistRepository extends JpaRepository<Pharmacist, Integer> {
    List<Pharmacist> findByNameContainingIgnoreCase(String name);
}