package com.ats.core.controller;

import com.ats.core.dto.ScoreResult;
import com.ats.core.service.ResumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.UUID;

/**
 * ResumeController - REST API endpoints for resume management.
 * 
 * <p>Provides endpoints for uploading resumes, extracting text content,
 * and computing ATS scores against job descriptions.</p>
 */
@Slf4j
@RestController
@RequestMapping("/api/resume")
@CrossOrigin("*")
public class ResumeController {
    
    private final ResumeService resumeService;
    
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }
    
    /**
     * Uploads and processes a resume file.
     * 
     * @param file the resume file to upload
     * @param jobDescription optional job description for context
     * @param userId optional user identifier
     * @return resume metadata and text preview
     */
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "jobDescription", required = false) String jobDescription,
            @RequestParam(value = "userId", required = false) String userId) {
        
        log.info("Resume upload request received: {}", file.getOriginalFilename());
        
        Map<String, Object> response = resumeService.storeAndExtract(file, jobDescription, userId);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Scores a resume against a job description.
     * 
     * @param request scoring request containing resumeId and jobDescription
     * @return detailed ATS scoring results
     */
    @PostMapping("/score")
    public ResponseEntity<ScoreResult> scoreResume(@RequestBody Map<String, String> request) {
        
        String resumeIdStr = request.get("resumeId");
        String jobDescription = request.get("jobDescription");
        
        if (resumeIdStr == null || resumeIdStr.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "resumeId is required");
        }
        
        UUID resumeId;
        try {
            resumeId = UUID.fromString(resumeIdStr);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid resumeId format");
        }
        
        log.info("Scoring request received for resume ID: {}", resumeId);
        
        ScoreResult scoreResult = resumeService.scoreResume(resumeId, jobDescription);
        
        return ResponseEntity.ok(scoreResult);
    }
}
