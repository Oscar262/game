# Usamos JDK 17 base
FROM eclipse-temurin:17-jdk

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos pom.xml para cachear dependencias
COPY pom.xml .

# Si tienes Maven Wrapper, descarga dependencias offline para acelerar build
RUN ./mvnw dependency:go-offline -B
# Si no tienes mvnw, usa: RUN mvn dependency:go-offline -B

# Copiamos el código fuente completo
COPY src ./src

# Construimos el proyecto y generamos el JAR, sin ejecutar tests
RUN ./mvnw clean package -DskipTests
# O RUN mvn clean package -DskipTests

# Puerto que expone la app (ajusta si usas otro)
EXPOSE 8080

# Comando para ejecutar el JAR de la app
CMD ["java", "-jar", "target/game-0.0.1.jar"]