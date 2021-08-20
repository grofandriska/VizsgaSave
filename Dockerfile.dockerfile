FROM maven:3.8.1-jdk-11-slim as builder
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline
COPY src /app/src
RUN mvn package -DskipTests

FROM openjdk:11.0.11-jre-slim-buster
WORKDIR /app
EXPOSE 8080
COPY --from=builder /app/target/Vizsgaremek-0.0.1-SNAPSHOT.jar /app/musicapi.jar
CMD "java" "-jar" "musicapi.jar"
