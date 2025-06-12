package com.bancx.lps.loans.repositories;

import com.bancx.lps.loans.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
