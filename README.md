# HotelMate

**Full-Stack Hotel Reservation System with Multithreading and Internationalization**

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Angular](https://img.shields.io/badge/Angular-14-red.svg)](https://angular.io/)
[![Docker](https://img.shields.io/badge/Docker-ready-blue.svg)](https://www.docker.com/)

## Overview

HotelMate is a full-stack hotel reservation application that demonstrates advanced Java features including multithreading, internationalization (i18n), timezone conversion, and multi-currency display. Built with Spring Boot backend and Angular frontend, it showcases modern web application development with enterprise-level features.

**Key Features:**
- Multithreaded welcome message generation (English/French)
- Internationalization with resource bundles
- Timezone conversion (Eastern, Mountain, UTC)
- Multi-currency display (USD, CAD, EUR)
- Angular 14 frontend embedded in Spring Boot JAR
- Docker containerization for easy deployment

## Problem Statement

Hotels operating across multiple regions need reservation systems that:
- Support multiple languages for diverse customer bases
- Handle timezone differences for international bookings
- Display prices in various currencies
- Efficiently process concurrent requests

Traditional single-language, single-timezone systems fail to meet modern hospitality industry requirements.

## Technical Approach

### Multithreading

Uses Java's `ExecutorService` for concurrent welcome message generation:
```java
ExecutorService executor = Executors.newFixedThreadPool(2);
executor.submit(() -> generateWelcomeMessage("en_US"));
executor.submit(() -> generateWelcomeMessage("fr_CA"));
executor.shutdown();
```

**Why multithreading?** Demonstrates concurrent request handling, improves performance for I/O-bound operations.

### Internationalization (i18n)

Resource bundles for bilingual support:
- `translation_en_US.properties` - English messages
- `translation_fr_CA.properties` - French (Canadian) messages
```java
ResourceBundle bundle = ResourceBundle.getBundle("translation", locale);
String welcome = bundle.getString("welcome");
```

### Timezone Conversion

Custom utility for timezone-aware timestamps:
```java
public static ZonedDateTime convertToTimezone(ZonedDateTime time, String timezone) {
    return time.withZoneSameInstant(ZoneId.of(timezone));
}
```

Supports: `America/New_York` (ET), `America/Denver` (MT), `UTC`

### Multi-Currency Display

Cosmetic currency conversion for display purposes:
- USD (base)
- CAD (Canadian Dollar)
- EUR (Euro)

**Note:** Currency display is cosmetic only - no actual payment processing.

## Tech Stack

**Backend:**
- Java 17
- Spring Boot 2.7
- Spring MVC
- Maven

**Frontend:**
- Angular 14
- TypeScript 4.7
- RxJS 7.5
- Angular Material (optional)

**DevOps:**
- Docker
- Nix (reproducible development environment)

## Setup & Installation

### Prerequisites

**Option A: Using Nix (Recommended)**
- Nix package manager with flakes enabled
- direnv (optional, for automatic environment loading)

**Option B: Manual Installation**
- Java 17 or higher
- Maven 3.8+
- Node.js 20+ (LTS recommended)
- npm 8+

**Optional:**
- Docker (for containerized deployment)

## Setup

### Option 1: Nix Development Environment (Recommended)

The project includes a Nix flake that provides all required dependencies.

**With direnv (automatic):**
```bash
# Clone repository
git clone https://github.com/yourusername/hotelmate.git
cd hotelmate

# Allow direnv to load the environment
direnv allow

# Dependencies are automatically loaded!
# The Nix shell will activate whenever you enter this directory
```

**Without direnv (manual):**
```bash
# Clone repository
git clone https://github.com/yourusername/hotelmate.git
cd hotelmate

# Enter Nix development shell
nix develop

# You're now in the development environment with all dependencies
```

**What the Nix environment provides:**
- ✅ OpenJDK 17
- ✅ Maven 3.x
- ✅ Node.js 24
- ✅ Angular CLI
- ✅ Java language server (for editor support)
- ✅ Google Java Format

---

### Option 2: Manual Installation

**1. Install prerequisites:**
- Install Java 17: https://adoptium.net/
- Install Maven: https://maven.apache.org/download.cgi
- Install Node.js: https://nodejs.org/

**2. Clone repository:**
```bash
git clone https://github.com/yourusername/hotelmate.git
cd hotelmate
```

**3. Install Angular dependencies:**
```bash
cd src/main/UI
npm install
cd ../../..
```

## Running the Application

### Development Mode (Separate Backend & Frontend)

This mode is best for active development with hot-reload.

**Terminal 1 - Backend:**
```bash
# If using Nix
nix develop  # or just cd into directory if using direnv

# Run Spring Boot
mvn spring-boot:run
```

Backend will be available at: `http://localhost:8080`

**Terminal 2 - Frontend:**
```bash
# Navigate to Angular app
cd src/main/UI

# Start development server
ng serve
# or
npm start
```

Frontend will be available at: `http://localhost:4200`

The Angular dev server will proxy API requests to the Spring Boot backend on port 8080.

---

### Production Build (Angular Embedded in JAR)

This creates a single JAR file with the Angular app embedded.
```bash
# Navigate to Angular app
cd src/main/UI

# Build for production
npm install  # if not already done
ng build --configuration production

# Copy build to Spring Boot static resources
cp -r dist/* ../resources/static/

# Go back to project root
cd ../../..

# Build Spring Boot JAR (includes Angular build)
mvn clean package

# Run the combined JAR
java -jar target/hotelmate-0.0.2-SNAPSHOT.jar
```

Access the full application at: `http://localhost:8080`

---

### Docker Deployment
```bash
# Build Docker image
docker build -t hotelmate:latest .

# Run container
docker run -p 8080:8080 hotelmate:latest
```

Access at: `http://localhost:8080`

## Project Structure
```
hotelmate/
├── Dockerfile                       # Docker containerization
├── mvnw                             # Maven wrapper (Unix)
├── mvnw.cmd                         # Maven wrapper (Windows)
├── pom.xml                          # Maven dependencies
├── flake.nix                        # Nix development environment
├── flake.lock                       # Nix dependency lock file
├── README.md
└── src/
    ├── main/
    │   ├── java/
    │   │   └── edu/wgu/d387sample/
    │   │       ├── D387SampleCodeApplication.java    # Spring Boot entry point
    │   │       ├── config/
    │   │       │   └── TimeZoneConfig.java           # Timezone configuration
    │   │       ├── convertor/
    │   │       │   └── CurrencyConverter.java        # Currency display
    │   │       ├── rest/
    │   │       │   ├── ReservationResource.java      # REST endpoints
    │   │       │   └── WelcomeMessageController.java # Multithreaded i18n
    │   │       └── model/
    │   │           ├── Reservation.java
    │   │           └── Room.java
    │   ├── resources/
    │   │   ├── application.properties                # Spring configuration
    │   │   ├── translation_en_US.properties          # English resource bundle
    │   │   ├── translation_fr_CA.properties          # French resource bundle
    │   │   └── static/                               # Angular production builds
    │   └── UI/                                       # Angular frontend (embedded)
    │       ├── angular.json                          # Angular workspace config
    │       ├── package.json                          # npm dependencies
    │       ├── package-lock.json
    │       ├── karma.conf.js                         # Test configuration
    │       ├── tsconfig.json                         # TypeScript config
    │       ├── tsconfig.app.json
    │       ├── tsconfig.spec.json
    │       ├── README.md                             # Angular-specific README
    │       └── src/
    │           ├── app/                              # Angular components
    │           ├── assets/                           # Images, icons
    │           ├── environments/                     # Environment configs
    │           └── index.html                        # Main HTML
    └── test/
        └── java/
            └── edu/wgu/d387sample/
                └── D387SampleCodeApplicationTests.java
```

## Key Features Explained

### Multithreaded Welcome Messages

Concurrent generation of bilingual welcome messages:
```java
@RestController
@RequestMapping("/api")
public class WelcomeMessageController {
    
    @GetMapping("/welcome")
    public Map<String, String> getWelcomeMessages() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        Future<String> englishFuture = executor.submit(() -> 
            generateMessage("en_US"));
        Future<String> frenchFuture = executor.submit(() -> 
            generateMessage("fr_CA"));
        
        // Wait for both threads to complete
        Map<String, String> messages = new HashMap<>();
        messages.put("english", englishFuture.get());
        messages.put("french", frenchFuture.get());
        
        executor.shutdown();
        return messages;
    }
}
```

**Benefits:**
- Demonstrates concurrent request handling
- Simulates real-world parallel processing
- Thread-safe resource bundle access

### Timezone Conversion
```java
public class TimeZoneUtils {
    public static String getCurrentTime(String timezone) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of(timezone));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss z");
        return now.format(formatter);
    }
}
```

**Supported Timezones:**
- `America/New_York` (Eastern Time)
- `America/Denver` (Mountain Time)
- `UTC` (Coordinated Universal Time)

### Resource Bundles

**translation_en_US.properties:**
```properties
welcome=Welcome to our hotel!
rooms=Available Rooms
book=Book Now
```

**translation_fr_CA.properties:**
```properties
welcome=Bienvenue à notre hôtel!
rooms=Chambres Disponibles
book=Réserver Maintenant
```

### Currency Display
```java
public class CurrencyConverter {
    public static Map<String, Double> convertPrice(double usdPrice) {
        Map<String, Double> prices = new HashMap<>();
        prices.put("USD", usdPrice);
        prices.put("CAD", usdPrice * 1.35);  // Cosmetic conversion
        prices.put("EUR", usdPrice * 0.92);  // Cosmetic conversion
        return prices;
    }
}
```

**Note:** No actual currency exchange - display purposes only.

## Frontend Integration

HotelMate uses an **embedded architecture** where Angular is bundled inside the Spring Boot JAR:

**Development Mode:**
- Angular runs on `http://localhost:4200` (via `ng serve`)
- Spring Boot runs on `http://localhost:8080`
- Angular proxies API requests to backend

**Production Mode:**
- Angular is built and copied to `src/main/resources/static/`
- Packaged inside the Spring Boot JAR
- Single deployment artifact
- Everything served from `http://localhost:8080`

### Why Embedded?

**Advantages:**
- Single JAR deployment
- Simplified deployment process
- No CORS configuration needed in production
- Easier Docker containerization

**Trade-offs:**
- Frontend and backend coupled in same repo
- Need to rebuild JAR for frontend changes in production
- Larger JAR file size

## Development Workflow

### Typical Development Cycle

**Backend changes:**
1. Edit Java files in `src/main/java`
2. Spring Boot auto-reloads with `mvn spring-boot:run`
3. Test API endpoints at `http://localhost:8080/api`

**Frontend changes:**
1. Edit Angular files in `src/main/UI/src`
2. Angular auto-reloads with `ng serve`
3. View changes at `http://localhost:4200`

**Full rebuild:**
```bash
# Build Angular
cd src/main/UI
ng build --configuration production
cp -r dist/* ../resources/static/

# Build Spring Boot JAR
cd ../../..
mvn clean package
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/welcome` | Get bilingual welcome messages (multithreaded) |
| GET | `/api/reservations` | List all reservations |
| POST | `/api/reservations` | Create new reservation |
| GET | `/api/time/{timezone}` | Get current time in specified timezone |
| GET | `/api/price/{amount}` | Convert price to multiple currencies |

## Nix Development Environment

This project includes a Nix flake for reproducible development environments.

### What is Nix?

Nix is a package manager that provides:
- ✅ Reproducible builds across machines
- ✅ Isolated development environments
- ✅ No conflicts with system packages
- ✅ Easy onboarding for new developers

### Setting up Nix

**1. Install Nix (if not already installed):**
```bash
# Linux/macOS
sh <(curl -L https://nixos.org/nix/install) --daemon

# Enable flakes
mkdir -p ~/.config/nix
echo "experimental-features = nix-command flakes" >> ~/.config/nix/nix.conf
```

**2. Optional: Install direnv for automatic environment loading:**
```bash
# macOS
brew install direnv

# Linux (Debian/Ubuntu)
sudo apt install direnv

# Add to your shell config (~/.bashrc, ~/.zshrc)
eval "$(direnv hook bash)"  # or zsh, fish, etc.
```

**3. Enter the development environment:**
```bash
cd hotelmate

# With direnv
direnv allow

# Without direnv
nix develop
```

### Flake Contents

The `flake.nix` provides:
- **Java:** OpenJDK 17 + Maven
- **Node.js:** Node.js 24 + Angular CLI
- **Developer tools:** JDT language server, Google Java Format

All dependencies are pinned to specific versions for reproducibility.

## Testing

### Backend Tests
```bash
mvn test
```

### Frontend Tests
```bash
cd src/main/UI
ng test
```

## Limitations & Trade-offs

**Current Limitations:**
- No authentication/authorization
- Currency conversion is cosmetic only (no actual exchange rates)
- Basic timezone conversion (no DST edge case handling)
- No database persistence (in-memory H2 or optional)
- No payment processing

**Design Trade-offs:**
- Embedded Angular (convenience vs. deployment flexibility)
- Multithreading for demonstration (may not be necessary for simple i18n)
- Limited currency support (3 currencies vs. comprehensive conversion)

## License

MIT License - see LICENSE file for details

---

*A full-stack application demonstrating Spring Boot backend development, Angular frontend integration, multithreading, internationalization, and modern DevOps practices.*
