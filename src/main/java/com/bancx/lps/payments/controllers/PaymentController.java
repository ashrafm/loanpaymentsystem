package com.bancx.lps.payments.controllers;

import com.bancx.lps.loans.domain.exceptions.LoanNotFoundException;
import com.bancx.lps.payments.domain.exceptions.PaymentAmountException;
import com.bancx.lps.payments.entities.Payment;
import com.bancx.lps.payments.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public Payment makePayment(@RequestBody Payment payment) throws Exception{
        return paymentService.save(payment);
    }

    @ExceptionHandler(PaymentAmountException.class)
    public ResponseEntity<String> handlePaymentAmountException(PaymentAmountException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());

    }

    @ExceptionHandler(LoanNotFoundException.class)
    public ResponseEntity<String> handleLoanNotFoundExceptionException(LoanNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}


