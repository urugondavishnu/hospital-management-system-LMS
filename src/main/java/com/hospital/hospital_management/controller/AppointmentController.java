package com.hospital.hospital_management.controller;

import com.hospital.hospital_management.entity.Appointment;
import com.hospital.hospital_management.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/{patientId}/appointments")
    public ResponseEntity<Appointment> bookAppointment(
            @PathVariable Long patientId,
            @Valid @RequestBody Appointment appointment) {

        appointment.setPatientId(patientId);
        Appointment saved = appointmentService.bookAppointment(appointment);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{patientId}/appointments")
    public ResponseEntity<?> getPatientAppointments(@PathVariable Long patientId) {
        return ResponseEntity.ok(
                appointmentService.getAppointmentsByPatient(patientId)
        );
    }

    @PatchMapping("/appointments/{appointmentId}/cancel")
    public ResponseEntity<Appointment> cancelAppointment(
            @PathVariable Long appointmentId) {

        return ResponseEntity.ok(
                appointmentService.cancelAppointment(appointmentId)
        );
    }

}