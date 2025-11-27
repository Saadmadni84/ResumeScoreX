package com.ats.core.service;

import com.ats.core.dto.ScoreResult;
import com.ats.core.model.Resume;
import com.ats.core.model.Score;
import com.ats.core.repository.ResumeRepository;
import com.ats.core.repository.ScoreRepository;
import com.ats.core.service.storage.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ResumeService - Orchestrates resume processing workflow.
 * 
 * <p>Coordinates file storage, text extraction, entity persistence, and
 * ATS scoring operations. Acts as the primary service layer for resume
 * management in the ATS system.</p>
 */
@Slf4j
@Service
public class ResumeService {
    
    private final StorageService storageService;
    private final ExtractorService extractorService;
    private final ResumeRepository resumeRepository;
    private final ScoreRepository scoreRepository;
    private final ScoringService scoringService;
    
    public ResumeService(
            StorageService storageService,
            ExtractorService extractorService,
            ResumeRepository resumeRepository,
            ScoreRepository scoreRepository,
            ScoringService scoringService) {
        this.storageService = storageService;
        this.extractorService = extractorService;
        this.resumeRepository = resumeRepository;
        this.scoreRepository = scoreRepository;
        this.scoringService = scoringService;
    }
    
    /**
     * Stores an uploaded resume file and extracts its text content.
     * 
     * @param file the uploaded resume file
     * @param jobDescription optional job description for context
     * @param userId optional user identifier
     * @return map containing resume metadata and text preview
     */
    public Map<String, Object> storeAndExtract(MultipartFile file, String jobDescription, String userId) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File cannot be empty");
        }
        
        // Store file
        String storedFilename = storageService.store(file);
        log.info("Stored resume file: {} as {}", file.getOriginalFilename(), storedFilename);
        
        // Extract text
        String extractedText = extractorService.extractText(file, false);
        
        // Create and save Resume entity
        Resume resume = Resume.builder()
                .filename(file.getOriginalFilename())
                .storagePath(storedFilename)
                .extractedText(extractedText)
                .userId(userId != null ? UUID.fromString(userId) : null)
                .build();
        
        resume = resumeRepository.save(resume);
        log.info("Saved resume entity with ID: {}", resume.getId());
        
        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("resumeId", resume.getId());
        response.put("filename", resume.getFilename());
        response.put("storagePath", resume.getStoragePath());
        response.put("extractedTextPreview", getTextPreview(extractedText, 500));
        
        return response;
    }
    
    /**
     * Scores a resume against a job description.
     * 
     * @param resumeId the ID of the resume to score
     * @param jobDescription the job description text
     * @return detailed scoring results
     */
    public ScoreResult scoreResume(UUID resumeId, String jobDescription) {
        if (jobDescription == null || jobDescription.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job description cannot be empty");
        }
        
        Resume resume = getOrThrow(resumeId);
        log.debug("Scoring resume ID: {} against job description", resumeId);
        
        // Compute score
        ScoreResult scoreResult = scoringService.computeScore(resume.getExtractedText(), jobDescription);
        
        // Create and save Score entity
        Score score = Score.builder()
                .resume(resume)
                .jobDescription(null) // Job description not persisted as entity in this flow
                .keywordMatch(scoreResult.getKeywordMatch())
                .formatting(scoreResult.getFormatting())
                .skillRelevance(scoreResult.getSkillRelevance())
                .overall(scoreResult.getOverall())
                .tipsJson(String.join("; ", scoreResult.getImprovementTips()))
                .build();
        
        scoreRepository.save(score);
        log.info("Saved score for resume ID: {} with overall score: {}", resumeId, scoreResult.getOverall());
        
        return scoreResult;
    }
    
    /**
     * Retrieves a resume by ID or throws an exception if not found.
     * 
     * @param resumeId the resume ID
     * @return the resume entity
     */
    private Resume getOrThrow(UUID resumeId) {
        return resumeRepository.findById(resumeId)
                .orElseThrow(() -> {
                    log.warn("Resume not found with ID: {}", resumeId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Resume not found");
                });
    }
    
    /**
     * Generates a text preview of specified length.
     * 
     * @param text the full text
     * @param maxLength maximum preview length
     * @return truncated text preview
     */
    private String getTextPreview(String text, int maxLength) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        
        if (text.length() <= maxLength) {
            return text;
        }
        
        return text.substring(0, maxLength) + "...";
    }
}
