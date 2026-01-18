FROM maven:3.9-eclipse-temurin-24 as builder
WORKDIR /workspace
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:24-jre
WORKDIR /app
USER nobody
COPY --from=builder /workspace/target/app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
