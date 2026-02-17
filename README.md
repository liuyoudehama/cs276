# Rate CS Teaching Staff

A Spring Boot + Thymeleaf + PostgreSQL course project for rating and managing CS teaching staff (TA / Professor / Instructor / Staff).

## Features
- Full CRUD: create, list, view details, update, and delete rating records
- Server-side validation (Bean Validation)
- Unique email validation (prevents duplicate records)
- Polymorphic role display (`StaffMemberProfile` + Factory)
- Global exception handling and error page support

## Tech Stack
- Java 17
- Spring Boot 3.4.2
- Spring MVC + Thymeleaf
- Spring Data JPA
- PostgreSQL (local/production)
- H2 (tests)
- JUnit 5 + MockMvc
- Docker (Maven + Temurin 17 base image)

## Pages and Routes
- `/` -> redirects to `/ratings`
- `GET /ratings`: ratings list
- `GET /ratings/new`: create form
- `POST /ratings`: create rating
- `GET /ratings/{id}`: rating detail
- `GET /ratings/{id}/edit`: edit form
- `POST /ratings/{id}`: update rating
- `GET /ratings/{id}/delete`: delete confirmation page
- `POST /ratings/{id}/delete`: perform deletion

## Validation Rules
- `name`: required, length 2-100
- `email`: required, valid format, globally unique
- `roleType`: required (`TA` / `PROF` / `INSTRUCTOR` / `STAFF`)
- `clarity` / `niceness` / `knowledgeableScore`: required, range 1-10
- `comment`: optional, max 400 characters

## Run Locally
### 1) Requirements
- JDK 17+
- Maven 3.9+
- PostgreSQL 14+

### 2) Create Database
Example database name: `rate_cs_staff`

### 3) Configure Environment Variables (recommended)
```bash
export DB_URL='jdbc:postgresql://localhost:5432/rate_cs_staff'
export DB_USERNAME='postgres'
export DB_PASSWORD='your_password'
```

### 4) Start Application
```bash
mvn spring-boot:run
```

After startup, open: `http://localhost:8080/ratings`

## Run with Docker
### Build Image
```bash
docker build -t rate-cs-staff .
```

### Start Container
```bash
docker run --rm -p 8080:8080 \
  -e DB_URL='jdbc:postgresql://host.docker.internal:5432/rate_cs_staff' \
  -e DB_USERNAME='postgres' \
  -e DB_PASSWORD='your_password' \
  rate-cs-staff
```

## Testing
This project uses slice tests:
- `@WebMvcTest`: controller/view behavior
- `@DataJpaTest`: persistence behavior
- Bean Validation unit tests: field validation rules

Run tests:
```bash
mvn test
```

See `TESTING.md` for more details.

## Configuration
Main config file: `src/main/resources/application.properties`

Use environment variables for database credentials. Do not commit plaintext secrets.

## Project Structure
```text
src/main/java/com/example/ratecs
├── controller    # MVC controllers and global exception handler
├── model         # JPA entities and enums
├── repository    # data access layer
├── service       # business logic and custom exceptions
└── design        # polymorphism design (Profile + Factory)
```

## Deployment (Render Example)
1. Create a PostgreSQL instance
2. Create a Web Service and connect this repository
3. Set environment variables: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`
4. Build Command: `mvn clean package`
5. Start Command: `mvn spring-boot:run`

## Future Improvements
- Add authentication and authorization
- Add pagination, search, and filtering
- Add analytics pages (for example, aggregation by role/course)
