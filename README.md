# Products Service
The Products Service is a microservice that provides endpoints for managing products. It allows users to create, retrieve, update, and delete products, and requests can be authenticated using either OAuth 2.0 access tokens or API keys. The service also implements role-based access control (RBAC).

## Prerequisites
Before running the Products Service, ensure you have the following prerequisites installed:

* Java Development Kit (JDK)
* Apache Maven
* PostgreSQL database set up with the required tables (refer to the [AuthEntity class](https://github.com/ChijiokeOkorji/products-service/blob/main/src/main/java/com/example/products/infrastructure/entity/AuthEntity.java) for the required table columns)
* Keycloak (for OAuth2 authentication and authorization)
* Optional: You can also utilize the [OAuth Next App](https://github.com/ChijiokeOkorji/oauth-next-app) for managing user authentication credentials

## Installation
1. Clone the repository:
```bash
git clone https://github.com/ChijiokeOkorji/products-service.git
```
2. Navigate to the project directory:
```bash
cd products-service
```
3. Build the project using Maven:
```bash
mvn clean install
```
4. Set the required environment variables (e.g. `POSTGRESQL_URI`, `POSTGRESQL_USERNAME`, `POSTGRESQL_PASSWORD`, `KEYCLOAK_ISSUER_URI`, `PORT`, `EUREKA_SERVICE_URL`) or provide them in the `application.yaml` file.
5. Run the application:
```bash
java -jar target/products-service-0.0.1-SNAPSHOT.jar
```

## Configuration
The Products Service can be configured using the `application.yaml` file. Below are the configurable properties:

* `spring.application.name`: Name of the application
* `spring.datasource.url`: URL of the PostgreSQL database
* `spring.datasource.username`: Username for the PostgreSQL database
* `spring.datasource.password`: Password for the PostgreSQL database
* `spring.datasource.driver-class-name`: Driver class name for the PostgreSQL database
* `spring.jpa.database-platform`: Database platform dialect for Hibernate
* `spring.jpa.hibernate.ddl-auto`: Hibernate DDL auto strategy
* `spring.security.oauth2.resourceserver.jwt.issuer-uri`: Issuer URI for JWT validation
* `server.port`: Port on which the application runs
* `eureka.client.serviceUrl.defaultZone`: Default zone for Eureka service registry

## Endpoints

### Create Task
Creates a new task.

- **URL**: `/api/v1/products`
- **Method**: `POST`
- **Access Role**: Required (User or Admin)
- **Request Body**: JSON
```json
{
  "productName": "Plate", 
  "categories": ["food", "ceramic"]
}
 ```
- **Response**: JSON
```json
{
  "id": "85596a76-24e2-4929-a1de-4efaaf6d24c3",
  "productName": "Plate",
  "categories": ["food", "ceramic"]
}
 ```

### Get All Tasks
Retrieves all tasks.

- **URL**: `/api/v1/products`
- **Method**: `GET`
- **Access Role**: Required (User or Admin)
- **Response**: Array of JSON objects, each representing a task
```json
[
  {
    "id": "85596a76-24e2-4929-a1de-4efaaf6d24c3",
    "productName": "Plate",
    "categories": ["food", "ceramic"]
  },
  {
    "id": "517c913b-fada-46b1-8a55-bc0a914ad931",
    "productName": "Shoe",
    "categories": ["leather", "fashion"]
  }
]
 ```

### Get Task by ID
Retrieves a task by its ID.

- **URL**: `/api/v1/products/{productId}`
- **Method**: `GET`
- **Access Role**: Required (User or Admin)
- **Response**: JSON
```json
{
  "id": "517c913b-fada-46b1-8a55-bc0a914ad931",
  "productName": "Shoe",
  "categories": ["leather", "fashion"]
}
 ```

### Update Task
Updates an existing task.

- **URL**: `/api/v1/products/{productId}`
- **Method**: `PUT`
- **Access Role**: Required (User or Admin)
- **Request Body**: JSON
```json
{
  "productName": "Shoe",
  "categories": ["leather", "fashion", "brown"]
}
 ```
- **Response**: JSON
```json
{
  "id": "85596a76-24e2-4929-a1de-4efaaf6d24c3",
  "productName": "Shoe",
  "categories": ["leather", "fashion", "brown"]
}
 ```

### Delete Task
Deletes a task by its ID.

- **URL**: `/api/v1/products/{productId}`
- **Method**: `DELETE`
- **Access Role**: Required (Admin)
- **Response**: Empty body with status code 204 (No Content)

## Documentation
For more information about how to use and configure the Products Service Microservice, refer to the following resources:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.5/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.5/reference/htmlsingle/index.html#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.2.5/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)

## Additional Resources
Explore some additional guides to learn more about building microservices with Spring Boot and Spring JPA:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
