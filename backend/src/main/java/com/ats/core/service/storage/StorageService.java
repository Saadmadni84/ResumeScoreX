package com.ats.core.service.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * StorageService - Interface for file storage operations.
 * 
 * <p>Defines the contract for storing, loading, and deleting uploaded
 * resume files in the ATS system. Implementations can use local filesystem,
 * cloud storage (S3, Azure Blob), or other storage backends.</p>
 */
public interface StorageService {
    
    /**
     * Stores an uploaded file and returns the stored filename.
     * 
     * @param file the multipart file to store
     * @return the unique filename used for storage
     */
    String store(MultipartFile file);
    
    /**
     * Loads a stored file as a resource.
     * 
     * @param filename the name of the file to load
     * @return the file as a Spring Resource
     */
    Resource loadAsResource(String filename);
    
    /**
     * Deletes a stored file.
     * 
     * @param filename the name of the file to delete
     */
    void delete(String filename);
}
