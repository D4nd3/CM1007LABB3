FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/backendnote-0.0.1-SNAPSHOT.jar backendnote.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "backendnote.jar"]