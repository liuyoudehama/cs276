# TESTING.md

This project uses **slice tests**:
- `@WebMvcTest` for controller/web behavior
- `@DataJpaTest` for persistence behavior
- plain Bean Validation unit tests for validation rules

## Why slice tests?
Slice tests are fast and focused while still validating key framework integration points required by the assignment.

## What is covered
1. Validation tests
- invalid email rejected
- out-of-range score rejected
- missing required fields rejected

2. Web/controller tests (MockMvc)
- GET index returns 200 and model contains list data
- GET detail returns 200 and detail model
- POST create success redirects to detail
- POST create failure returns form with field errors
- POST update success redirects
- POST delete redirects to index

3. Persistence tests
- saving and retrieving an entry works
- delete removes the entry

## How to run
- `./mvnw test` or `mvn test`
