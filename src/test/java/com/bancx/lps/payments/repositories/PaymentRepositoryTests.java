package com.bancx.lps.payments.repositories;

import com.bancx.lps.payments.entities.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class PaymentRepositoryTests {

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void testSave() {
        Payment payment = new Payment();

        paymentRepository.save(payment);
        assertEquals(1L, payment.getPaymentId());
    }
}
