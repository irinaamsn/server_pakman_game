FROM openjdk:20
LABEL authors="irinaamsn, adel grishin"

WORKDIR /app

COPY build/libs/server-0.0.1-SNAPSHOT.jar server.jar

CMD ["java", "-jar", "server.jar"]