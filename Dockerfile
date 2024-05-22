FROM openjdk:17-alpine
COPY build/libs/*.jar projectt2-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "projectt2-0.0.1-SNAPSHOT.jar"]