
# Products CRUD API (Spring Boot + JWT + PostgreSQL)

## Overview

Products CRUD API is a secure RESTful backend application built using **Spring Boot**. The system allows management of products and their items while implementing **JWT authentication, refresh token rotation, role‑based authorization, pagination, validation, testing, and Docker deployment**.

This project was created as part of a **Java Backend Developer technical assignment** and follows clean architecture principles.

---

# Tech Stack

| Technology        | Purpose                          |
| ----------------- | -------------------------------- |
| Java 21           | Backend language                 |
| Spring Boot 3     | Application framework            |
| Spring Data JPA   | ORM and database interaction     |
| PostgreSQL        | Primary database                 |
| Spring Security   | Authentication and authorization |
| JWT               | Stateless authentication         |
| Refresh Tokens    | Secure session renewal           |
| Swagger / OpenAPI | API documentation                |
| JUnit 5           | Unit testing                     |
| Mockito           | Mocking for tests                |
| H2 Database       | Integration testing              |
| Docker            | Containerization                 |
| Docker Compose    | Multi‑container orchestration    |

---

# Architecture

The project follows a **layered clean architecture**.

```
src/main/java
 ├── config
 │    ├── CorsConfig
 │    ├── OpenApiConfig
 │    └── PasswordConfig
 │
 ├── controller
 │    ├── ProductController
 │    ├── ItemController
 │    └── AuthController
 │
 ├── dto
 │    ├── ProductRequestDTO
 │    ├── ProductResponseDTO
 │    ├── ItemRequestDTO
 │    ├── ItemResponseDTO
 │    ├── LoginRequest
 │    └── RefreshTokenRequest
 │
 ├── entity
 │    ├── Product
 │    ├── Item
 │    ├── User
 │    └── RefreshToken
 │
 ├── repository
 │    ├── ProductRepository
 │    ├── ItemRepository
 │    ├── UserRepository
 │    └── RefreshTokenRepository
 │
 ├── service
 │    ├── ProductService
 │    ├── ProductServiceImpl
 │    ├── ItemService
 │    ├── ItemServiceImpl
 │    ├── UserService
 │    ├── UserServiceImpl
 │    ├── RefreshTokenService
 │    └── AsyncAuditService
 │
 ├── security
 │    ├── JwtAuthenticationFilter
 │    └── SecurityConfig
 │
 ├── util
 │    └── JwtUtil
 │
 └── exception
      ├── ApiError
      ├── GlobalExceptionHandler
      ├── ResourceNotFoundException
      └── TokenExpiredException
```

---

# Database Design

## Product Table

| Column       | Type      |
| ------------ | --------- |
| id           | bigint    |
| product_name | varchar   |
| created_by   | varchar   |
| created_on   | timestamp |
| modified_by  | varchar   |
| modified_on  | timestamp |

## Item Table

| Column     | Type    |
| ---------- | ------- |
| id         | bigint  |
| product_id | bigint  |
| quantity   | integer |

Relationship:

```
Product (1) ----- (Many) Item
```

Additional security tables:

```
users
refresh_token
```

---

# Security

The API is secured using **Spring Security + JWT**.

### Authentication Flow

1. User registers
2. User logs in
3. API returns:

```
Access Token
Refresh Token
```

4. Client sends access token in requests:

```
Authorization: Bearer <token>
```

5. Refresh token endpoint issues a new access token when the access token expires.

---

# Role Based Authorization

| Role  | Permissions                     |
| ----- | ------------------------------- |
| USER  | View products                   |
| ADMIN | Create, update, delete products |

Example rules:

```
GET /api/v1/products → USER or ADMIN
POST /api/v1/products → ADMIN only
PUT /api/v1/products/{id} → ADMIN only
DELETE /api/v1/products/{id} → ADMIN only
```

---

# API Endpoints

## Authentication

| Method | Endpoint              | Description              |
| ------ | --------------------- | ------------------------ |
| POST   | /api/v1/auth/register | Register new user        |
| POST   | /api/v1/auth/login    | Login and receive tokens |
| POST   | /api/v1/auth/refresh  | Refresh access token     |

---

## Products

| Method | Endpoint              |
| ------ | --------------------- |
| POST   | /api/v1/products      |
| GET    | /api/v1/products      |
| GET    | /api/v1/products/{id} |
| PUT    | /api/v1/products/{id} |
| DELETE | /api/v1/products/{id} |

Pagination supported:

```
GET /api/v1/products?page=0&size=10
```

---

## Items

| Method | Endpoint                           |
| ------ | ---------------------------------- |
| POST   | /api/v1/products/{productId}/items |
| GET    | /api/v1/products/{productId}/items |

---

# Error Handling

Global exception handling ensures consistent API responses.

Example error response:

```
{
  "timestamp": "2026-01-01T10:00:00",
  "status": 404,
  "error": "NOT_FOUND",
  "message": "Product not found",
  "path": "/api/v1/products/1"
}
```

---

# API Documentation

Swagger UI is available at:

```
http://localhost:8080/swagger-ui/index.html
```

---

# Testing

The project includes **unit tests and integration tests**.

### Unit Testing

```
ProductServiceTest
```

Tests:

* Create product
* Get product
* Update product
* Delete product
* Pagination

Tools used:

```
JUnit 5
Mockito
```

### Integration Testing

```
ProductIntegrationTest
```

Uses:

```
SpringBootTest
MockMvc
H2 database
```

---

# Running the Project Locally

## Requirements

* Java 21
* Maven
* PostgreSQL

## Steps

### 1. Clone the repository

```
git clone <repository-url>
```

### 2. Build the project

```
mvn clean package
```

### 3. Run the application

```
mvn spring-boot:run
```

Application will start at:

```
http://localhost:8080
```

---

# Running with Docker

## Build and start containers

```
docker-compose up --build
```

This will start:

```
Spring Boot API
PostgreSQL Database
```

API will be available at:

```
http://localhost:8080
```

---

# Docker Services

| Service         | Port |
| --------------- | ---- |
| Spring Boot API | 8080 |
| PostgreSQL      | 5433 |

---

# Default Users and Roles

The application supports **role-based authentication** using Spring Security.

Two main user roles exist in the system:

| Role  | Description                             |
| ----- | --------------------------------------- |
| ADMIN | Can create, update, and delete products |
| USER  | Can only view products                  |

## Example Admin Login

Use the following credentials to log in as an **administrator**:

```
{
  "username": "admin",
  "password": "123"
}
```

After login the API returns:

```
accessToken
refreshToken
```

Use the access token in requests:

```
Authorization: Bearer <accessToken>
```

## Difference Between ADMIN and Normal USER

| Feature          | ADMIN | USER |
| ---------------- | ----- | ---- |
| View products    | Yes   | Yes  |
| Create product   | Yes   | No   |
| Update product   | Yes   | No   |
| Delete product   | Yes   | No   |
| Access item list | Yes   | Yes  |

Security rules are enforced in **Spring Security configuration**.

---

# Features Implemented

* RESTful API design
* CRUD operations
* Product‑Item relationship
* JWT authentication
* Refresh token rotation
* Role‑based authorization
* Pagination support
* Input validation
* Global exception handling
* Swagger API documentation
* Unit testing
* Integration testing
* Docker containerization

---

# Assignment Evaluation Criteria Coverage

| Criteria                | Status      |
| ----------------------- | ----------- |
| Clean Architecture      | Implemented |
| REST API Design         | Implemented |
| Security Implementation | Implemented |
| Testing Coverage        | Implemented |
| Documentation           | Implemented |
| Docker Deployment       | Implemented |

---

# Author

Java Backend Developer Assignment Implementation

---

# License

This project was created for technical evaluation purposes.
