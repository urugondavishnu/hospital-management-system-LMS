package com.hospital.hospital_management.service;

import com.hospital.hospital_management.entity.Appointment;
import com.hospital.hospital_management.entity.AppointmentStatus;
import com.hospital.hospital_management.exception.ResourceNotFoundException;
import com.hospital.hospital_management.repository.AppointmentRepository;
import com.hospital.hospital_management.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    public Appointment bookAppointment(Appointment appointment) {

        // Check if patient exists
        patientRepository.findById(appointment.getPatientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found with id: " + appointment.getPatientId()
                        ));

        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setCreatedAt(LocalDateTime.now());

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public Appointment cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found with id: " + appointmentId
                        ));

        LocalDateTime appointmentDateTime =
                LocalDateTime.of(
                        appointment.getAppointmentDate(),
                        appointment.getTimeSlot()
                );

        LocalDateTime now = LocalDateTime.now();


        long hoursBetween = ChronoUnit.HOURS.between(now, appointmentDateTime);

        if (hoursBetween < 24) {
            throw new IllegalStateException(
                    "Appointment cannot be cancelled within 24 hours of the scheduled time"
            );
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        return appointmentRepository.save(appointment);
    }


}