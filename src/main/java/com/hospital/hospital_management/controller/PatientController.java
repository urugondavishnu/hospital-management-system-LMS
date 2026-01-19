package com.hospital.hospital_management.controller;

import com.hospital.hospital_management.entity.Patient;
import com.hospital.hospital_management.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@Valid @RequestBody Patient patient) {
        Patient savedPatient = patientService.createPatient(patient);
        return ResponseEntity.ok(savedPatient);
    }

    @GetMapping
    public ResponseEntity<?> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id){
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatientById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody Patient patient) {

        return ResponseEntity.ok(patientService.updatePatient(id, patient));
    }

}
