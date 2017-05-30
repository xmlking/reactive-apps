UI App
======
UI Client App

##### Technology stack
* Spring Boot
* Spring WebFlux

##### Features
* Traditional Annotation-based Routes  
* WebClient
* Use of webjars
* Thymeleaf

### Running
> use `./gradlew` instead of `gradle` if you didn't installed `gradle`
```bash
gradle ui-app:bootRun
```
### Testing
```bash
gradle ui-app:test
```
### Building 
```bash
gradle ui-app:build
# build docker image
gradle ui-app:docker
```

### App
http://localhost:8080/