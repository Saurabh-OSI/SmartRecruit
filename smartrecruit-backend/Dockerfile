FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw

COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/smartrecruit-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["sh", "-c", "if [ -n \"$DB_URL\" ]; then raw_url=\"${DB_URL#jdbc:}\"; raw_url=\"${raw_url#postgresql://}\"; raw_url=\"${raw_url#*@}\"; export DB_URL=\"jdbc:postgresql://$raw_url\"; fi; exec java -jar app.jar"]
