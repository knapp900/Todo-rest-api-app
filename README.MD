# Todo REST API Application

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-25A162?style=for-the-badge&logo=junit5&logoColor=white)

Welcome to the Todo REST API Application! This project is a RESTful API designed to manage tasks, providing users with CRUD operations on task lists, task management, and user authentication via JWT.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Running the Application](#running-the-application)
    - [Running Tests](#running-tests)

- [Contact](#contact)

## Features

- User registration and JWT-based authentication
- CRUD operations for task lists
- Task management within lists (CRUD operations)
- Filter tasks based on completion status
- Pagination support for task lists

## Technologies Used

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Security**
- **JWT (JSON Web Token)**
- **Hibernate**
- **Maven**
- **JUnit 5**
- **Mockito**
- **Docker**

## Getting Started

### Prerequisites

- Java 17
- Maven
- Docker

### Installation

1. **Clone the repository:**

    ```sh
    git clone https://github.com/knapp900/Todo-rest-api-app.git
    cd todo-restapi-app
    ```

2. **Run PostgreSQL in Docker:**

    ```sh
    docker run --name todo-db -p 5432:5432 -e POSTGRES_USER=todo -e POSTGRES_PASSWORD=password -e POSTGRES_DB=todo postgres:16
    ```

3. **Configure the database:**

   Update the `application.properties` file in the `src/main/resources` directory with your database credentials.

    ```properties
    spring.application.name=todo rest-api app
    spring.datasource.url=jdbc:postgresql://0.0.0.0:5432/todo
    spring.datasource.username=todo
    spring.datasource.password=password
    ```

### Running the Application

1. **Build the project:**

    ```sh
    mvn clean install
    ```

2. **Run the application:**

    ```sh
    mvn spring-boot:run
    ```

   The application will start on `http://localhost:8081`.

### Running Tests

To run the tests, use the following command:

```sh
mvn test
```

### Contact
Feel free to reach out if you have any questions or suggestions!

- **Email: knapp900@gmail.com**
- **LinkedIn: [Arthur Knapp](#https://www.linkedin.com/in/arthur-knapp-808a6a166/)**
- **GitHub: [Knapp900](#https://github.com/knapp900)**
