package com.bancx.lps.payments.repositories;

import com.bancx.lps.payments.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
