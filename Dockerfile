FROM maven:3.9.9-eclipse-temurin-17

WORKDIR /app

# Copy pom first to leverage Docker layer caching for dependencies.
COPY pom.xml ./
RUN mvn -q -DskipTests dependency:go-offline

# Copy source after dependencies are cached.
COPY src ./src

EXPOSE 8080

CMD ["mvn", "spring-boot:run"]
