
# Step 1: Use an official base image (OpenJDK 17)
#for x86_64 architecture
FROM openjdk:17-jdk-alpine

#for ARM64 architecture
#FROM --platform=linux/arm64 eclipse-temurin:17-jdk


# Step 3: Copy your JAR file into the container
COPY target/*.jar app.jar

# Step 4: Expose the port your app runs on
EXPOSE 8080

# Step 5: Run the application
CMD ["java", "-jar", "/app.jar"]
