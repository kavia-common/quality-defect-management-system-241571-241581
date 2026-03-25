package com.example.springbootbackend.api;

import com.example.springbootbackend.domain.Defect;
import com.example.springbootbackend.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // PUBLIC_INTERFACE
    @GetMapping("/defects")
    @Operation(summary = "Export defects (JSON)", description = "Returns all defects for reporting/export.")
    public List<Defect> defectsJson() {
        return reportService.allDefects();
    }

    // PUBLIC_INTERFACE
    @GetMapping(value = "/defects/{defectId}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "Defect report (PDF)", description = "Generates a PDF report for a defect including its corrective actions.")
    public ResponseEntity<byte[]> defectPdf(@PathVariable Long defectId) {
        byte[] pdf = reportService.defectReportPdf(defectId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("defect-" + defectId + "-report.pdf")
                .build());

        return ResponseEntity.ok().headers(headers).body(pdf);
    }
}
