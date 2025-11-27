package com.ats.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Resume Entity - Represents uploaded resume documents in the ATS system.
 * 
 * <p>This entity stores resume metadata including the uploaded file information,
 * extracted text content, and upload timestamps. Each resume can be analyzed
 * against job descriptions to generate ATS scores.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "resumes")
public class Resume {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @Column(nullable = false)
    private String filename;
    
    @Column(nullable = false)
    private String storagePath;
    
    @Column(columnDefinition = "TEXT")
    private String extractedText;
    
    @Column(nullable = false)
    private OffsetDateTime uploadTs;
    
    private UUID userId;
    
    @PrePersist
    protected void onCreate() {
        if (uploadTs == null) {
            uploadTs = OffsetDateTime.now();
        }
    }
}
