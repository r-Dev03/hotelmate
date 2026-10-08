# Start with base JDK image
FROM eclipse-temurin:17-jdk

# Copy project to image
COPY target/*.jar /app/myApp.jar

# Expose the front-end & backend ports
EXPOSE 8080 

# Run our app when image is started
CMD ["java","-jar","/app/myApp.jar"]

