package com.bancx.lps.payments.services;

import com.bancx.lps.loans.domain.exceptions.InvalidLoanParameterException;
import com.bancx.lps.loans.domain.exceptions.LoanNotFoundException;
import com.bancx.lps.loans.entities.Loan;
import com.bancx.lps.loans.services.LoanService;
import com.bancx.lps.payments.domain.exceptions.PaymentAmountException;
import com.bancx.lps.payments.entities.Payment;
import com.bancx.lps.payments.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private LoanService loanService;


    public Payment save(Payment payment) throws LoanNotFoundException,
            InvalidLoanParameterException,
            PaymentAmountException {

        if (payment.getLoan() == null){
            throw new LoanNotFoundException("Loan id not supplied");
        }

        Loan loan = loanService.getLoan(payment.getLoan().getLoanId());

        if (loan == null) {
            throw new LoanNotFoundException("Loan with id " + payment.getLoan().getLoanId() + " not found");
        }

        if (payment.getPaymentAmount().compareTo(loan.getLoanAmount()) > 0){
            throw new PaymentAmountException("Payment amount is greater than loan amount");
        }

        loan.setLoanAmount(loan.getLoanAmount().subtract(payment.getPaymentAmount()));


        loan = loanService.save(loan);
        payment.setLoan(loan);
        return paymentRepository.save(payment);
    }
}
