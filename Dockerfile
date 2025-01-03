# Did not find Maven:4.0.0
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /order
COPY pom.xml .
COPY src ./src
# Skip tests, run in CI/CD pipeline before building the image
RUN mvn clean package -DskipTests
FROM eclipse-temurin:21
WORKDIR /order
COPY --from=build /order/target/OrderApplication.jar app.jar
EXPOSE 8081
CMD ["java", "-jar", "app.jar"]