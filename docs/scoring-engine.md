# ATS Scoring Engine

## Overview
The ATS Scoring Engine analyzes resumes and calculates a score based on multiple factors.

## Scoring Criteria

### 1. Keyword Matching (40%)
- Matches job description keywords
- Industry-specific terms
- Technical skills
- Soft skills

### 2. Format & Structure (25%)
- Proper sections (Education, Experience, Skills)
- Consistent formatting
- Clear hierarchy
- Contact information

### 3. Content Quality (20%)
- Action verbs
- Quantifiable achievements
- Relevant experience
- Education alignment

### 4. ATS Compatibility (15%)
- Standard fonts
- No graphics/images
- Proper file format
- Parseable structure

## Scoring Algorithm

```
Total Score = (Keyword Match × 0.40) + 
              (Format Score × 0.25) + 
              (Content Quality × 0.20) + 
              (ATS Compatibility × 0.15)
```

## Score Interpretation
- **90-100**: Excellent - Highly likely to pass ATS
- **75-89**: Good - Should pass most ATS systems
- **60-74**: Fair - May need improvements
- **Below 60**: Poor - Significant improvements needed

## Implementation
The scoring logic is implemented in `ScoringService.java` and uses:
- Natural language processing
- Keyword extraction
- Pattern matching
- Statistical analysis
