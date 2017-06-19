Stream Service
=============
Stream API

##### Technology stack
* Spring Boot 2.0.0
* Spring WebFlux
* Server-Sent Events (SSE) 

##### Features
* Server-Sent Events (SSE)
* Functional Style Routes

### Run
> use `./gradlew` instead of `gradle` if you didn't installed `gradle`
```bash
gradle stream-service:bootRun
```
### Test
```bash
gradle stream-service:test
```
### Build
```bash
gradle stream-service:build
# continuous build with `-t`. 
gradle -t stream-service:build
# build docker image
gradle stream-service:docker
```

### API
http://localhost:8082/sse/quotes


http://localhost:8082/sse/fibonacci

### Test testing tool for socket.io

http://amritb.github.io/socketio-client-tool/

Connect URL: http://localhost:8082/websocket/echo [TODO]