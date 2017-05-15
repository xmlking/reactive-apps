FROM openjdk:jre-alpine

RUN mkdir /app
ADD build/libs/mongo-data-service-0.1.0-SNAPSHOT.jar /app

CMD java -jar -Djava.security.egd=file:/dev/./urandom -Xmx512m /app/mongo-data-service-0.1.0-SNAPSHOT.jar