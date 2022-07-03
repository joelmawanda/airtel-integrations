package com.flyhub.airtelintegration.src;

import com.flyhub.airtelintegration.src.exceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/airtel-payments")
public class PaymentsDetailsController {

    @Autowired
    PaymentDetailsService paymentDetailsService;

    @Autowired
    PaymentDetailsRepository paymentDetailsRepository;

    @GetMapping(path = {"/", ""})
    public ResponseEntity<?> listAllPayments() {
        try {
            List<PaymentDetails> all_payments = paymentDetailsService.listAllPaymentDetails();
            return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_SUCCESS_CODE, Constants.OPERATION_SUCCESS_DESCRIPTION, all_payments), HttpStatus.OK);
        } catch (RecordNotFoundException ex) {
            return new ResponseEntity<>(new OperationResponse(ex.getExceptionCode(), ex.getExceptionMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public PaymentDetails savePaymentsDetails(@RequestBody PaymentDetails paymentDetails) {
        return paymentDetailsService.receivePaymentsDetails(paymentDetails);
    }

    @GetMapping("/payments-report")
        public void  generateExcelReport () throws IOException {
            paymentDetailsService.generateExcelReport();
        }

    @GetMapping("/download/")
    @ResponseBody
    public void download(HttpServletResponse response) throws FileNotFoundException {
            paymentDetailsService.downloadFile(response);
        }
}




