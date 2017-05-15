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

### Running
> use `./gradlew` instead of `gradle` if you didn't installed `gradle`
```bash
gradle :stream-service:bootRun
```
### Testing
```bash
gradle :stream-service:test
```
### Building 
```bash
gradle :stream-service:build
```

### API
http://localhost:8082/sse/quotes

### Test testing tool for socket.io

http://amritb.github.io/socketio-client-tool/

Connect URL: http://localhost:8082/websocket/echo