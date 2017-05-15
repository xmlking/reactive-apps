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

### Running
> use `./gradlew` instead of `gradle` if you didn't installed `gradle`
```bash
gradle :mongo-data-service:bootRun
```
### Testing
```bash
gradle :mongo-data-service:test
```
### Building 
```bash
gradle :mongo-data-service:build
```

### API
http://localhost:8081/api/events
http://localhost:8081/api/events/mixit12
http://localhost:8081/api/users
http://localhost:8081/api/users/pamelafox
http://localhost:8081/api/staff
http://localhost:8081/api/staff/sdeleuze