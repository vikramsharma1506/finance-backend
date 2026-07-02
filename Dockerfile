# =============================================
# STAGE 1 — BUILD
# =============================================
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml first for dependency caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the project — skip tests
RUN mvn clean package -DskipTests

# =============================================
# STAGE 2 — RUN
# =============================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy JAR from build stage
# Uses the SNAPSHOT name which Maven actually creates
COPY --from=build /app/target/finance-backend-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-jar", \
  "app.jar"]