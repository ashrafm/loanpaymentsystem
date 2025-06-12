package com.bancx.lps.loans.controllers;

import com.bancx.lps.loans.entities.Loan;
import com.bancx.lps.loans.services.LoanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willAnswer;


@WebMvcTest(controllers = LoanController.class)
public class LoanControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LoanService loanService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateLoan() throws Exception {
        System.out.println("createLoan");
        Loan loan = new Loan(new BigDecimal("1000.00"), 6); // Replace YourEntity

        willAnswer(invocation -> {
            Loan newLoan = invocation.getArgument(0);
            newLoan.setLoanId(1L);
            return newLoan;
        }).given(loanService).save(any());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/loans") // Replace /your-endpoint
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loan)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.loanId").value(1))
                .andReturn();
    }
}
