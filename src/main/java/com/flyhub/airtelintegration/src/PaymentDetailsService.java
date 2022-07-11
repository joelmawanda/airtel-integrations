package com.flyhub.airtelintegration.src;

import com.flyhub.airtelintegration.src.exceptions.RecordNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 *
 * @author Mawanda Joel
 */
@AllArgsConstructor
@Service
public class PaymentDetailsService {

    Logger log = LoggerFactory.getLogger(PaymentDetailsService.class);

    private final String uploadDir = "C:\\MY_PROJECTS\\airtel-integrations\\Payments.xlsx";
    private final String filename = "Payments.xlsx";

    @Autowired
    PaymentDetailsRepository paymentDetailsRepository;

    public Page<PaymentDetails> listAllPaymentDetailsWithPaginationAndSorting(Pageable page) {
        log.info("[Inside the listAllPaymentDetailsWithPaginationAndSorting method]: Retrieving all payments from the database");
        return  paymentDetailsRepository.findAll(page);
    }

    public PaymentDetails getPaymentById (Long id) throws RecordNotFoundException {
        log.info("[Inside the getPaymentById method]: Retrieving payment from the database");
        PaymentDetails paymentDetails = paymentDetailsRepository.findById(id).orElse(null);
        if(paymentDetails == null){
            throw new RecordNotFoundException("Record with id " + id + " not found");
        }else{
            return paymentDetails;
        }
    }

    public PaymentDetails receivePaymentsDetails(PaymentDetails paymentDetails) {
        log.info("[Inside the receivePaymentsDetails method]: Receiving payments");
        return paymentDetailsRepository.save(paymentDetails);
    }

    public void generateExcelReport() throws IOException {

        String[] columns = { "Amount", "Reference", "Payments Date" };

        log.info("[Inside the generateExcelReport method]: Retrieving all payments from the database");

        List<PaymentDetails> paymentDetails = paymentDetailsRepository.findAll();

        log.info("Creating sheet: Payment Details");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Payment Details");

        log.info("Designing the excel document");
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        log.info("Designing the excel document cell style");
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        log.info("Creating the row for the excel document");
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Create Other rows and cells with their data
        log.info("Creating Other rows and cells with their data");
        int rowNum = 1;

        for (PaymentDetails paymentDetail : paymentDetails) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(paymentDetail.getAmount());
            row.createCell(1).setCellValue(paymentDetail.getReference());
            row.createCell(2).setCellValue(paymentDetail.getCreateDate().toString());
        }

        // Resize all columns to fit the content size
        log.info("Resizing all columns to fit the content size");

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        log.info("Writing the output to a file");
        FileOutputStream fileOut = new FileOutputStream("Payments.xlsx");
        workbook.write(fileOut);
        fileOut.close();

        }

    public void downloadFile(HttpServletResponse response) throws IOException {

        log.info("[Inside the downloadFile method]: Downloading report");

        if (filename.indexOf(".xlsx")>-1) response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=" +filename);
        response.setHeader("Content-Transfer-Encoding", "binary");
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            FileInputStream fis = new FileInputStream(uploadDir);
            int len;
            byte[] buf = new byte[1024];
            while((len = fis.read(buf)) > 0) {
                bos.write(buf,0,len);
            }
            bos.close();
            response.flushBuffer();
    }
}

