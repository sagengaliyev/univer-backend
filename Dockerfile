FROM gradle:jdk17-alpine as build
WORKDIR /app/gradle
COPY . .
RUN chmod 777 gradlew &&  ./gradlew assemble

FROM maven:3.8.6-eclipse-temurin-17-alpine
COPY --from=build /app/gradle/build/libs/univer-backend-0.0.1-SNAPSHOT.jar univer-backend-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "univer-backend-0.0.1-SNAPSHOT.jar"]
