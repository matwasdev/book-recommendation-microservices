FROM openjdk:21-jdk-slim

COPY build/libs/*.jar app.jar

EXPOSE 8765

ENTRYPOINT ["java", "-jar", "app.jar"]

