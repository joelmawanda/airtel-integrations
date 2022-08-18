package com.flyhub.airtelintegration.src;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(MockitoExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@WebMvcTest(PaymentsDetailsController.class)
class PaymentsDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PaymentsDetailsController paymentsDetailsController;

    @MockBean
    private PaymentDetailsService paymentDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //paymentsDetailsController =  new PaymentsDetailsController(paymentDetailsService);
    }

//    @Test
//    void showPaymentsDetails() {
//        ResponseEntity<?> result = paymentsDetailsController.showPaymentsDetails();
//        Assert.assertNotNull(result);
//    }

    @Test
    void getPaymentById() {
    }

    @Test
    void savePaymentsDetails() throws Exception {
        //given
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setAmount("1000");
        paymentDetails.setReference("Payment");

        when(paymentDetailsService.receivePaymentsDetails(any(PaymentDetails.class))).thenReturn(paymentDetails);

        PaymentDetails paymentDetails1 = new PaymentDetails();
        paymentDetails1.setAmount("1000");
        paymentDetails1.setReference("Payments");

        mockMvc.perform(post("/", paymentDetails1)).andExpect(status().isCreated()).andReturn();
    }

    @Test
    void generateExcelReport() {
    }

    @Test
    void downloadFile() {
    }
}