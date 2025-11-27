package com.ats.core.controller;

import com.ats.core.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ReportController - REST API endpoints for report generation.
 * 
 * <p>Provides endpoints for generating PDF reports of ATS scores
 * and downloading generated reports.</p>
 */
@Slf4j
@RestController
@RequestMapping("/api/report")
@CrossOrigin("*")
public class ReportController {
    
    private final ReportService reportService;
    
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }
    
    /**
     * Generates a PDF report for a scored resume.
     * 
     * @param request report generation request
     * @return report metadata with download URL
     */
    @PostMapping
    public ResponseEntity<Map<String, String>> generateReport(@RequestBody Map<String, String> request) {
        
        String resumeIdStr = request.get("resumeId");
        String scoreIdStr = request.get("scoreId");
        String template = request.getOrDefault("template", "default");
        
        if (resumeIdStr == null || resumeIdStr.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "resumeId is required");
        }
        
        if (scoreIdStr == null || scoreIdStr.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "scoreId is required");
        }
        
        UUID resumeId;
        UUID scoreId;
        
        try {
            resumeId = UUID.fromString(resumeIdStr);
            scoreId = UUID.fromString(scoreIdStr);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid UUID format");
        }
        
        log.info("Report generation request for resume: {}, score: {}, template: {}", 
                resumeId, scoreId, template);
        
        String reportId = reportService.generatePdfReport(resumeId, scoreId, template);
        
        Map<String, String> response = new HashMap<>();
        response.put("reportId", reportId);
        response.put("downloadUrl", "/api/report/download/" + reportId);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Downloads a generated PDF report.
     * 
     * @param reportId the report identifier
     * @return PDF file as byte array
     */
    @GetMapping("/download/{reportId}")
    public ResponseEntity<byte[]> downloadReport(@PathVariable String reportId) {
        
        if (reportId == null || reportId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "reportId is required");
        }
        
        log.info("Report download request for reportId: {}", reportId);
        
        byte[] pdfBytes = reportService.loadReport(reportId);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "ats-report.pdf");
        headers.setContentLength(pdfBytes.length);
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
