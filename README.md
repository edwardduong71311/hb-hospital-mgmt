# Spring Boot Project

## Read Me First

Welcome to the Spring Boot Project! This repository contains a Spring Boot application with configurations for:

- **Code formatting** with **Spotless**.
- **Running tests** using different Spring profiles (`mongo` and `postgres`).
- **Generating code coverage reports** with **Jacoco**.

## Getting Started

To get this project up and running locally, follow the steps below.

### Prerequisites

Before starting, make sure you have the following installed:

- **Java 21** or newer
- **Maven 3.x**
- **Git**

### Format Code
```bash
    mvn spotless:check # Throw error when fail
    mvn spotless:apply
```

### Test
```bash
    mvn test
```

### Generate Report
```bash
    mvn clean
    mvn verify # Throw when coverage does not meet expectation
    mvn jacoco:report
```