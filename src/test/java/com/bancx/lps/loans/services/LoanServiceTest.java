package com.bancx.lps.loans.services;

import com.bancx.lps.loans.entities.Loan;
import com.bancx.lps.loans.entities.LoanStatus;
import com.bancx.lps.loans.repositories.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanService loanService;

    @Test
    void save() throws Exception{

        Loan loan = new Loan(1L, new BigDecimal("1000.00"), 6);

        when(loanRepository.save(Mockito.any(Loan.class))).thenReturn(loan);
        Loan aLoan =  loanService.save(loan);
        assertNotNull(aLoan);
        assertEquals(1, aLoan.getLoanId());
        assertEquals(LoanStatus.ACTIVE, aLoan.getStatus());

    }

    @Test
    void getLoan() throws Exception{

        Loan loan = new Loan(1L,new BigDecimal("1000.00"), 6);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        Loan aLoan =  loanService.getLoan(1L);
        assertNotNull(aLoan);
        assertEquals(1, aLoan.getLoanId());
        assertEquals(LoanStatus.ACTIVE, aLoan.getStatus());
    }
}