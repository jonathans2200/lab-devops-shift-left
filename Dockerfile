FROM openjdk:17-bullseye AS builder

RUN apt-get update && \
    apt-get install -y build-essential maven tree

COPY src ./src
COPY pom.xml .
RUN mvn -B clean package spring-boot:repackage

FROM python:3.9.10-slim-bullseye

RUN apt-get update && \
    apt-get install -y openjdk-17-jre figlet netcat telnet tree

WORKDIR /msoft/app
COPY --from=builder target/minibank-*.jar ./minibank.jar

EXPOSE 8080

CMD ["java", "-jar", "minibank.jar"]
