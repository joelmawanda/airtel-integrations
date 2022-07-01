package com.flyhub.airtelintegration.src;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/airtel-payments")
public class PaymentsDetailsController {

    @Autowired
    PaymentDetailsService paymentDetailsService;

    @Autowired
    PaymentDetailsRepository paymentDetailsRepository;

    @GetMapping(path={"/", ""})
    public List<PaymentDetails> listAllPayments (){
        List<PaymentDetails> all_payements = paymentDetailsService.listAllPaymentDetails();
        return all_payements;
    }

    @PostMapping
    public PaymentDetails savePaymentsDetails(@RequestBody PaymentDetails paymentDetails) {
        return paymentDetailsService.receivePaymentsDetails(paymentDetails);
    }

}
