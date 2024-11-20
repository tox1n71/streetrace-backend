FROM ubuntu:latest
FROM openjdk:18-jdk-slim as build

RUN apt-get update && apt-get install -y wget unzip && \
    wget https://services.gradle.org/distributions/gradle-7.6-bin.zip -P /tmp && \
    unzip /tmp/gradle-7.6-bin.zip -d /opt && \
    ln -s /opt/gradle-7.6/bin/gradle /usr/local/bin/gradle && \
    rm -rf /tmp/gradle-7.6-bin.zip \

WORKDIR /backend
COPY streetrace-api/build.gradle streetrace-api/settings.gradle ./
RUN gradle dependencies --no-daemon
COPY streetrace-api/src ./src
RUN gradle build -x test --no-daemon
FROM openjdk:18-jdk-slim
WORKDIR /backend
COPY --from=build /backend/build/libs/api-0.0.1-SNAPSHOT.jar /backend/backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/backend/backend.jar"]
