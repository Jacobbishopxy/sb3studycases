# Spring Boot 3 Study cases

## Building a RESTful Web Service

最简单的 RESTFUL Web Service，包含两个文件：

- Greeting.java
- GreetingController.java

测试：

```sh
./mvnw spring-boot:run
```

生产：

```sh
./mvnw clean package
java -jar target/sb3studycases-0.0.1-SNAPSHOT.jar
```
