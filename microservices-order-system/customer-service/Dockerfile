# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/customer-service-*.jar customer-service.jar

# Expose the port the application runs on
EXPOSE 8083

# Command to run the application
ENTRYPOINT ["java", "-jar", "customer-service.jar"]
