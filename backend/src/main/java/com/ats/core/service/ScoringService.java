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
     * @param jobDescription the job description text
     * @return detailed scoring results with improvement tips
     */
    public ScoreResult computeScore(String resumeText, String jobDescription) {
        // Normalize and tokenize texts
        String normalizedResume = TextCleaner.normalize(resumeText);
        String normalizedJD = TextCleaner.normalize(jobDescription);
        
        List<String> resumeTokens = Tokenizer.tokenize(normalizedResume);
        List<String> jdTokens = Tokenizer.tokenize(normalizedJD);
        
        // Compute individual scores
        double keywordMatch = computeKeywordMatch(resumeTokens, jdTokens);
        double skillRelevance = computeSkillRelevance(resumeTokens, jdTokens);
        double formatting = computeFormattingScore(normalizedResume);
        
        // Compute weighted overall score
        double overall = (keywordMatch * keywordWeight) +
                        (skillRelevance * skillWeight) +
                        (formatting * formattingWeight);
        
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
        double baseScore = 50.0;
        
        if (TextCleaner.containsEmail(resumeText)) {
            baseScore += 10;
        }
        
        if (TextCleaner.containsPhone(resumeText)) {
            baseScore += 10;
        }
        
        if (TextCleaner.containsBulletPoints(resumeText)) {
            baseScore += 10;
        }
        
        if (TextCleaner.containsHeadings(resumeText)) {
            baseScore += 10;
        }
        
        return Math.min(baseScore, 100.0);
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
