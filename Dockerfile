# Use the official OpenJDK base image
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/grocery.jar /app/

# Expose the port that your Spring Boot app will run on
EXPOSE 8080

# Specify the command to run on container startup
CMD ["java", "-jar", "grocery.jar"]
