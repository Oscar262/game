# Etapa 1: Compila el proyecto con Maven
FROM maven:3.8.5-eclipse-temurin-11 AS build
WORKDIR /game/game

# Copia todo el código fuente
COPY . .

RUN ls -la /game/game

# Compila el proyecto y genera el JAR
RUN /game sudo mvn clean package -DskipTests

# Etapa 2: Imagen final con solo el JAR
FROM eclipse-temurin:11-jdk-jammy
WORKDIR /game/game

# Copia el JAR desde la etapa de construcción
COPY --from=build /game/game/target/*.jar game.jar

# Expone el puerto estándar para Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "game.jar"]