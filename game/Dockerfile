FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY game/pom.xml .
COPY game/src ./src

RUN ./mvnw dependency:go-offline -B

# Comando para ejecutar el JAR de la app
CMD ["java", "-jar", "target/game-0.0.1.jar"]