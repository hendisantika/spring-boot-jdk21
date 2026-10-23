# Use Maven to build the application
FROM maven:3.9.11-eclipse-temurin-25 AS build

# Set the working directory for the build
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the application (tests run in CI)
RUN mvn -B clean package -DskipTests

# Use a minimal Java runtime for the final image
FROM eclipse-temurin:25-jre

LABEL authors="hendisantika"

# Add a volume to hold the application data
VOLUME /tmp

# The application's jar file
ARG JAR_FILE=target/spring-boot-jdk21-0.0.1-SNAPSHOT.jar

# Copy the jar file from the build stage to the final image
COPY --from=build /app/${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]
