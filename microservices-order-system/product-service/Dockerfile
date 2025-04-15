# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/product-service-*.jar product-service.jar

# Expose the port the application runs on
EXPOSE 8082

# Command to run the application
ENTRYPOINT ["java", "-jar", "product-service.jar"]
