package com.hospital.hospital_management.service;

import com.hospital.hospital_management.entity.Appointment;
import com.hospital.hospital_management.entity.AppointmentStatus;
import com.hospital.hospital_management.entity.MedicalRecord;
import com.hospital.hospital_management.exception.ResourceNotFoundException;
import com.hospital.hospital_management.repository.AppointmentRepository;
import com.hospital.hospital_management.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final AppointmentRepository appointmentRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository,
                                AppointmentRepository appointmentRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public MedicalRecord addMedicalRecord(MedicalRecord record) {

        Appointment appointment = appointmentRepository.findById(record.getAppointmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found with id: " + record.getAppointmentId()
                        ));

        // Mark appointment as completed
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);

        record.setRecordDate(LocalDateTime.now());
        return medicalRecordRepository.save(record);
    }

    public List<MedicalRecord> getRecordsByPatient(Long patientId) {
        return medicalRecordRepository.findByPatientId(patientId);
    }
}