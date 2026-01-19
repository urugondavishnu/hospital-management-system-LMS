package com.hospital.hospital_management.controller;

import com.hospital.hospital_management.repository.AppointmentRepository;
import com.hospital.hospital_management.repository.PatientRepository;
import com.hospital.hospital_management.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final PaymentService paymentService;

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    public AdminController(PaymentService paymentService,
                           PatientRepository patientRepository,
                           AppointmentRepository appointmentRepository) {
        this.paymentService = paymentService;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/revenue")
    public ResponseEntity<?> getRevenue(
            @RequestParam Long doctorId,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) {

        BigDecimal totalRevenue =
                paymentService.calculateRevenue(
                        doctorId,
                        from.atStartOfDay(),
                        to.atTime(23, 59, 59)
                );

        return ResponseEntity.ok(
                new RevenueResponse(totalRevenue)
        );
    }

    record RevenueResponse(BigDecimal total) {}

    @GetMapping("/stats")
    public ResponseEntity<?> getDashboardStats() {

        long totalPatients = patientRepository.count();
        long todayAppointments =
                appointmentRepository.countByAppointmentDate(LocalDate.now());

        BigDecimal monthlyRevenue =
                paymentService.calculateMonthlyRevenue(
                        LocalDate.now().withDayOfMonth(1).atStartOfDay(),
                        LocalDateTime.now()
                );

        return ResponseEntity.ok(
                new DashboardStats(
                        totalPatients,
                        0L, // doctors not implemented
                        todayAppointments,
                        monthlyRevenue
                )
        );
    }

    record DashboardStats(
            long totalPatients,
            long totalDoctors,
            long todayAppointments,
            BigDecimal monthlyRevenue
    ) {}

}
