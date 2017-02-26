# Camel Spring Boot REST DSL / JPA Example

### Introduction

This example demonstrates how to use JPA and Spring Data along with Camel's REST DSL
to expose a RESTful API that performs CRUD operations on a database.

It relies on Swagger to expose the API documentation of the REST service.

### Build

You can build this example using:

```sh
$ mvn package
```

### Run

You can run this example with Maven using:

```sh
$ mvn spring-boot:run
```

Alternatively, you can also run this example using the executable JAR:

```sh
$ java -jar -Dspring.profiles.active=dev target/camel-example-spring-boot-rest-jpa-${project.version}.jar
```

This uses an embedded in-memory HSQLDB database. You can use the default
Spring Boot profile in case you have a MySQL server available for you to test.

When the Camel application runs, you can start the weixin app to access the checkin service

You can then access the REST API directly from your Web browser, e.g.:

- <http://localhost:8080/camel-rest-example/meeting/checkin/1>
- <http://localhost:8080/camel-rest-example/meeting/checkin/All>

The Camel application can be stopped pressing <kbd>ctrl</kbd>+<kbd>c</kbd> in the shell.

### Swagger API

The example provides API documentation of the service using Swagger using
the _context-path_ `camel-rest-jpa/api-doc`. You can access the API documentation
from your Web browser at <http://localhost:8080/camel-rest-example/api-doc>.

