# Usamos imagen base de OpenJDK 17 (elige la versión que uses)
FROM eclipse-temurin:17-jdk-jammy

# Copiamos el jar generado dentro del contenedor
COPY target/*.jar app.jar

# Exponemos el puerto 8080 (el que usa Spring Boot por defecto)
EXPOSE 8080

# Comando para arrancar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]
