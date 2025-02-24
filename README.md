# Read Me First

# Getting Started

### Reference Documentation

### Guides

### Test
mvn test "-Dspring.profiles.active=mongo"
mvn test "-Dspring.profiles.active=postgres"

### Generate Report
mvn clean
mvn test "-Dspring.profiles.active=mongo"
mvn test "-Dspring.profiles.active=postgres"
mvn jacoco:report
