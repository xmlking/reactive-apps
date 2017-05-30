FROM openjdk:8
MAINTAINER "Sumanth Chinthagunta"

ARG JAR_NAME
ARG JAVA_OPTS
ARG PORT
VOLUME /tmp
WORKDIR /app
ADD $JAR_NAME app.jar
RUN touch app.jar
EXPOSE $PORT
ENV PORT=$PORT
ENV JAVA_OPTS=$JAVA_OPTS
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Dserver.port=$PORT -Djava.security.egd=file:/dev/./urandom -jar app.jar" ]