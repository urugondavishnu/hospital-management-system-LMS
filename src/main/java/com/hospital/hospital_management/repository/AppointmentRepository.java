package com.hospital.hospital_management.repository;

import com.hospital.hospital_management.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByDoctorIdAndAppointmentDate(
            Long doctorId,
            LocalDate appointmentDate
    );

    long countByAppointmentDate(LocalDate date);
}