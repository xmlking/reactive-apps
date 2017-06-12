Mongo Data Service
==================
MongoDB Data API

##### Technology stack
* Spring Boot 2.0.0
* Spring WebFlux
* Embedded MongoDB
* Reactive MongoDB Driver

##### Features
* Functional Style Routes

### Run
> use `./gradlew` instead of `gradle` if you didn't installed `gradle`
```bash
gradle mongo-data-service:bootRun
# run with `dev` profile. loads application-dev.properties
SPRING_PROFILES_ACTIVE=dev gradle mongo-data-service:bootRun
```
### Test
```bash
gradle mongo-data-service:test
```
### Build
```bash
gradle mongo-data-service:build
# build docker image
gradle mongo-data-service:docker
```

### API
http://localhost:8081/api/events

http://localhost:8081/api/events/mixit12

http://localhost:8081/api/users

http://localhost:8081/api/users/pamelafox

http://localhost:8081/api/staff

http://localhost:8081/api/staff/sdeleuze

http://localhost:8081/api/guestbook

### EventSource API
http://localhost:8081/sse/guestbook