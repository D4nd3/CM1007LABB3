FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/backenduser-0.0.1-SNAPSHOT.jar backenduser.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "backenduser.jar"]