package com.ats.core.repository;

import com.ats.core.model.JobDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * JobDescription Repository - Data access layer for JobDescription entities.
 * 
 * <p>Provides CRUD operations and custom query methods for managing job descriptions
 * in the ATS system. Supports user-specific queries and keyword-based searching
 * for job titles.</p>
 */
@Repository
public interface JobDescriptionRepository extends JpaRepository<JobDescription, UUID> {
    
    List<JobDescription> findAllByUserId(UUID userId);
    
    List<JobDescription> findByTitleContainingIgnoreCase(String keyword);
}
