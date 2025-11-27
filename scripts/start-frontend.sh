#!/bin/bash

# Start Frontend Script
echo "Starting ATS Score Frontend..."

cd frontend

# Check if node_modules exists
if [ ! -d "node_modules" ]; then
    echo "Installing dependencies..."
    npm install
fi

# Start the Next.js development server
npm run dev
