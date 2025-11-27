package com.ats.core.service;

import com.ats.core.dto.ScoreResult;
import com.ats.core.util.TextCleaner;
import com.ats.core.util.Tokenizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ScoringService - Core ATS scoring engine.
 * 
 * <p>Analyzes resumes against job descriptions using keyword matching,
 * skill relevance analysis, and formatting quality assessment. Applies
 * configurable weights to compute an overall ATS compatibility score
 * and generates personalized improvement tips.</p>
 */
@Slf4j
@Service
public class ScoringService {
    
    private final double keywordWeight;
    private final double skillWeight;
    private final double formattingWeight;
    
    public ScoringService(
            @Value("${scoring.weights.keyword}") double keywordWeight,
            @Value("${scoring.weights.skill}") double skillWeight,
            @Value("${scoring.weights.formatting}") double formattingWeight) {
        this.keywordWeight = keywordWeight;
        this.skillWeight = skillWeight;
        this.formattingWeight = formattingWeight;
    }
    
    /**
     * Computes comprehensive ATS score for a resume against a job description.
     * 
     * @param resumeText the extracted resume text
     * @param jobDescription the job description text (can be null or empty for general analysis)
     * @return detailed scoring results with improvement tips
     */
    public ScoreResult computeScore(String resumeText, String jobDescription) {
        // Normalize and tokenize texts
        String normalizedResume = TextCleaner.normalize(resumeText);
        String normalizedJD = (jobDescription != null && !jobDescription.isBlank()) 
                              ? TextCleaner.normalize(jobDescription) : "";
        
        List<String> resumeTokens = Tokenizer.tokenize(normalizedResume);
        List<String> jdTokens = Tokenizer.tokenize(normalizedJD);
        
        // Compute individual scores
        double keywordMatch = jdTokens.isEmpty() ? 0.0 : computeKeywordMatch(resumeTokens, jdTokens);
        double skillRelevance = jdTokens.isEmpty() ? 0.0 : computeSkillRelevance(resumeTokens, jdTokens);
        double formatting = computeFormattingScore(normalizedResume);
        
        // Compute weighted overall score
        // If no job description provided, overall score is just formatting quality
        double overall;
        if (jdTokens.isEmpty()) {
            overall = formatting;
        } else {
            overall = (keywordMatch * keywordWeight) +
                     (skillRelevance * skillWeight) +
                     (formatting * formattingWeight);
        }
        
        // Generate improvement tips
        List<String> improvementTips = generateImprovementTips(keywordMatch, skillRelevance, formatting);
        
        log.debug("Computed score - Overall: {}, Keyword: {}, Skill: {}, Formatting: {}", 
                 overall, keywordMatch, skillRelevance, formatting);
        
        return ScoreResult.builder()
                .keywordMatch(keywordMatch)
                .skillRelevance(skillRelevance)
                .formatting(formatting)
                .overall(overall)
                .improvementTips(improvementTips)
                .build();
    }
    
    /**
     * Computes keyword match score based on token intersection.
     * 
     * @param resumeTokens tokens from resume
     * @param jdTokens tokens from job description
     * @return keyword match score (0-100)
     */
    private double computeKeywordMatch(List<String> resumeTokens, List<String> jdTokens) {
        if (jdTokens.isEmpty()) {
            return 0.0;
        }
        
        Set<String> resumeSet = new HashSet<>(resumeTokens);
        Set<String> jdSet = new HashSet<>(jdTokens);
        
        // Calculate intersection
        Set<String> intersection = new HashSet<>(resumeSet);
        intersection.retainAll(jdSet);
        
        double score = (double) intersection.size() / jdSet.size() * 100;
        return Math.min(score, 100.0);
    }
    
    /**
     * Computes skill relevance score.
     * 
     * @param resumeTokens tokens from resume
     * @param jdTokens tokens from job description
     * @return skill relevance score (0-100)
     */
    private double computeSkillRelevance(List<String> resumeTokens, List<String> jdTokens) {
        // TODO: Implement semantic matching for better skill relevance detection
        // For now, using same logic as keyword match
        return computeKeywordMatch(resumeTokens, jdTokens);
    }
    
    /**
     * Computes formatting quality score based on resume structure.
     * 
     * @param resumeText normalized resume text
     * @return formatting score (0-100)
     */
    private double computeFormattingScore(String resumeText) {
        if (resumeText == null || resumeText.isEmpty()) {
            return 0.0;
        }
        
        double score = 0.0;
        
        // Contact Information (20 points)
        boolean hasEmail = TextCleaner.containsEmail(resumeText);
        boolean hasPhone = TextCleaner.containsPhone(resumeText);
        
        if (hasEmail && hasPhone) {
            score += 20;
        } else if (hasEmail || hasPhone) {
            score += 10;
        }
        
        // Resume Structure (30 points)
        boolean hasHeadings = TextCleaner.containsHeadings(resumeText);
        boolean hasBullets = TextCleaner.containsBulletPoints(resumeText);
        
        if (hasHeadings) {
            score += 15;
        }
        
        if (hasBullets) {
            score += 15;
        }
        
        // Content Length Analysis (20 points)
        int wordCount = resumeText.split("\\s+").length;
        if (wordCount >= 300 && wordCount <= 800) {
            score += 20; // Optimal length
        } else if (wordCount >= 200 && wordCount < 300) {
            score += 15; // Acceptable but short
        } else if (wordCount > 800 && wordCount <= 1200) {
            score += 15; // Acceptable but long
        } else if (wordCount >= 100 && wordCount < 200) {
            score += 10; // Too short
        } else if (wordCount > 1200) {
            score += 10; // Too long
        } else {
            score += 5; // Very short
        }
        
        // Readability & Organization (30 points)
        // Check for line breaks (paragraphs)
        int lineBreaks = resumeText.split("\\n").length;
        if (lineBreaks > 5) {
            score += 15; // Well organized with sections
        } else if (lineBreaks > 2) {
            score += 10; // Some structure
        } else {
            score += 5; // Poor structure
        }
        
        // Check for variety in content (not just repetitive)
        Set<String> uniqueWords = new HashSet<>();
        String[] words = resumeText.split("\\s+");
        for (String word : words) {
            if (word.length() > 3) { // Only count meaningful words
                uniqueWords.add(word);
            }
        }
        
        double uniqueRatio = words.length > 0 ? (double) uniqueWords.size() / words.length : 0;
        if (uniqueRatio > 0.4) {
            score += 15; // Good vocabulary diversity
        } else if (uniqueRatio > 0.25) {
            score += 10; // Moderate diversity
        } else {
            score += 5; // Low diversity (repetitive)
        }
        
        return Math.min(score, 100.0);
    }
    
    /**
     * Generates personalized improvement tips based on scores.
     * 
     * @param keywordMatch keyword match score
     * @param skillRelevance skill relevance score
     * @param formatting formatting score
     * @return list of actionable improvement tips
     */
    private List<String> generateImprovementTips(double keywordMatch, double skillRelevance, double formatting) {
        List<String> tips = new ArrayList<>();
        
        if (keywordMatch < 60) {
            tips.add("Add more job-related keywords");
        }
        
        if (formatting < 60) {
            tips.add("Improve formatting: add headings, bullet points, and consistent structure");
        }
        
        if (skillRelevance < 60) {
            tips.add("Highlight more relevant technical skills");
        }
        
        if (tips.isEmpty()) {
            tips.add("Great job! Your resume is well-optimized for ATS systems");
        }
        
        return tips;
    }
}
