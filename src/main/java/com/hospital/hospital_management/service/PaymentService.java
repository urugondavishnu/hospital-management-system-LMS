package com.hospital.hospital_management.service;

import com.hospital.hospital_management.entity.Payment;
import com.hospital.hospital_management.entity.PaymentStatus;
import com.hospital.hospital_management.exception.ResourceNotFoundException;
import com.hospital.hospital_management.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment createPayment(Payment payment) {
        payment.setPaymentStatus(PaymentStatus.PENDING);
        return paymentRepository.save(payment);
    }

    public Payment updatePaymentStatus(Long paymentId, PaymentStatus status) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found with id: " + paymentId
                        ));

        payment.setPaymentStatus(status);

        if (status == PaymentStatus.PAID) {
            payment.setPaidAt(LocalDateTime.now());
        }

        return paymentRepository.save(payment);
    }

    public BigDecimal calculateRevenue(Long doctorId,
                                       LocalDateTime from,
                                       LocalDateTime to) {

        List<Payment> payments =
                paymentRepository.findByDoctorIdAndPaidAtBetween(
                        doctorId, from, to
                );

        return payments.stream()
                .filter(p -> p.getPaymentStatus() == PaymentStatus.PAID)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateMonthlyRevenue(LocalDateTime from, LocalDateTime to) {

        return paymentRepository.findAll().stream()
                .filter(p -> p.getPaymentStatus() == PaymentStatus.PAID)
                .filter(p ->
                        p.getPaidAt() != null &&
                                !p.getPaidAt().isBefore(from) &&
                                !p.getPaidAt().isAfter(to)
                )
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
