package com.flyhub.airtelintegration.src;

import com.flyhub.airtelintegration.src.exceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/airtel-payments")
public class PaymentsDetailsController {

    @Autowired
    PaymentDetailsService paymentDetailsService;

    @GetMapping(path = {"/", ""})
    public ResponseEntity<?> listAllPayments() {
        try {
            List<PaymentDetails> all_payments = paymentDetailsService.listAllPaymentDetails();
            return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_SUCCESS_CODE, Constants.OPERATION_SUCCESS_DESCRIPTION, all_payments), HttpStatus.OK);
        } catch (RecordNotFoundException ex) {
            return new ResponseEntity<>(new OperationResponse(ex.getExceptionCode(), ex.getExceptionMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<?> savePaymentsDetails(@RequestBody PaymentDetails paymentDetails){

        paymentDetailsService.receivePaymentsDetails(paymentDetails);

        if (paymentDetails != null) {
            return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_SUCCESS_CODE, Constants.OPERATION_SUCCESS_DESCRIPTION,paymentDetails), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/payments-report")
    public ResponseEntity<?> generateExcelReport() {
        try {
            paymentDetailsService.generateExcelReport();
            return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_SUCCESS_CODE, "Report successfully generated, You can now download it"), HttpStatus.OK);
        } catch (IOException ex) {
            return new ResponseEntity<>(new OperationResponse(ex.hashCode(), ex.getLocalizedMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/download/")
    public ResponseEntity<?> downloadFile (HttpServletResponse response) {
        try {
            paymentDetailsService.downloadFile(response);
            return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_SUCCESS_CODE, "Report successfully downloaded"), HttpStatus.OK);
        } catch (IOException ex) {
            return new ResponseEntity<>(new OperationResponse(ex.hashCode(), ex.getLocalizedMessage()), HttpStatus.NOT_FOUND);
        }
    }

}




