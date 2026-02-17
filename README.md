# Rate CS Teaching Staff (Prototype)

A full CRUD Spring Boot web app using Thymeleaf and PostgreSQL.

## Features
- Create staff ratings from a form
- Read all ratings (index) and one rating (detail)
- Update ratings with validation feedback and preserved form data
- Delete ratings using a confirmation page
- Server-side validation:
  - required name
  - valid email format
  - score range 1..10
  - comment max length 400
  - unique email rule
- OO/polymorphism component:
  - `StaffMemberProfile` interface with role-specific implementations
  - `displayTitle()` shown in UI

## Tech Stack
- Java 17
- Spring Boot 3
- Thymeleaf
- Spring Data JPA
- PostgreSQL
- JUnit + Spring Test + MockMvc

## Local Run
1. Create PostgreSQL database:
   - DB name: `rate_cs_staff`
2. Set environment variables (or rely on defaults in `application.properties`):
   - `DB_URL` (default: `jdbc:postgresql://localhost:5432/`)
   - `DB_USERNAME` (default: `postgres`)
   - `DB_PASSWORD` (default: `postgres`)
3. Run:
   - `./mvnw spring-boot:run` or `mvn spring-boot:run`
4. Open:
   - `http://localhost:8080/ratings`

## Run Tests
- `./mvnw test` or `mvn test`

## Render Deployment
1. Push repo to GitHub.
2. On Render, create:
   - a new PostgreSQL instance
   - a new Web Service from this repo
3. Set env vars in Render service:
   - `DB_URL` (Render external DB URL in JDBC format)
   - `DB_USERNAME`
   - `DB_PASSWORD`
4. Build command:
   - `mvn clean package`
5. Start command:
   - `mvn spring-boot:run`

## Deliverable placeholders
- Render URL: `<fill-after-deploy>`
- GitHub URL: `<fill-after-push>`

## Known Issues / Future Work
- No authentication/authorization yet
- No average-by-instructor analytics page yet
- No pagination/search yet
