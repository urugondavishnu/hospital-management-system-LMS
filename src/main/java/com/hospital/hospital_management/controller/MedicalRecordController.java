package com.hospital.hospital_management.controller;

import com.hospital.hospital_management.entity.MedicalRecord;
import com.hospital.hospital_management.service.MedicalRecordService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping("/{doctorId}/records")
    public ResponseEntity<MedicalRecord> addMedicalRecord(
            @PathVariable Long doctorId,
            @Valid @RequestBody MedicalRecord record) {

        record.setDoctorId(doctorId);
        MedicalRecord saved = medicalRecordService.addMedicalRecord(record);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/patients/{patientId}/records")
    public ResponseEntity<?> getPatientRecords(@PathVariable Long patientId) {
        return ResponseEntity.ok(
                medicalRecordService.getRecordsByPatient(patientId)
        );
    }

}
