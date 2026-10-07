package com.hospital.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.model.Medicine;
import com.hospital.model.MedicineOrder;
import com.hospital.repository.MedicineOrderRepository;
import com.hospital.repository.MedicineRepository;

@Service
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private final MedicineOrderRepository orderRepository;

    public MedicineService(MedicineRepository medicineRepository, MedicineOrderRepository orderRepository) {
        this.medicineRepository = medicineRepository;
        this.orderRepository = orderRepository;
    }

    public Medicine addOrUpdateMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    public MedicineOrder purchaseMedicine(MedicineOrder order) {
        Medicine med = medicineRepository.findByMedicineNameIgnoreCase(order.getMedicineName())
                .orElseThrow(() -> new IllegalArgumentException("Medicine not found in inventory"));

        if (med.getStockQuantity() < order.getQuantity()) {
            throw new IllegalArgumentException("Insufficient stock available (" + med.getStockQuantity() + " remaining)");
        }

        med.setStockQuantity(med.getStockQuantity() - order.getQuantity());
        medicineRepository.save(med);

        order.setTotalCost(order.getQuantity() * med.getUnitPrice());
        order.setOrderDate(LocalDate.now().toString());
        return orderRepository.save(order);
    }

    public List<MedicineOrder> getPatientOrders(String patientId) {
        return orderRepository.findByPatientId(patientId);
    }
}