# Hospital Management System

## Description

Hospital Management System is a Spring Boot application designed to manage hospital operations, including patient records, appointments, and medical staff.

## Requirements

- Java 23
- Maven
- PostgreSQL (Runtime dependency)

## Installation

### Clone the Repository

```sh
git clone <repository-url>
cd hospital-mgmt
```

### Build the Project

```sh
mvn clean install
```

### Run the Application

```sh
mvn spring-boot:run
```

## Configuration

### Database Configuration

Ensure PostgreSQL is running and configure database properties in `application.properties` or `application.yml`.

## Features

- **Spring Boot Security**: Secure endpoints using Spring Security.
- **Spring Data JPA**: Database interactions with JPA and Hibernate.
- **Liquibase**: Database migration and versioning.
- **JWT Authentication**: Secure authentication with JSON Web Tokens.
- **Actuator Monitoring**: System health checks and monitoring.
- **Tracing & Logging**: Integrated with Micrometer and Zipkin.

## Dependencies

This project includes key dependencies:

- **Spring Boot Starters**: Web, Security, JPA, Actuator
- **Liquibase**: Database migration
- **JWT (JJWT)**: Authentication and authorization
- **Micrometer & Zipkin**: Tracing and metrics
- **PostgreSQL**: Database driver
- **MapStruct**: Object mapping
- **Lombok**: Reduce boilerplate code
- **JUnit & Testcontainers**: Testing framework

## Testing

Run tests using:

```sh
mvn test
```

## Code Quality & Formatting

- **Jacoco**: Code coverage analysis
- **Spotless**: Code formatting with Palantir Java Format

Run the following commands for code formatting:

```sh
mvn spotless:check  # Throw error when formatting fails
mvn spotless:apply  # Auto-format code
```

## License

This project is licensed under the MIT License.

