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
docker run -p 8081:8080 -t reactive/mongo-data-service:0.1.0-SNAPSHOT
docker run -p 8081:8080 -t -t reactive/mongo-data-service:0.1.0-SNAPSHOT
docker run -p 8082:8080 -i -t reactive/stream-service:0.1.0-SNAPSHOT
docker run -p 8080:8080 -i -t reactive/ui-app:0.1.0-SNAPSHOT
# To see list of running containers
docker ps
# To stop a running container
docker stop 81c723d22865
```