# HotelMate

**Full-Stack Hotel Reservation System with Internationalization & Docker Deployment**

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Angular](https://img.shields.io/badge/Angular-17-red.svg)](https://angular.io/)
[![Docker](https://img.shields.io/badge/Docker-enabled-blue.svg)](https://www.docker.com/)

## Overview

HotelMate is a full-stack hotel reservation application built with Spring Boot and Angular. The project demonstrates practical implementation of multithreading, internationalization (i18n), time zone handling, and containerized deployment—skills commonly required in enterprise Java development.

**Key Features:**
- Bilingual welcome messages (English/French) using separate threads
- Multi-timezone display for event scheduling (ET, MT, UTC)
- Multi-currency price display (USD, CAD, EUR)
- Dockerized single-JAR deployment
- RESTful API architecture

## Tech Stack

**Backend:**
- Java 17 (Eclipse Temurin)
- Spring Boot 3.x
- Maven
- Multithreading (`ExecutorService`)

**Frontend:**
- Angular 17
- TypeScript
- Angular CLI

**DevOps:**
- Docker
- Docker Compose (optional)
- AWS Elastic Beanstalk / ECS ready

**Database:**
- MySQL (optional for persistence)
- H2 (in-memory for development)

## Problem Statement

Canadian businesses must comply with bilingual requirements (English and French). Additionally, hotels operating across time zones need to communicate event times clearly to international guests. This application demonstrates how to:

- Implement **resource bundles** for i18n compliance
- Use **multithreading** to display content in multiple languages simultaneously
- Convert and display times across **different time zones**
- Present pricing in **multiple currencies** without actual conversion logic

## Technical Implementation

### Multithreading for i18n

Two separate threads display welcome messages in English and French using Java's `ExecutorService`:
```java
ExecutorService executor = Executors.newFixedThreadPool(2);

executor.submit(() -> {
    ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.ENGLISH);
    System.out.println(bundle.getString("welcome.message"));
});

executor.submit(() -> {
    ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.FRENCH);
    System.out.println(bundle.getString("welcome.message"));
});

executor.shutdown();
```

**Resource Bundles:**
- `messages_en.properties`: English messages
- `messages_fr.properties`: French messages

### Time Zone Conversion

A utility method converts presentation times between Eastern Time (ET), Mountain Time (MT), and Coordinated Universal Time (UTC):
```java
public class TimeZoneConverter {
    public static ZonedDateTime convertTime(ZonedDateTime sourceTime, ZoneId targetZone) {
        return sourceTime.withZoneSameInstant(targetZone);
    }
}
```

The frontend displays event times in all three zones simultaneously for guest convenience.

### Multi-Currency Display

The application displays reservation prices in three currencies on separate lines:
- US Dollars ($)
- Canadian Dollars (C$)
- Euros (€)

*Note: This is a display-only feature; actual currency conversion is not implemented.*

## Installation & Setup

### Prerequisites

**Software:**
- Java 17 or higher
- Node.js 18+ (for Angular)
- Maven 3.8+
- Docker (optional for containerization)

**Hardware:**
- Minimum 4 GB RAM
- Modern multi-core CPU

### Local Development

**1. Clone the repository:**
```bash
git clone https://github.com/yourusername/hotelmate.git
cd hotelmate
```

**2. Build the Angular frontend:**
```bash
cd src/main/UI
npm install
ng build --configuration production
```

This places the compiled Angular app in `src/main/resources/static`.

**3. Build the Spring Boot JAR:**
```bash
cd ../../..  # Back to project root
./mvnw clean package -DskipTests
```

The packaged JAR will be at: `target/D387_sample_code-0.0.2-SNAPSHOT.jar`

**4. Run the application:**
```bash
java -jar target/D387_sample_code-0.0.2-SNAPSHOT.jar
```

Access the application at: `http://localhost:8080`

## Docker Deployment

### Build Docker Image

**Dockerfile:**
```dockerfile
# Start with base JDK image
FROM eclipse-temurin:17-jdk

# Copy project JAR to image
COPY target/D387_sample_code-0.0.2-SNAPSHOT.jar /app/myApp.jar

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "/app/myApp.jar"]
```

**Build the image:**
```bash
docker build -t hotelmate:latest .
```

### Run Docker Container
```bash
docker run -d -p 8080:8080 --name hotelmate-app hotelmate:latest
```

Access the containerized app at: `http://localhost:8080`

**Stop the container:**
```bash
docker stop hotelmate-app
docker rm hotelmate-app
```

## Project Structure
```
hotelmate/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── edu/wgu/d387_sample_code/
│   │   │       ├── rest/              # REST controllers
│   │   │       ├── service/           # Business logic
│   │   │       ├── repository/        # Data access layer
│   │   │       └── util/              # Utility classes (timezone, etc.)
│   │   ├── resources/
│   │   │   ├── static/                # Angular build output
│   │   │   ├── application.properties # Spring configuration
│   │   │   ├── messages_en.properties # English resource bundle
│   │   │   └── messages_fr.properties # French resource bundle
│   │   └── UI/                        # Angular source code
│   │       ├── src/
│   │       ├── angular.json
│   │       └── package.json
│   └── test/                          # Unit tests
├── Dockerfile
├── pom.xml                            # Maven configuration
└── README.md
```

## API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/welcome` | GET | Returns bilingual welcome messages |
| `/api/timezone` | GET | Returns presentation time in ET, MT, UTC |
| `/api/currency` | GET | Returns price in USD, CAD, EUR |
| `/api/reservations` | GET | Retrieves all reservations |
| `/api/reservations` | POST | Creates a new reservation |

## Cloud Deployment

### AWS Elastic Beanstalk

**1. Install AWS CLI and EB CLI:**
```bash
pip install awsebcli
```

**2. Initialize Elastic Beanstalk:**
```bash
eb init -p docker hotelmate-app
```

**3. Create environment and deploy:**
```bash
eb create hotelmate-env
eb deploy
```

**4. Open the application:**
```bash
eb open
```

### AWS ECS (Alternative)

Push the Docker image to Amazon ECR and create an ECS service with Fargate for serverless container deployment.

## Development Notes

**Why This Architecture?**

- **Single JAR Deployment:** Angular build is embedded in Spring Boot's static resources, simplifying deployment to a single artifact
- **Multithreading:** Demonstrates concurrent execution for i18n, a common requirement in enterprise applications
- **Docker:** Provides consistent deployment across development, staging, and production environments
- **RESTful Design:** Clean separation between frontend and backend enables independent scaling

**Limitations:**

- Currency display is cosmetic only (no real-time exchange rates)
- Time zone conversion is basic (doesn't account for DST edge cases)
- No authentication/authorization implemented
- Database is optional (can run entirely in-memory with H2)

## Future Enhancements

**Core Features:**
- User authentication with Spring Security
- Actual currency conversion using external API (e.g., ExchangeRate-API)
- Email confirmation for reservations
- Payment processing integration

**Technical Improvements:**
- Implement caching for resource bundles
- Add comprehensive unit and integration tests
- Set up CI/CD pipeline (GitHub Actions, Jenkins)
- Add logging and monitoring (ELK stack, Prometheus)

**Scalability:**
- Separate frontend and backend for independent scaling
- Add Redis for session management
- Implement database connection pooling
- Add API rate limiting

## License

MIT License - see LICENSE file for details

## Acknowledgments

Built as a demonstration of full-stack Java development, multithreading, internationalization, and containerized deployment patterns.

---

*A practical full-stack project showcasing Spring Boot, Angular, Docker, and Java enterprise development concepts.*
