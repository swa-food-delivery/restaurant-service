FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} restaurants.jar
ENTRYPOINT ["java", "-jar", "/restaurants.jar"]

LABEL org.label-schema.name="restaurants-service"
LABEL org.label-schema.description="Docker image for restaurant service"
LABEL org.label-schema.docker.cmd="docker run --network='host' restaurants-service"