# D387 Advanced Java – Multithreaded Spring Boot + Angular Application

## Project Overview

This project is part of the **WGU D387 – Advanced Java** course.  
It demonstrates the integration of a **Spring Boot** back end (Java) with an **Angular** front end, including **multithreading**, **RESTful API endpoints**, and **database interaction**.  
The application is designed to showcase advanced Java concepts such as:

- Multithreaded server-side processing  
- RESTful communication between client and server  
- Use of data access layers and services  
- Deployment through Docker containers and cloud environments  

The final deliverable packages the full stack into a single runnable JAR (Spring Boot + Angular static build)  
and deploys it as a Dockerized container that can run locally or in the cloud.

---

## Technologies Used

- **Java 17 (OpenJDK / Eclipse Temurin)**
- **Spring Boot 3**
- **Angular 17**
- **Maven**
- **Docker**
- **MySQL (optional for database integration)**
- **AWS Elastic Beanstalk / ECS (for deployment)**

---

## Local Development Setup

### 1️⃣ Build the Spring Boot JAR

Make sure Maven is configured and your Angular build is generated inside  
`src/main/resources/static` (via `ng build --prod` in `src/main/UI`).

Then package the Spring Boot project:

```bash
./mvnw clean package -DskipTests
```
After a successful build your JAR will be located at: 
```bash
target/D387_sample_code-0.0.2-SNAPSHOT.jar
```

# Running the docker container

## Start with base JDK image
FROM eclipse-temurin:17-jdk

## Copy project to image
COPY target/D387_sample_code-0.0.2-SNAPSHOT.jar /app/myApp.jar

## Expose the front-end & backend ports
EXPOSE 8080 4200

## Run our app when image is started
CMD ["java","-jar","/app/myApp.jar"]



