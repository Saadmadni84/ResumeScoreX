package com.ats.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * JobDescription Entity - Represents job postings in the ATS system.
 * 
 * <p>This entity stores job description content that serves as the baseline
 * for comparing and scoring candidate resumes. Each job description contains
 * the required skills, qualifications, and keywords used for matching.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "job_descriptions")
public class JobDescription {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    
    @Column(nullable = false)
    private OffsetDateTime createdAt;
    
    private UUID userId;
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
    }
}
