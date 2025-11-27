package com.ats.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

/**
 * ATS Resume Scoring Engine - Main Application Entry Point
 * 
 * <p>This application provides an intelligent resume scoring system that analyzes
 * resumes against job descriptions using natural language processing and keyword
 * matching algorithms. It offers RESTful APIs for resume upload, parsing, scoring,
 * and generating detailed analytical reports.</p>
 * 
 * @author ATS Score Team
 * @version 1.0.0
 */
@Slf4j
@SpringBootApplication
public class AtsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtsApplication.class, args);
        log.info("ATS Resume Scoring Engine started successfully");
    }
}
