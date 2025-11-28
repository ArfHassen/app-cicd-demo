# Dockerfile
FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp
ARG JAR_FILE=target/app-demo-spring.jar
COPY ${JAR_FILE} app-demo-spring.jar
ENTRYPOINT ["java","-jar","/app-demo-spring.jar"]
