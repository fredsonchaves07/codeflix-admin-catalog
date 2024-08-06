FROM maven:3.9.8-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY . /app

RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

ARG JAR_FILE=target/*.jar

COPY --from=build /app/${JAR_FILE} codeflix-admin-catalog.jar

EXPOSE 3000

CMD ["java", "-jar", "codeflix-admin-catalog.jar"]