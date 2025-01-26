FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/backendencounter-0.0.1-SNAPSHOT.jar backendencounter.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "backendencounter.jar"]