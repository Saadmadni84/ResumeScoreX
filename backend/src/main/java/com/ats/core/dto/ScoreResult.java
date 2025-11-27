package com.ats.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ScoreResult - DTO for ATS scoring results.
 * 
 * <p>Contains detailed scoring metrics including keyword matching,
 * skill relevance, formatting quality, overall score, and personalized
 * improvement tips for the candidate.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreResult {
    
    private double keywordMatch;
    private double skillRelevance;
    private double formatting;
    private double overall;
    private List<String> improvementTips;
}
