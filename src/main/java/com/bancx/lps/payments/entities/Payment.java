package com.bancx.lps.payments.entities;

import com.bancx.lps.loans.entities.Loan;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name  ="payments")
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;

    private BigDecimal paymentAmount;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "loanid", updatable = false, insertable = false)
    private Loan loan;

    public Payment() {
    }


    public Payment(BigDecimal paymentAmount, Loan loan) {
        this.paymentAmount = paymentAmount;
        this.loan = loan;
    }

    public Payment(Long paymentId, BigDecimal paymentAmount, Loan loan) {
        this.paymentId = paymentId;
        this.paymentAmount = paymentAmount;
        this.loan = loan;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }


}
