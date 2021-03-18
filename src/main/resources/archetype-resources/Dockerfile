#if (${javaVersion} == '1.8')
FROM openjdk:8-jre-alpine 
#end
#if (${javaVersion} == '11')
FROM alpine:3.10.3
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8
ENV JAVA_HOME=/usr/lib/jvm/zulu-11
RUN ZULU_ARCH=zulu11.31.11-ca-jre11.0.3-linux_musl_x64.tar.gz && \
    INSTALL_DIR=$( dirname $JAVA_HOME ) && \
    BIN_DIR=/usr/bin && \
    MAN_DIR=/usr/share/man/man1 && \
    ZULU_DIR=$( basename ${ZULU_ARCH} .tar.gz ) && \
    wget -q https://cdn.azul.com/zulu/bin/${ZULU_ARCH} && \
    mkdir -p ${INSTALL_DIR} && \
    tar -xf ./${ZULU_ARCH} -C ${INSTALL_DIR} && rm -f ${ZULU_ARCH} && \
    mv ${INSTALL_DIR}/${ZULU_DIR} ${JAVA_HOME} && \
    cd ${BIN_DIR} && find ${JAVA_HOME}/bin -type f -perm -a=x -exec ln -s {} . \; && \
    mkdir -p ${MAN_DIR} && \
    cd ${MAN_DIR} && find ${JAVA_HOME}/man/man1 -type f -name "*.1" -exec ln -s {} . \;
#end
#if (${javaVersion} == '16')
FROM openjdk:16-alpine
#end

ARG JAR_NAME="${artifactId}"
ENV HTTP_PORT=8080
ENV LOG_LEVEL=warn
EXPOSE ${HTTP_PORT}
WORKDIR /usr/src/app

COPY ./target/${JAR_NAME}.jar ./app.jar

RUN chmod 777 /usr/src/app

#if (${javaVersion} == '1.8')
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "-XX:+UseG1GC", "-Xmx256m", "-Xms32m", "-XX:MaxHeapFreeRatio=70", "-XX:+ExitOnOutOfMemoryError", "./app.jar"]
#end
#if (${javaVersion} != '1.8')
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "-Xmx256m", "-Xms32m", "-XX:MaxHeapFreeRatio=70", "-XX:+ExitOnOutOfMemoryError", "./app.jar"]
#end