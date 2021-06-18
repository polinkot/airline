FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} airline.jar
ENTRYPOINT ["java","-jar","/airline.jar"]
CMD echo 'Hello world'