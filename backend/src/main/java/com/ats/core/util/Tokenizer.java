package com.ats.core.util;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Tokenizer - Utility class for tokenizing text into keywords.
 * 
 * <p>Provides static methods for splitting text into tokens, filtering stopwords,
 * and generating frequency maps. Used for keyword matching and ATS scoring.</p>
 */
public class Tokenizer {
    
    private static final Set<String> STOPWORDS = Set.of(
        "the", "is", "in", "at", "to", "and", "of", "for", "a", "an"
    );
    
    private Tokenizer() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Tokenizes text by splitting on whitespace and punctuation,
     * filtering stopwords, and converting to lowercase.
     * 
     * @param text the text to tokenize
     * @return list of tokens in lowercase, excluding stopwords
     */
    public static List<String> tokenize(String text) {
        if (text == null || text.isEmpty()) {
            return List.of();
        }
        
        // Split by whitespace and punctuation (except +, #, .)
        return Arrays.stream(text.toLowerCase()
                .split("[^a-z0-9+#.]+"))
                .filter(token -> !token.isEmpty())
                .filter(token -> !STOPWORDS.contains(token))
                .collect(Collectors.toList());
    }
    
    /**
     * Creates a frequency map from a list of tokens.
     * 
     * @param tokens the list of tokens
     * @return map with token counts
     */
    public static Map<String, Integer> frequencyMap(List<String> tokens) {
        if (tokens == null || tokens.isEmpty()) {
            return Map.of();
        }
        
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String token : tokens) {
            frequencyMap.put(token, frequencyMap.getOrDefault(token, 0) + 1);
        }
        return frequencyMap;
    }
    
    /**
     * Extracts unique tokens from a list.
     * 
     * @param tokens the list of tokens
     * @return set of unique tokens
     */
    public static Set<String> uniqueTokens(List<String> tokens) {
        if (tokens == null || tokens.isEmpty()) {
            return Set.of();
        }
        return new HashSet<>(tokens);
    }
}
