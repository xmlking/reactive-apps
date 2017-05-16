FROM openjdk:alpine
MAINTAINER "Sumanth Chinthagunta"

ARG JAVA_OPTS
ARG PORT
#VOLUME /tmp
RUN mkdir /app
ADD stream-service-0.1.0-SNAPSHOT.jar /app/app.jar
RUN touch /app/app.jar
EXPOSE $PORT
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar" ]