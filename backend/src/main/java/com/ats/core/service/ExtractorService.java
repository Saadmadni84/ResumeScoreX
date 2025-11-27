package com.ats.core.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * ExtractorService - Handles text extraction from resume documents.
 * 
 * <p>This service uses Apache Tika to extract text content from various
 * document formats including PDF and DOCX. It provides robust error handling
 * and ensures clean text output for downstream ATS processing.</p>
 */
@Slf4j
@Service
public class ExtractorService {
    
    private final Tika tika = new Tika();
    
    /**
     * Extracts text content from an uploaded resume file.
     * 
     * @param file the uploaded multipart file
     * @param useOcr flag to enable OCR processing (future feature)
     * @return extracted text content, or empty string if extraction fails
     */
    public String extractText(MultipartFile file, boolean useOcr) {
        if (file == null || file.isEmpty()) {
            log.warn("Attempted to extract text from null or empty file");
            return "";
        }
        
        try (InputStream inputStream = file.getInputStream()) {
            String extractedText = parseWithTika(inputStream);
            
            if (useOcr) {
                // TODO: Implement OCR integration with Tesseract for image-based PDFs
                log.debug("OCR requested but not yet implemented");
            }
            
            return extractedText;
            
        } catch (Exception e) {
            log.warn("Failed to extract text from file: {}", file.getOriginalFilename(), e);
            return "";
        }
    }
    
    /**
     * Parses text content from an input stream using Apache Tika.
     * 
     * @param is the input stream containing document data
     * @return extracted and trimmed text content
     * @throws Exception if parsing fails
     */
    private String parseWithTika(InputStream is) throws Exception {
        String text = tika.parseToString(is);
        return text != null ? text.trim() : "";
    }
}
