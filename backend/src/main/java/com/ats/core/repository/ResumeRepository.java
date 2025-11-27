package com.ats.core.repository;

import com.ats.core.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Resume Repository - Data access layer for Resume entities.
 * 
 * <p>Provides CRUD operations and custom query methods for managing resume
 * documents in the ATS system. Supports filename-based lookups, user-specific
 * queries, and existence checks.</p>
 */
@Repository
public interface ResumeRepository extends JpaRepository<Resume, UUID> {
    
    Optional<Resume> findByFilename(String filename);
    
    List<Resume> findAllByUserId(UUID userId);
    
    boolean existsByFilename(String filename);
}
