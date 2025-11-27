package com.ats.core.repository;

import com.ats.core.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Score Repository - Data access layer for Score entities.
 * 
 * <p>Provides CRUD operations and custom query methods for managing ATS scores.
 * Supports retrieving scores by resume, finding the most recent score, and
 * querying scores by job description.</p>
 */
@Repository
public interface ScoreRepository extends JpaRepository<Score, UUID> {
    
    List<Score> findAllByResumeId(UUID resumeId);
    
    Optional<Score> findTopByResumeIdOrderByCreatedAtDesc(UUID resumeId);
    
    List<Score> findAllByJobDescriptionId(UUID jobDescriptionId);
}
