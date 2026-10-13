package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospital.model.PharmacyBill;

@Repository
public interface PharmacyBillRepository extends JpaRepository<PharmacyBill, Long> {
    List<PharmacyBill> findByPatientId(String patientId);
    List<PharmacyBill> findByPaymentStatus(String paymentStatus);
}