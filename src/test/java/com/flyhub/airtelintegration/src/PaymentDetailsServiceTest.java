//package com.flyhub.airtelintegration.src;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.slf4j.Logger;
//import java.util.Optional;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class PaymentDetailsServiceTest {
//
//    @Mock
//    private PaymentDetailsRepository paymentDetailsRepository;
//
//    private PaymentDetailsService paymentDetailsService;
//
//    @Mock
//    Logger logger;
//
//    @BeforeEach
//    void setUp() {
//        paymentDetailsService =  new PaymentDetailsService(logger, paymentDetailsRepository);
//    }
//
//    @Test
//    void listAllPaymentDetailsWithPaginationAndSorting() {
//        //when
//        paymentDetailsService.listAllPaymentDetailsWithPaginationAndSorting();
//        //then
//        verify(paymentDetailsRepository).findAll();
//    }
//
//    @Test
//    void getPaymentById() {
//        when(paymentDetailsRepository.findById(1L)).thenReturn(Optional.of(new PaymentDetails( "10000", "Payments")));
//        assertEquals("1000", paymentDetailsService.getPaymentById(1L));
//    }
//
//    @Test
//    void receivePaymentsDetails() {
//        //given
//        PaymentDetails paymentDetails = new PaymentDetails();
//        paymentDetails.setAmount("1000");
//        paymentDetails.setReference("Payments");
//
//        Mockito.when(paymentDetailsRepository.save(paymentDetails)).thenReturn(paymentDetails);
//        assertThat(paymentDetails).isEqualTo(paymentDetailsService.receivePaymentsDetails(paymentDetails));
//
//    }
//
//    @Test
//    void generateExcelReport() {
//    }
//
//    @Test
//    void downloadFile() {
//    }
//}