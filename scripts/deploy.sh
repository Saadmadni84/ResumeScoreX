#!/bin/bash

# Deployment Script
echo "Deploying ATS Score Application..."

# Build backend
echo "Building backend..."
cd backend
./mvnw clean package -DskipTests
cd ..

# Build frontend
echo "Building frontend..."
cd frontend
npm run build
cd ..

# Build Docker images
echo "Building Docker images..."
docker-compose build

# Start containers
echo "Starting containers..."
docker-compose up -d

echo "Deployment complete!"
echo "Backend: http://localhost:8080"
echo "Frontend: http://localhost:3000"
