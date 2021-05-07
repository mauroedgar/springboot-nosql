# Quick Start Java & MongoDB Project

## Supported versions:

- Java 8
- Spring boot 2.4.2
- MongoDB 4.4.3
- MongoDB Java driver 4.1.1
- Maven 3.6.3
- Swagger 3.0.0

## MongoDB Atlas

- Using a Free Tier Cluster on [MongoDB Atlas](https://www.mongodb.com/cloud/atlas).
- Default MongoDB URI `spring.data.mongodb.uri` in the `application.properties` file.

## Commands

- Start the server in a console with `mvn spring-boot:run`.
- If you add some Unit Tests, you would start them with `mvn clean test`.
- Start the end to end tests with `mvn clean integration-test`.
- Build the project with : `mvn clean package`.
- Run the project with the jar and the embedded Tomcat: `java -jar target/customers-api-1.0.1.jar`

## Swagger 3
- Swagger 3 is already configured in this project using `SpringFoxConfig.java`.
- The Swagger UI can be seen at [http://localhost:9001/swagger-ui/index.html](http://localhost:9001/swagger-ui/index.html).
- The Swagger API documentation 2.0 is at [http://localhost:9001/v2/api-docs](http://localhost:9001/v2/api-docs).
- The Open API documentation 3.0.3 is at [http://localhost:9001/v3/api-docs](http://localhost:9001/v3/api-docs).
- You can also try the entire REST API directly from the Swagger interface!

## Features showcase
This project showcases several features of MongoDB:

- Implementation of basic CRUD queries. See `CustomerDAOImpl.java`.
- MongoDB typed collection with automatic mapping to POJOs using codecs: See `ConfigurationSpring.java`.
- How to manipulate correctly ObjectidId across, the REST API, the POJOs and the database itself. See the main trick in `Customer.java`.

## Author
- Mauro Sousa
