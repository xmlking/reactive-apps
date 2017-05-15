FROM openjdk:jre-alpine

RUN mkdir /app
ADD build/libs/ui-app-0.1.0-SNAPSHOT.jar /app

CMD java -jar -Xmx512m /app/ui-app-0.1.0-SNAPSHOT.jar