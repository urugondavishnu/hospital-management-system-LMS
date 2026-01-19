package com.hospital.hospital_management.repository;

import com.hospital.hospital_management.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByDoctorIdAndPaidAtBetween(
            Long doctorId,
            LocalDateTime from,
            LocalDateTime to
    );
}