# ATS Score - Resume Scoring System

An intelligent ATS (Applicant Tracking System) resume scoring application that helps job seekers optimize their resumes for better success rates.

## 🚀 Features

- **Resume Upload**: Support for PDF and DOCX formats
- **ATS Scoring**: Comprehensive scoring based on multiple criteria
- **Keyword Analysis**: Match resume keywords with job descriptions
- **Visual Dashboard**: Interactive charts and score visualization
- **Improvement Tips**: Actionable suggestions to improve your resume
- **Report Generation**: Downloadable PDF reports

## 🏗️ Architecture

### Backend
- **Framework**: Spring Boot 3+ (Java 17)
- **Build Tool**: Maven
- **Database**: JPA-compatible (configurable)
- **Key Features**:
  - RESTful API
  - Resume text extraction
  - ATS scoring algorithms
  - Keyword matching engine

### Frontend
- **Framework**: Next.js 14
- **Styling**: Tailwind CSS
- **Key Features**:
  - Responsive design
  - Interactive dashboards
  - Real-time score updates
  - Data visualization

## 📁 Project Structure

```
ats-score/
├── backend/                 # Spring Boot application
├── frontend/               # Next.js application
├── docs/                   # Documentation
├── storage/                # File uploads (dev)
├── scripts/                # Utility scripts
└── docker-compose.yml      # Docker configuration
```

## 🛠️ Getting Started

### Prerequisites
- Java 17+
- Node.js 18+
- Maven 3.8+
- npm or yarn

### Quick Start

#### Using Scripts
```bash
# Start backend
./scripts/start-backend.sh

# Start frontend (in another terminal)
./scripts/start-frontend.sh
```

#### Using Docker
```bash
# Build and start all services
docker-compose up --build
```

#### Manual Setup

**Backend:**
```bash
cd backend
./mvnw clean install
./mvnw spring-boot:run
```

**Frontend:**
```bash
cd frontend
npm install
npm run dev
```

## 🌐 Access the Application

- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080/api

## 📖 Documentation

- [API Specification](docs/api-spec.md)
- [Architecture Overview](docs/architecture.md)
- [Scoring Engine Details](docs/scoring-engine.md)

## 🧪 Testing

### Backend Tests
```bash
cd backend
./mvnw test
```

### Frontend Tests
```bash
cd frontend
npm test
```

## 🚢 Deployment

Use the deployment script:
```bash
./scripts/deploy.sh
```

## 📝 API Endpoints

### Resume Operations
- `POST /api/resume/upload` - Upload a resume
- `GET /api/resume/{id}` - Get resume details
- `GET /api/resume/{id}/score` - Get ATS score

### Report Operations
- `GET /api/report/{resumeId}` - Generate report
- `GET /api/report/{resumeId}/pdf` - Download PDF report

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👥 Authors

- Your Name - Initial work

# 🙏 Acknowledgments

- Spring Boot team
- Next.js team
- Open source community
