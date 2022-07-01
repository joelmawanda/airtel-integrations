package com.flyhub.airtelintegration.src;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PaymentDetailsService {

    @Autowired
    PaymentDetailsRepository paymentDetailsRepository;

    public List<PaymentDetails> listAllPaymentDetails(){
        List<PaymentDetails> all_payments_details = paymentDetailsRepository.findAll();
        return all_payments_details;
    }

    public PaymentDetails receivePaymentsDetails(PaymentDetails paymentDetails) {
        return paymentDetailsRepository.save(paymentDetails);
    }
}
