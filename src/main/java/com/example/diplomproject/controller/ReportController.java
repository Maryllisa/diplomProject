package com.example.diplomproject.controller;

import com.example.diplomproject.report.GenerateReport;
import com.example.diplomproject.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@Controller
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final GenerateReport generateReport;

    @GetMapping("/preview")
    public String previewReport() {
        return "preview"; // Возвращает имя представления (шаблон Thymeleaf, JSP и т. д.)
    }

    @GetMapping("/generate-report")
    @ResponseBody
    public ResponseEntity<Resource> generateReport() throws IOException {
        byte[] reportBytes = reportService.generateReport();
        ByteArrayResource resource = new ByteArrayResource(reportBytes);
        return ResponseEntity.ok()
                .header("fileName", "src")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(new ByteArrayInputStream(reportBytes)));
    }

    @GetMapping("/generate-marking-report")
    @ResponseBody
    public ResponseEntity<Resource> generateMarkinReport() throws IOException {
        byte[] reportBytes = generateReport.generateMarkingProd();
        ByteArrayResource resource = new ByteArrayResource(reportBytes);
        return ResponseEntity.ok()
                .header("pdf", "src")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(new ByteArrayInputStream(reportBytes)));
    }
    @GetMapping("/generate-active-report")
    @ResponseBody
    public ResponseEntity<Resource> generateActiveReport() throws IOException {
        byte[] reportBytes = generateReport.generateActiveWord();
        ByteArrayResource resource = new ByteArrayResource(reportBytes);
        return ResponseEntity.ok()
                .header("pdf", "src")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(new ByteArrayInputStream(reportBytes)));
    }
    @GetMapping("/generate-history-report")
    @ResponseBody
    public ResponseEntity<Resource> generateHistoryReport() throws IOException {
        byte[] reportBytes = generateReport.generateHistoryWord();
        ByteArrayResource resource = new ByteArrayResource(reportBytes);
        return ResponseEntity.ok()
                .header("pdf", "src")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(new ByteArrayInputStream(reportBytes)));
    }
    @GetMapping("/generate-delivery-report")
    @ResponseBody
    public ResponseEntity<Resource> generateDeliveryReport() throws IOException {
        byte[] reportBytes = generateReport.generateDeliveryWord();
        ByteArrayResource resource = new ByteArrayResource(reportBytes);
        return ResponseEntity.ok()
                .header("pdf", "src")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(new ByteArrayInputStream(reportBytes)));
    }
    @GetMapping("/generate-otpusk-report")
    @ResponseBody
    public ResponseEntity<Resource> generateOtpuskReport() throws IOException {
        byte[] reportBytes = generateReport.generateOtpuskWord();
        ByteArrayResource resource = new ByteArrayResource(reportBytes);
        return ResponseEntity.ok()
                .header("pdf", "src")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(new ByteArrayInputStream(reportBytes)));
    }
}
