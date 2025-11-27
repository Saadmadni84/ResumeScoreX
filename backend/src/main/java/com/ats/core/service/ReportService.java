package com.ats.core.service;

import com.ats.core.model.Resume;
import com.ats.core.model.Score;
import com.ats.core.repository.ResumeRepository;
import com.ats.core.repository.ScoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * ReportService - Generates PDF reports for ATS scoring results.
 * 
 * <p>Creates professional PDF reports using Apache PDFBox that summarize
 * resume analysis including scores, metrics, and improvement recommendations.</p>
 */
@Slf4j
@Service
public class ReportService {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final int MAX_LINE_LENGTH = 80;
    
    private final ResumeRepository resumeRepository;
    private final ScoreRepository scoreRepository;
    
    public ReportService(ResumeRepository resumeRepository, ScoreRepository scoreRepository) {
        this.resumeRepository = resumeRepository;
        this.scoreRepository = scoreRepository;
    }
    
    /**
     * Generates a PDF report for a scored resume.
     * 
     * @param resumeId the resume identifier
     * @param scoreId the score identifier
     * @param template the report template to use
     * @return PDF file as byte array
     */
    public byte[] generatePdfReport(UUID resumeId, UUID scoreId, String template) {
        log.info("Generating PDF report for resume: {}, score: {}", resumeId, scoreId);
        
        // Load entities
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resume not found"));
        
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Score not found"));
        
        PDDocument document = new PDDocument();
        
        try {
            PDPage page = new PDPage();
            document.addPage(page);
            
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                float yPosition = 750;
                float margin = 50;
                float pageWidth = page.getMediaBox().getWidth();
                
                // Header
                drawText(contentStream, "ATS Resume Analysis Report", margin, yPosition, 20, true);
                yPosition -= 30;
                
                // Subheader
                drawText(contentStream, "Candidate: " + resume.getFilename(), margin, yPosition, 12, false);
                yPosition -= 20;
                
                String timestamp = score.getCreatedAt().format(DATE_FORMATTER);
                drawText(contentStream, "Analysis Date: " + timestamp, margin, yPosition, 12, false);
                yPosition -= 40;
                
                // Separator line
                contentStream.moveTo(margin, yPosition);
                contentStream.lineTo(pageWidth - margin, yPosition);
                contentStream.stroke();
                yPosition -= 30;
                
                // Score Section
                drawText(contentStream, "Score Summary", margin, yPosition, 14, true);
                yPosition -= 25;
                
                drawText(contentStream, String.format("Overall Score: %.2f%%", score.getOverall()), 
                        margin + 20, yPosition, 12, false);
                yPosition -= 20;
                
                drawText(contentStream, String.format("Keyword Match: %.2f%%", score.getKeywordMatch()), 
                        margin + 20, yPosition, 12, false);
                yPosition -= 20;
                
                drawText(contentStream, String.format("Skill Relevance: %.2f%%", score.getSkillRelevance()), 
                        margin + 20, yPosition, 12, false);
                yPosition -= 20;
                
                drawText(contentStream, String.format("Formatting Quality: %.2f%%", score.getFormatting()), 
                        margin + 20, yPosition, 12, false);
                yPosition -= 40;
                
                // Improvement Tips Section
                drawText(contentStream, "Improvement Recommendations", margin, yPosition, 14, true);
                yPosition -= 25;
                
                if (score.getTipsJson() != null && !score.getTipsJson().isEmpty()) {
                    String[] tips = score.getTipsJson().split(";");
                    
                    for (String tip : tips) {
                        tip = tip.trim();
                        if (!tip.isEmpty()) {
                            List<String> wrappedLines = wrapText(tip, MAX_LINE_LENGTH);
                            
                            for (int i = 0; i < wrappedLines.size(); i++) {
                                String prefix = (i == 0) ? "\u2022 " : "  ";
                                drawText(contentStream, prefix + wrappedLines.get(i), 
                                        margin + 20, yPosition, 12, false);
                                yPosition -= 18;
                            }
                            yPosition -= 5;
                        }
                    }
                }
                
                // Footer/Watermark
                yPosition = 50;
                drawText(contentStream, "Generated by ATS Score Engine", margin, yPosition, 10, false);
            }
            
            // Save to byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            byte[] pdfBytes = outputStream.toByteArray();
            
            log.info("Successfully generated PDF report ({} bytes)", pdfBytes.length);
            return pdfBytes;
            
        } catch (IOException e) {
            log.error("Failed to generate PDF report", e);
            throw new RuntimeException("Failed to generate PDF report", e);
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                log.warn("Failed to close PDF document", e);
            }
        }
    }
    
    /**
     * Loads a generated PDF report.
     * 
     * @param reportId the report identifier
     * @return PDF file as byte array
     */
    public byte[] loadReport(String reportId) {
        log.warn("Direct report loading not implemented - reports are generated on-demand");
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, 
                "Direct report loading is not implemented. Use generatePdfReport instead.");
    }
    
    /**
     * Draws text on the PDF page.
     * 
     * @param contentStream the PDF content stream
     * @param text the text to draw
     * @param x the x coordinate
     * @param y the y coordinate
     * @param fontSize the font size
     * @param bold whether to use bold font
     * @throws IOException if drawing fails
     */
    private void drawText(PDPageContentStream contentStream, String text, 
                         float x, float y, int fontSize, boolean bold) throws IOException {
        contentStream.beginText();
        
        if (bold) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
        } else {
            contentStream.setFont(PDType1Font.HELVETICA, fontSize);
        }
        
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
    }
    
    /**
     * Wraps text into multiple lines based on maximum line length.
     * 
     * @param text the text to wrap
     * @param maxLineLength maximum characters per line
     * @return list of wrapped text lines
     */
    private List<String> wrapText(String text, int maxLineLength) {
        List<String> lines = new ArrayList<>();
        
        if (text == null || text.isEmpty()) {
            return lines;
        }
        
        String[] words = text.split("\\s+");
        StringBuilder currentLine = new StringBuilder();
        
        for (String word : words) {
            if (currentLine.length() + word.length() + 1 <= maxLineLength) {
                if (currentLine.length() > 0) {
                    currentLine.append(" ");
                }
                currentLine.append(word);
            } else {
                if (currentLine.length() > 0) {
                    lines.add(currentLine.toString());
                    currentLine = new StringBuilder(word);
                } else {
                    lines.add(word);
                }
            }
        }
        
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }
        
        return lines;
    }
}
