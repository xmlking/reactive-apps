Docker
======

### Commands

```bash
# To see list of images
docker images
docker images -a
# To delete an image 
docker rmi  eb46b3df6e36 
docker rmi  eb46b3df6e36 -f
# To run an image 
docker run -p 8081:8081 -i -t reactive/mongo-data-service:0.1.0-SNAPSHOT
docker run -p 8082:8082 -i -t reactive/stream-service:0.1.0-SNAPSHOT
docker run -p 8080:8080 -e "app.mongoApiUrl=http://localhost:8081"  -e "app.streamApiUrl=http://localhost:8082" -i -t reactive/ui-app:0.1.0-SNAPSHOT
# Using Spring Profiles
$ docker run -e "SPRING_PROFILES_ACTIVE=prod" -p 8081:8081 -i -t reactive/stream-service:0.1.0-SNAPSHOT
# Debugging the application in a Docker container
$ docker run -e "JAVA_OPTS=-agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n" -p 8081:8081 -p 5005:5005 -i -t reactive/stream-service:0.1.0-SNAPSHOT
# To see list of running containers
docker ps
# To stop a running container
docker stop 81c723d22865

```