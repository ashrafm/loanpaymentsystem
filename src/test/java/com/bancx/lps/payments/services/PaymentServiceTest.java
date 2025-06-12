package com.bancx.lps.payments.services;

import com.bancx.lps.loans.domain.exceptions.InvalidLoanParameterException;
import com.bancx.lps.loans.domain.exceptions.LoanNotFoundException;
import com.bancx.lps.loans.entities.Loan;
import com.bancx.lps.loans.entities.LoanStatus;
import com.bancx.lps.loans.repositories.LoanRepository;
import com.bancx.lps.loans.services.LoanService;
import com.bancx.lps.payments.domain.exceptions.PaymentAmountException;
import com.bancx.lps.payments.entities.Payment;
import com.bancx.lps.payments.repositories.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.willAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanService loanService;

    @Mock
    private PaymentRepository paymentRepository;
    @InjectMocks
    private PaymentService paymentService;


    @Test
    void save() throws InvalidLoanParameterException, LoanNotFoundException, PaymentAmountException {
        Loan loan = new Loan(1L, new BigDecimal("1000.00"), 6);

        when(loanService.save(Mockito.any(Loan.class))).thenReturn(loan);
        loan =  loanService.save(loan);
        assertNotNull(loan);
        assertEquals(1, loan.getLoanId());
        assertEquals(LoanStatus.ACTIVE, loan.getStatus());

        Loan finalLoan = loan;
        willAnswer(invocation -> {
            return finalLoan;
        }).given(loanService).getLoan(anyLong());


        Payment payment = new Payment(1L, new BigDecimal("1000.00"), new Loan(1L));
        when(paymentRepository.save(Mockito.any(Payment.class))).thenReturn(payment);

        Payment aPayment = paymentService.save(payment);
        assertNotNull(aPayment);
        assertEquals(1, aPayment.getPaymentId());

    }
}