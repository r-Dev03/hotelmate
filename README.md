# HotelMate

**Hotel reservation app with bilingual messages, multi-time-zone display, and a Docker build.**

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Angular](https://img.shields.io/badge/Angular-14-red.svg)](https://angular.io/)
[![Docker](https://img.shields.io/badge/Docker-ready-blue.svg)](https://www.docker.com/)

Guests search for rooms by date and book them. The Spring Boot backend serves both the API and the compiled Angular frontend from a single JAR.

**Built on:** a Spring Boot + Angular hotel reservation starter template that provided room search, reservations, the H2 data layer, and the "Landon Hotel" page design and branding. Added in this repository: English/French welcome messages, presentation times across three time zones, prices shown in three currencies, the Docker image, and a Nix dev environment.

## Highlights

- English and French welcome messages loaded from Java resource bundles and served by language tag
- Presentation times converted to Eastern, Mountain, and UTC with `java.time`, so daylight saving is handled automatically
- Room prices displayed in USD, CAD, and EUR, with CAD and EUR formatted by Angular's `currency` pipe
- One Maven build produces a single JAR with the frontend included, packaged into a Docker image

## How It Works

**Welcome messages:** `translation_en_US.properties` and `translation_fr_CA.properties` hold the text. `GET /welcome?lang=fr-CA` loads the matching bundle:

```java
ResourceBundle bundle = ResourceBundle.getBundle("translation", locale);
return bundle.getString("welcome");
```

**Time zones:** `GET /presentation` converts one instant into three zones. Using region IDs like `America/Denver` instead of fixed offsets lets the JDK apply daylight saving rules:

```java
ZonedDateTime time = ZonedDateTime.now();
ZonedDateTime et  = time.withZoneSameInstant(ZoneId.of("America/New_York"));
ZonedDateTime mt  = time.withZoneSameInstant(ZoneId.of("America/Denver"));
ZonedDateTime utc = time.withZoneSameInstant(ZoneId.of("UTC"));
```

The landing page shows the current server time in all three zones, e.g. `There is a presentation starting at: 02:00 PM ET / 12:00 PM MT / 06:00 PM UTC`.

**Single build:** one Maven command builds both halves of the app into a single JAR:

```
./mvnw clean package
   │
   ├─▶ exec-maven-plugin runs `ng build`
   │      src/main/UI  ──▶  src/main/resources/static/   (compiled Angular)
   │
   ├─▶ compile Java + copy resources (including static/)
   │
   └─▶ target/*.jar   ── backend API + frontend in one file
             │
             ├─▶ java -jar target/*.jar        → http://localhost:8080
             └─▶ docker build -t hotelmate .   → same JAR on a Java 17 image
```

Spring Boot serves anything in `static/` at the root URL, so one `java -jar` command runs the whole app on port 8080.

## API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/welcome?lang={tag}` | Welcome message for `en-US` or `fr-CA` |
| GET | `/presentation` | Presentation time in ET, MT, and UTC |
| GET | `/room/reservation/v1` | Search available rooms by check-in and check-out dates |
| GET | `/room/reservation/v1/{roomId}` | Get one room |
| POST | `/room/reservation/v1` | Create a reservation |

The starter template also defines `PUT` and `DELETE` routes for reservations, but they aren't implemented.

## Getting Started

**Prerequisites:** Java 17, Node.js 20, and Angular CLI 14 (`npm install -g @angular/cli@14`). Or run `nix develop`, which provides Java and Node and puts the project's own Angular CLI on your PATH once the frontend dependencies are installed.

```bash
git clone https://github.com/r-Dev03/hotelmate.git
cd hotelmate
(cd src/main/UI && npm install)

./mvnw clean package
java -jar target/*.jar
```

Open `http://localhost:8080`.

**With Docker** (after `./mvnw clean package`):

```bash
docker build -t hotelmate .
docker run -p 8080:8080 hotelmate
```

**For frontend development**, run `./mvnw spring-boot:run` in one terminal and `ng serve` from `src/main/UI` in another, then open `http://localhost:4200`.

## Tech Stack

Java 17 · Spring Boot · Spring Data JPA · H2 · Angular 14 · TypeScript · RxJS · Docker · Nix · Maven

## Known Limitations

- **Currency is display-only.** The same base price is shown with each currency's symbol; no exchange rates are applied.
- **Partial translation.** Only the welcome message is translated; the rest of the UI is English.
- **No authentication or payments.** Reservations are open to anyone.
