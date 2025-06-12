package com.bancx.lps.loans.repositories;

import com.bancx.lps.loans.entities.Loan;
import com.bancx.lps.loans.entities.LoanStatus;
import com.bancx.lps.loans.repositories.LoanRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

@DataJpaTest
public class LoanRepositoryTests {

    @Autowired
    private LoanRepository loanRepository;

    @Test
    public void testSave() {
        Loan loan = new Loan(new BigDecimal("100.00"), 8);

        loan = loanRepository.save(loan);
        Assertions.assertEquals(1L, loan.getLoanId());
        Assertions.assertEquals(LoanStatus.ACTIVE, loan.getStatus());

    }
}
