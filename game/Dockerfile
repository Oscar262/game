FROM eclipse-temurin:17-jdk

WORKDIR /game

COPY game/pom.xml .
COPY game/mvnw .
COPY game/.mvn .mvn
COPY game/src ./src

RUN chmod +x ./mvnw

RUN ./mvnw dependency:go-offline -B
RUN ./mvnw clean package -DskipTests

CMD ["sh", "-c", "java -jar target/*.jar"]
