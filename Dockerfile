FROM amazoncorretto:21-alpine-jdk
COPY target/*.jar /app.jar
WORKDIR /
ENV spring.profiles.active=docker
ENV TZ=Europe/Moscow
ENV JAVA_OPTS=""
ENV JAVA_RAM_OPTS="-XX:InitialRAMPercentage=80.0 -XX:MaxRAMPercentage=80.0"
ENTRYPOINT exec java $JAVA_OPTS $JAVA_RAM_OPTS -jar /app.jar
