FROM ubuntu:latest
FROM gradle:7.6-jdk17 as build
WORKDIR /backend
COPY streetrace-api/build.gradle streetrace-api/settings.gradle ./
RUN gradle dependencies --no-daemon
COPY streetrace-api/src ./src
RUN gradle build -x test --no-daemon
FROM openjdk:17-jdk-slim
RUN apt-get update && apt-get install -y \
    fontconfig \
    fonts-dejavu-core \
    && apt-get clean && rm -rf /var/lib/apt/lists/*
WORKDIR /backend
COPY --from=build /backend/build/libs/*.jar backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/backend/backend.jar"]
