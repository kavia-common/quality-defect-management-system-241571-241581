package com.example.springbootbackend.service;

import com.example.springbootbackend.domain.CorrectiveAction;
import com.example.springbootbackend.domain.Defect;
import com.example.springbootbackend.repo.CorrectiveActionRepository;
import com.example.springbootbackend.repo.DefectRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Reporting and PDF export.
 */
@Service
public class ReportService {

    private final DefectRepository defects;
    private final CorrectiveActionRepository actions;

    public ReportService(DefectRepository defects, CorrectiveActionRepository actions) {
        this.defects = defects;
        this.actions = actions;
    }

    /**
     * PUBLIC_INTERFACE
     * Returns all defects for reporting.
     */
    @Transactional(readOnly = true)
    public List<Defect> allDefects() {
        return defects.findAll();
    }

    /**
     * PUBLIC_INTERFACE
     * Generates a PDF report for a defect including its corrective actions.
     */
    @Transactional(readOnly = true)
    public byte[] defectReportPdf(Long defectId) {
        Defect d = defects.findById(defectId).orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Defect not found: " + defectId));
        List<CorrectiveAction> a = actions.findByDefectId(defectId);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4, 36, 36, 36, 36);
            PdfWriter.getInstance(document, baos);
            document.open();

            Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD);
            Font hFont = new Font(Font.HELVETICA, 12, Font.BOLD);

            document.add(new Paragraph("Defect Report", titleFont));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Defect ID: " + d.getId(), hFont));
            document.add(new Paragraph("Title: " + d.getTitle()));
            document.add(new Paragraph("Severity: " + d.getSeverity()));
            document.add(new Paragraph("Status: " + d.getStatus()));
            document.add(new Paragraph("Location: " + (d.getLocation() == null ? "" : d.getLocation())));
            document.add(new Paragraph("Created By: " + (d.getCreatedBy() == null ? "" : d.getCreatedBy())));
            document.add(new Paragraph("Created At: " + (d.getCreatedAt() == null ? "" : d.getCreatedAt().toString())));
            document.add(new Paragraph("Updated At: " + (d.getUpdatedAt() == null ? "" : d.getUpdatedAt().toString())));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Description:", hFont));
            document.add(new Paragraph(d.getDescription()));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Corrective Actions", hFont));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.addCell("Description");
            table.addCell("Owner");
            table.addCell("Due Date");
            table.addCell("Status");

            DateTimeFormatter df = DateTimeFormatter.ISO_LOCAL_DATE;
            for (CorrectiveAction ca : a) {
                table.addCell(ca.getDescription());
                table.addCell(ca.getOwner() == null ? "" : ca.getOwner());
                table.addCell(ca.getDueDate() == null ? "" : df.format(ca.getDueDate()));
                table.addCell(ca.getStatus().name());
            }
            document.add(table);

            document.close();
            return baos.toByteArray();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to generate PDF report", ex);
        }
    }
}
