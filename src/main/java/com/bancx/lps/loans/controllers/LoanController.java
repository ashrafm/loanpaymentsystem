package com.bancx.lps.loans.controllers;

import com.bancx.lps.loans.domain.exceptions.InvalidLoanParameterException;
import com.bancx.lps.loans.domain.exceptions.LoanNotFoundException;
import com.bancx.lps.loans.entities.Loan;
import com.bancx.lps.loans.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping
    public Loan createLoan(@RequestBody Loan loan) throws Exception{
        return loanService.save(loan);

    }

    @GetMapping(value = "/{loanId}")
    public Loan getLoan(@PathVariable Long loanId) throws Exception {
        return loanService.getLoan(loanId);
    }


    @ExceptionHandler(LoanNotFoundException.class)
    public ResponseEntity<String> handleLoanNotFoundException(LoanNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidLoanParameterException.class)
    public ResponseEntity<String> handleLInvalidLoanParameterException(InvalidLoanParameterException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
