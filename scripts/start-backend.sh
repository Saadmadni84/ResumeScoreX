#!/bin/bash

# Start Backend Script
echo "Starting ATS Score Backend..."

cd backend

# Check if Maven wrapper exists
if [ ! -f "./mvnw" ]; then
    echo "Maven wrapper not found. Installing..."
    mvn -N io.takari:maven:wrapper
fi

# Build and run the Spring Boot application
./mvnw clean install
./mvnw spring-boot:run
