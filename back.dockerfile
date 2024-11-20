FROM ubuntu:latest
FROM openjdk:18-jdk-slim as build
WORKDIR /backend
COPY streetrace-api/build.gradle streetrace-api/settings.gradle ./
RUN gradle dependencies --no-daemon
COPY streetrace-api/src ./src
RUN gradle build -x test --no-daemon
FROM openjdk:18-jdk-slim
RUN apt-get update && apt-get install -y \
    fontconfig \
    fonts-dejavu-core \
    && apt-get clean && rm -rf /var/lib/apt/lists/*
WORKDIR /backend
COPY --from=build /backend/build/libs/api-0.0.1-SNAPSHOT.jar /backend/backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/backend/backend.jar"]
