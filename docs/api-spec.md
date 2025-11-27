# ATS Score API Specification

## Overview
This document describes the REST API endpoints for the ATS Score application.

## Base URL
```
http://localhost:8080/api
```

## Endpoints

### Resume Upload
**POST** `/resume/upload`

Upload a resume for ATS scoring.

**Request:**
- Content-Type: `multipart/form-data`
- Body: Resume file (PDF/DOCX)

**Response:**
```json
{
  "resumeId": 123,
  "message": "Resume uploaded successfully"
}
```

### Get Score
**GET** `/resume/{resumeId}/score`

Retrieve the ATS score for a resume.

**Response:**
```json
{
  "score": 85.5,
  "analysis": "Detailed analysis..."
}
```

## Error Responses
All endpoints may return standard HTTP error codes:
- 400: Bad Request
- 404: Not Found
- 500: Internal Server Error
