# Use Java 17 (recommended for Spring Boot)
FROM eclipse-temurin:25-jdk

# Set working directory
WORKDIR /app

# Copy Maven wrapper & pom
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (cached layer)
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build application
RUN ./mvnw clean package -DskipTests

# Expose Render port
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "target/*.jar"]
