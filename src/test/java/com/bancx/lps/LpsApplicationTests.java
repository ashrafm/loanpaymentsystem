package com.bancx.lps;

import com.bancx.lps.loans.entities.Loan;
import com.bancx.lps.loans.entities.LoanStatus;
import com.bancx.lps.payments.entities.Payment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LpsApplicationTests {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private String paymentsUrl = "http://localhost";
    private String loansUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setup() {
        baseUrl = baseUrl.concat(":").concat(String.valueOf(port));
        paymentsUrl = baseUrl.concat("/payments");
        loansUrl = baseUrl.concat("/loans");
    }

    @Test
    void testLoanSave() {

        Loan loan = new Loan(new BigDecimal("1000.00"), 6);
        Loan response = restTemplate.postForObject(loansUrl, loan, Loan.class);
        assertNotNull(response);
        assertEquals(1, response.getLoanId());
    }

    @Test
    void testLoanGet() {

        Loan loan = new Loan(new BigDecimal("1000.00"), 6);
        loan = restTemplate.postForObject(loansUrl, loan, Loan.class);

        assertNotNull(loan);
        String url = loansUrl.concat("/").concat(String.valueOf(loan.getLoanId()));

        Loan response = restTemplate.getForObject(url, Loan.class);
        assertNotNull(response);
        assertEquals(loan.getLoanId(), response.getLoanId());
    }

    @Test
    void testLoanNotFound() {

        String url = loansUrl.concat("/100");
        try {
            restTemplate.getForObject(url, Loan.class);
        } catch (HttpClientErrorException e) {

            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }

    @Test
    void testPaymentsSave() {

        Loan loan = new Loan(new BigDecimal("1000.00"), 6);
        loan = restTemplate.postForObject(loansUrl, loan, Loan.class);


        assertNotNull(loan);
        Payment payment = new Payment(new BigDecimal("1000.00"), loan);
        Payment aPayment = restTemplate.postForObject(paymentsUrl, payment, Payment.class);
        assertNotNull(aPayment);
        assertEquals(1, aPayment.getPaymentId());
    }

    @Test
    void testPaymentsLoanNotFound() {
        Payment payment = new Payment(new BigDecimal("1000.00"), new Loan(10L));
        try {
            Payment aPayment = restTemplate.postForObject(paymentsUrl, payment, Payment.class);
            assertNotNull(aPayment);
            assertEquals(1, aPayment.getPaymentId());
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }

    @Test
    void testPaymentSettlement() {


        Loan loan = new Loan(new BigDecimal("1000.00"), 6);
        loan = restTemplate.postForObject(loansUrl, loan, Loan.class);

        assertNotNull(loan);
        Payment payment = new Payment(new BigDecimal("1000.00"), loan);
        restTemplate.postForObject(paymentsUrl, payment, Payment.class);
        String url = loansUrl.concat("/").concat(loan.getLoanId().toString());
        Loan response = restTemplate.getForObject(url, Loan.class);
        assertNotNull(response);
        assertEquals(LoanStatus.SETTLED, response.getStatus());

    }

    @Test
    void testPaymentsError() {

        try {

            Loan loan = new Loan(new BigDecimal("1000.00"), 6);
            loan = restTemplate.postForObject(loansUrl, loan, Loan.class);

            assertNotNull(loan);
            Payment payment = new Payment(new BigDecimal("1200.00"), loan);
            HttpEntity<Payment> request = new HttpEntity<>(payment);

            restTemplate.exchange(paymentsUrl, HttpMethod.POST, request, Payment.class);

        } catch (HttpClientErrorException e) {

            assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }

    }
}
