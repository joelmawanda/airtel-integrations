package com.flyhub.airtelintegration.src;

import com.flyhub.airtelintegration.src.exceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;


@RestController
@RequestMapping("/api/v1/airtel-payments")
public class PaymentsDetailsController {

    @Autowired
    PaymentDetailsService paymentDetailsService;

    @GetMapping(path = {"/", ""})
    public ModelAndView showPaymentsDetails(ModelAndView mv, Pageable page) throws RecordNotFoundException {
            Page<PaymentDetails> all_payments_details = paymentDetailsService.listAllPaymentDetailsWithPaginationAndSorting(page);
            mv.addObject("all_payments_details", all_payments_details);
            mv.setViewName("payments");
            return mv;
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
            return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_FAILURE_CODE, Constants.OPERATION_FAILED_DESCRIPTION), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/download/")
    public ResponseEntity<?> downloadFile (HttpServletResponse response) {
        try {
            paymentDetailsService.downloadFile(response);
            return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_SUCCESS_CODE, "Report successfully downloaded"), HttpStatus.OK);
        } catch (IOException ex) {
            return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_FAILURE_CODE, "Please first generate the report"), HttpStatus.NOT_FOUND);
        }
    }
}




