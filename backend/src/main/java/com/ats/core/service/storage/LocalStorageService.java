package com.ats.core.service.storage;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * LocalStorageService - Local filesystem implementation of StorageService.
 * 
 * <p>Stores uploaded resume files on the local filesystem with unique
 * generated filenames. Automatically creates the storage directory on
 * initialization if it doesn't exist.</p>
 */
@Slf4j
@Service
public class LocalStorageService implements StorageService {
    
    private final Path storageLocation;
    
    public LocalStorageService(@Value("${file.storage.location}") String storagePath) {
        this.storageLocation = Paths.get(storagePath);
    }
    
    @PostConstruct
    public void init() {
        try {
            if (!Files.exists(storageLocation)) {
                Files.createDirectories(storageLocation);
                log.info("Created storage directory at: {}", storageLocation.toAbsolutePath());
            }
        } catch (IOException e) {
            log.error("Failed to create storage directory", e);
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }
    
    @Override
    public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Cannot store empty file");
        }
        
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            
            String uniqueFilename = UUID.randomUUID() + extension;
            Path destinationFile = storageLocation.resolve(uniqueFilename).normalize();
            
            // Security check: ensure the file is stored within the storage location
            if (!destinationFile.getParent().equals(storageLocation)) {
                throw new SecurityException("Cannot store file outside designated directory");
            }
            
            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
            log.info("Stored file: {} as {}", originalFilename, uniqueFilename);
            
            return uniqueFilename;
            
        } catch (IOException e) {
            log.error("Failed to store file: {}", file.getOriginalFilename(), e);
            throw new RuntimeException("Failed to store file", e);
        }
    }
    
    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = storageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(file.toUri());
            
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                log.warn("File not found or not readable: {}", filename);
                throw new RuntimeException("File not found: " + filename);
            }
            
        } catch (Exception e) {
            log.error("Failed to load file: {}", filename, e);
            throw new RuntimeException("Failed to load file: " + filename, e);
        }
    }
    
    @Override
    public void delete(String filename) {
        try {
            Path file = storageLocation.resolve(filename).normalize();
            boolean deleted = Files.deleteIfExists(file);
            
            if (deleted) {
                log.info("Deleted file: {}", filename);
            } else {
                log.warn("File not found for deletion: {}", filename);
            }
            
        } catch (IOException e) {
            log.error("Failed to delete file: {}", filename, e);
            throw new RuntimeException("Failed to delete file: " + filename, e);
        }
    }
}
