package com.ats.core.util;

import java.util.regex.Pattern;

/**
 * TextCleaner - Utility class for normalizing and analyzing text content.
 * 
 * <p>Provides static methods for cleaning resume and job description text,
 * detecting email addresses, phone numbers, bullet points, and common headings.
 * Used for preprocessing text before ATS scoring.</p>
 */
public class TextCleaner {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
    );
    
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "(\\+?\\d{1,3}[-.\\s]?)?\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}"
    );
    
    private static final Pattern BULLET_PATTERN = Pattern.compile(
        "[\\-•*]"
    );
    
    private static final Pattern HEADING_PATTERN = Pattern.compile(
        "\\b(experience|education|skills?|projects?|summary|objective|certifications?|achievements?)\\b",
        Pattern.CASE_INSENSITIVE
    );
    
    private TextCleaner() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Normalizes text by converting to lowercase, trimming whitespace,
     * collapsing multiple spaces, and removing most punctuation.
     * 
     * @param input the text to normalize
     * @return normalized text, or empty string if input is null
     */
    public static String normalize(String input) {
        if (input == null) {
            return "";
        }
        
        // Convert to lowercase and trim
        String normalized = input.toLowerCase().trim();
        
        // Remove punctuation except: . + # @
        normalized = normalized.replaceAll("[^a-z0-9\\s.+#@\\n]", " ");
        
        // Collapse multiple spaces into one
        normalized = normalized.replaceAll(" +", " ");
        
        return normalized.trim();
    }
    
    /**
     * Detects if the text contains an email address.
     * 
     * @param text the text to analyze
     * @return true if email pattern is found, false otherwise
     */
    public static boolean containsEmail(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(text).find();
    }
    
    /**
     * Detects if the text contains a phone number.
     * 
     * @param text the text to analyze
     * @return true if phone pattern is found, false otherwise
     */
    public static boolean containsPhone(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(text).find();
    }
    
    /**
     * Detects if the text contains bullet points.
     * 
     * @param text the text to analyze
     * @return true if bullet point markers are found, false otherwise
     */
    public static boolean containsBulletPoints(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return BULLET_PATTERN.matcher(text).find();
    }
    
    /**
     * Detects if the text contains common resume headings.
     * 
     * @param text the text to analyze
     * @return true if common headings are found, false otherwise
     */
    public static boolean containsHeadings(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return HEADING_PATTERN.matcher(text).find();
    }
}
