package com.flyhub.airtelintegration.src;


import com.flyhub.airtelintegration.src.exceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Optional;

/**
 *
 * @author Mawanda Joel
 */


@RestController
@RequestMapping("/api/v1/airtel-payments")
public class PaymentsDetailsController {

    @Autowired
    PaymentDetailsService paymentDetailsService;

    @GetMapping(path = {"/", ""})
    public ModelAndView showPaymentsDetails(ModelAndView mv, Pageable page) {
            Page<PaymentDetails> all_payments_details = paymentDetailsService.listAllPaymentDetailsWithPaginationAndSorting(page);
            mv.addObject("all_payments_details", all_payments_details);
            mv.setViewName("payments");
            return mv;
    }

//    @GetMapping("findById/{id}")
//    public ResponseEntity<?> getPaymentById (@PathVariable("id") @NotNull(message = "Id cannot be null") Long id) throws RecordNotFoundException {
//            PaymentDetails paymentDetails = paymentDetailsService.getPaymentById(id);
//            return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_SUCCESS_CODE, Constants.OPERATION_SUCCESS_DESCRIPTION, paymentDetails), HttpStatus.OK);
//    }

    @GetMapping("findById/{id}")
    public ResponseEntity<?> getPaymentById (@PathVariable("id") @NotNull(message = "Id cannot be null") Long id) throws RecordNotFoundException {
        try {
            PaymentDetails paymentDetails = paymentDetailsService.getPaymentById(id);
            return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_SUCCESS_CODE, Constants.OPERATION_SUCCESS_DESCRIPTION, paymentDetails), HttpStatus.OK);
        } catch (RecordNotFoundException ex) {
            return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_FAILURE_CODE, ex.getLocalizedMessage()), HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping()
    public ResponseEntity<?> savePaymentsDetails(@RequestBody @Valid PaymentDetails paymentDetails){

       PaymentDetails new_payment = paymentDetailsService.receivePaymentsDetails(paymentDetails);

        return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_SUCCESS_CODE, Constants.OPERATION_SUCCESS_DESCRIPTION,new_payment), HttpStatus.CREATED);
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




