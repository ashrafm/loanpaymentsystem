package com.bancx.lps.loans.entities;


import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "LOANS")
public class Loan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long loanId;

    private BigDecimal loanAmount;

    private Integer term;

    @Enumerated(EnumType.ORDINAL)
    private LoanStatus status;

    public Loan() {
    }

    public Loan(Long loanId) {
        this.loanId = loanId;
    }

    public Loan(Long loanId, BigDecimal loanAmount, Integer term) {
        this.loanId = loanId;
        this.loanAmount = loanAmount;
        this.term = term;
        this.status = LoanStatus.ACTIVE;
    }

    public Loan(BigDecimal loanAmount, Integer term) {
        this.loanAmount = loanAmount;
        this.term = term;
        this.status = LoanStatus.ACTIVE;
    }

    public Loan(Long loanId, BigDecimal loanAmount, Integer term, LoanStatus status) {
        this.loanId = loanId;
        this.loanAmount = loanAmount;
        this.term = term;
        this.status = status;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }
}
