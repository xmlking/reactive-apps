FROM openjdk:jre-alpine

RUN mkdir /app
ADD build/libs/stream-service-0.1.0-SNAPSHOT.jar /app

CMD java -jar -Xmx512m /app/stream-service-0.1.0-SNAPSHOT.jar