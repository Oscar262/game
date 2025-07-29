FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY game/pom.xml .
COPY game/src ./src

RUN ./mvnw dependency:go-offline -B

CMD ["bash"]
