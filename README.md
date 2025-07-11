# EVMS Charging Station Information Management System

## Project Introduction
This project is an information management system for electric mobility service providers (eMSP), based on Spring Boot + DDD + event-driven design, supporting OCPI-compliant EVSE ID validation, RESTful API, data validation, unit testing, Dockerization, and CI automation.

## Main Features
- CRUD for charging stations (Location)
- Add EVSE (charging equipment), status transitions (with rule validation)
- Add Connector (charging port)
- Paginated query by last_updated
- EVSE ID format validation (OCPI standard)
- Event-driven (status change events)
- Unified exception handling

## Tech Stack
- Java 17
- Spring Boot 3
- Spring Data JPA
- MySQL
- Docker
- GitHub Actions CI

## Local Run
1. Start MySQL, create database `evms`, username/password both `evms`.
2. Modify `src/main/resources/application.yml` for your database config.
3. Build and run:
   ```bash
   mvn clean package
   java -jar target/evms-1.0.0.jar
   ```
4. Access API: `http://localhost:8080/api/locations` etc.

## Docker Run
```bash
docker build -t evms:latest .
docker run -p 8080:8080 --env SPRING_DATASOURCE_URL=jdbc:mysql://<host>:3306/evms --env SPRING_DATASOURCE_USERNAME=evms --env SPRING_DATASOURCE_PASSWORD=evms evms:latest
```

## CI
- Push to main branch triggers Maven build, test, and Docker image build automatically.

## Directory Structure
- domain: Domain models
- application: Application services
- infrastructure: JPA repositories, etc.
- interfaces: RESTful API, exception handling

## Event Driven
- EVSE status change automatically publishes events, can be extended to integrate with message middleware.

---
For cloud deployment, IaC, integration testing, or other advanced features, please contact the developer. 