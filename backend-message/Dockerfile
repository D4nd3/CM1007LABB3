FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/backendmessage-0.0.1-SNAPSHOT.jar backendmessage.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "backendmessage.jar"]