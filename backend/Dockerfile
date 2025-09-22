# ---- Build stage ----
FROM amazoncorretto:25-alpine-jdk AS builder

WORKDIR /app

# Copy Maven wrapper and pom.xml first (caching)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN ./mvnw dependency:go-offline

# Copy the rest of the source code
COPY src src

# Build the application JAR
RUN ./mvnw clean package -DskipTests

# ---- Run stage ----
FROM amazoncorretto:25-alpine-jdk

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
