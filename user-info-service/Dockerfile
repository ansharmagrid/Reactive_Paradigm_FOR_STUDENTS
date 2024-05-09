#Use Java Core 17
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:17-oracle
WORKDIR /app
COPY --from=build /app/target/user-info-service-0.0.1-SNAPSHOT.jar .
ENTRYPOINT  java -jar user-info-service-0.0.1-SNAPSHOT.jar
