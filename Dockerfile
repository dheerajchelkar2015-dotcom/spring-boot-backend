# Java 17 (safe & stable for Spring Boot)
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy pom first (for dependency caching)
COPY pom.xml .

# Install Maven
RUN apt-get update && apt-get install -y maven

# Download dependencies
RUN mvn dependency:go-offline

# Copy source code
COPY src src

# Build the app
RUN mvn clean package -DskipTests

# Expose Render port
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "target/*.jar"]
