FROM openjdk:8-jre-alpine

ARG JAR_NAME="${artifactId}"
ENV HTTP_PORT=8080
ENV LOG_LEVEL=warn
EXPOSE ${HTTP_PORT}
WORKDIR /usr/src/app

COPY ./target/${JAR_NAME}.jar ./app.jar

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "-XX:+UseG1GC", "-Xmx256m", "-Xms32m", "-XX:MaxHeapFreeRatio=70", "./app.jar"]