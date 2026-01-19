package com.hospital.hospital_management.controller;

import com.hospital.hospital_management.entity.Payment;
import com.hospital.hospital_management.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@Valid @RequestBody Payment payment) {
        Payment savedPayment = paymentService.createPayment(payment);
        return ResponseEntity.ok(savedPayment);
    }

    @PatchMapping("/{paymentId}/status")
    public ResponseEntity<Payment> updatePaymentStatus(
            @PathVariable Long paymentId,
            @RequestParam String status) {

        Payment updated = paymentService.updatePaymentStatus(
                paymentId,
                Enum.valueOf(
                        com.hospital.hospital_management.entity.PaymentStatus.class,
                        status.toUpperCase()
                )
        );

        return ResponseEntity.ok(updated);
    }

}
