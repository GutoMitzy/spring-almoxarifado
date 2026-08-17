FROM maven:4.0.0-rc-5-amazoncorretto-25 AS build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app
RUN mvn clean install -DskipTests

FROM amazoncorretto:25-jdk

COPY --from=build /app/target/almoxarifado-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app

EXPOSE 8082

CMD ["java", "-jar", "app.jar"]
