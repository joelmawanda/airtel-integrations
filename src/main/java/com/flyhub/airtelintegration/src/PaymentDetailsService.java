package com.flyhub.airtelintegration.src;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentDetailsService {

    private final String uploadDir = "C:\\MY_PROJECTS\\airtel-integrations\\Payments.xlsx";
    private final String filename = "Payments.xlsx";

    @Autowired
    PaymentDetailsRepository paymentDetailsRepository;

    public List<PaymentDetails> listAllPaymentDetails(){
        List<PaymentDetails> all_payments_details = paymentDetailsRepository.findAll();
        return all_payments_details;
    }

    public PaymentDetails receivePaymentsDetails(PaymentDetails paymentDetails) {
        return paymentDetailsRepository.save(paymentDetails);
    }

    public void generateExcelReport() throws IOException {

        String[] columns = { "Amount", "Reference" };

        List<PaymentDetails> paymentDetails = paymentDetailsRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Payment Details");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Create Other rows and cells with contacts data
        int rowNum = 1;

        for (PaymentDetails paymentDetail : paymentDetails) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(paymentDetail.getAmount());
            row.createCell(1).setCellValue(paymentDetail.getReference());
        }

        // Resize all columns to fit the content size
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("Payments.xlsx");
        workbook.write(fileOut);
        fileOut.close();

        }

    public void downloadFile(HttpServletResponse response) throws FileNotFoundException {

        if (filename.indexOf(".xls")>-1) response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=" +filename);
        response.setHeader("Content-Transfer-Encoding", "binary");
        try {
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
        catch(IOException e) {
            e.printStackTrace();

        }
    }
}

