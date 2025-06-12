package com.bancx.lps.payments.controllers;

import com.bancx.lps.loans.entities.Loan;
import com.bancx.lps.loans.services.LoanService;
import com.bancx.lps.payments.entities.Payment;
import com.bancx.lps.payments.services.PaymentService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.willAnswer;

@WebMvcTest(controllers = PaymentController.class)
public class PaymentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LoanService loanService;

    @MockitoBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testPayment() throws Exception {
        System.out.println("testPayment");
        Payment payment = new Payment(new BigDecimal("200.00"), new Loan(1L));

        willAnswer(invocation -> {
            Loan loan = new Loan();
            loan.setLoanId(invocation.getArgument(0));
            loan.setLoanAmount(new BigDecimal("200.00"));
            loan.setTerm(6);
            return loan;
        }).given(loanService).getLoan(anyLong());

        willAnswer(invocation -> {
            Payment newPayment = invocation.getArgument(0);
            newPayment.setPaymentId(1L);
            return newPayment;

        }).given(paymentService).save(any(Payment.class));

        MvcResult result   = mockMvc.perform(MockMvcRequestBuilders.post("/payments") // Replace /your-endpoint
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payment)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentId").value(1))
                .andReturn();

    }
}
