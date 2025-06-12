package com.bancx.lps.loans.services;

import com.bancx.lps.loans.domain.exceptions.InvalidLoanParameterException;
import com.bancx.lps.loans.domain.exceptions.LoanNotFoundException;
import com.bancx.lps.loans.entities.Loan;
import com.bancx.lps.loans.entities.LoanStatus;
import com.bancx.lps.loans.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    public Loan save(Loan loan) throws LoanNotFoundException, InvalidLoanParameterException {

        if (loan.getLoanAmount() == null){
            throw new InvalidLoanParameterException("loan amount is null");
        }

        if (loan.getTerm() == null){
            throw new InvalidLoanParameterException("loan term is null");
        }
        if (loan.getLoanAmount().compareTo(BigDecimal.ZERO )== 0){
            loan.setStatus(LoanStatus.SETTLED);
        }else {
            loan.setStatus(LoanStatus.ACTIVE);
        }
        loanRepository.save(loan);
        return loan;
    }

    public Loan getLoan(long loanId) throws LoanNotFoundException {
        Optional<Loan> loan =  loanRepository.findById(loanId);
        if(loan.isPresent()) {
            return loan.get();
        }else{
            throw new LoanNotFoundException("loan not found");
        }
    }
}
