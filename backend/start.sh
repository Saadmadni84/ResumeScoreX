#!/bin/bash
mvn clean install -DskipTests
java -Dserver.port=${PORT:-8080} -jar target/ats-score-backend.jar
