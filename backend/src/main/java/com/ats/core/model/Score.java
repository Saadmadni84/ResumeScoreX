package com.ats.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Score Entity - Represents ATS scoring results for resume-job matches.
 * 
 * <p>This entity stores comprehensive scoring metrics including keyword matching,
 * formatting quality, skill relevance, and overall ATS compatibility score.
 * It maintains relationships with both Resume and JobDescription entities and
 * stores improvement tips in JSON format.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "scores")
public class Score {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "job_description_id")
    private JobDescription jobDescription;
    
    @Column(nullable = false)
    private Double keywordMatch;
    
    @Column(nullable = false)
    private Double formatting;
    
    @Column(nullable = false)
    private Double skillRelevance;
    
    @Column(nullable = false)
    private Double overall;
    
    @Column(columnDefinition = "TEXT")
    private String tipsJson;
    
    @Column(nullable = false)
    private OffsetDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
    }
}
