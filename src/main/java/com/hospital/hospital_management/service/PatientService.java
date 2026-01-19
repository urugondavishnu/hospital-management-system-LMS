package com.hospital.hospital_management.service;

import com.hospital.hospital_management.entity.Patient;
import com.hospital.hospital_management.exception.ResourceNotFoundException;
import com.hospital.hospital_management.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id){
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patients not found with id : " + id));
    }

    public void deletePatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        patientRepository.delete(patient);
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        existingPatient.setName(updatedPatient.getName());
        existingPatient.setAge(updatedPatient.getAge());
        existingPatient.setPhone(updatedPatient.getPhone());
        existingPatient.setEmail(updatedPatient.getEmail());
        existingPatient.setPassword(updatedPatient.getPassword());
        existingPatient.setAddress(updatedPatient.getAddress());
        existingPatient.setGender(updatedPatient.getGender());
        existingPatient.setBloodGroup(updatedPatient.getBloodGroup());
        existingPatient.setEmergencyContact(updatedPatient.getEmergencyContact());

        return patientRepository.save(existingPatient);
    }



}
