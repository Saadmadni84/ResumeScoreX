# Architecture Overview

## System Architecture

The ATS Score application follows a three-tier architecture:

### 1. Frontend Layer (Next.js)
- **Technology**: Next.js 14 with Tailwind CSS
- **Responsibility**: User interface, file uploads, score visualization
- **Components**:
  - Upload forms
  - Score dashboards
  - Radar charts
  - Improvement tips

### 2. Backend Layer (Spring Boot 3)
- **Technology**: Spring Boot 3+ with Java 17
- **Responsibility**: Business logic, ATS scoring algorithms
- **Key Components**:
  - **Controllers**: Handle HTTP requests
  - **Services**: Business logic implementation
  - **Repositories**: Data persistence
  - **Models**: JPA entities

### 3. Data Layer
- **Database**: JPA-compatible database
- **Entities**:
  - Resume
  - Score
  - JobDescription

## Communication Flow
1. User uploads resume via frontend
2. Frontend sends request to backend API
3. Backend processes resume and calculates ATS score
4. Score is stored in database
5. Results are returned to frontend for visualization

## Key Features
- Resume parsing and text extraction
- Keyword matching
- ATS scoring algorithm
- Report generation
- PDF export capabilities
