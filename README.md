# Student Management System – Backend

A RESTful backend application built using Java, Spring Boot, Spring Security, JWT Authentication, and MySQL. It provides secure APIs to manage student records with role-based access control.

## Live Project

- **Frontend:** https://student-management-frontend-two-swart.vercel.app
- **Backend API:** https://student-management-backend-production-8c7e.up.railway.app
- **Swagger UI:** https://student-management-backend-production-8c7e.up.railway.app/swagger-ui/index.html

## Technologies Used

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- MySQL
- JWT Authentication
- BCrypt Password Encryption
- Maven
- Swagger / OpenAPI
- Railway (Deployment)
- Git and GitHub

## Features

- User registration and login
- JWT-based authentication
- Role-based authorization (ADMIN and USER)
- Secure password encryption using BCrypt
- Student CRUD operations
- Input validation
- Global exception handling
- DTO-based request and response handling
- Standardized API responses
- API documentation using Swagger UI
- MySQL database integration

## User Roles and Permissions

| Operation | ADMIN | USER |
|---|---|---|
| View Students | Yes | Yes |
| Add Student | Yes | No |
| Update Student | Yes | No |
| Delete Student | Yes | No |

## API Endpoints

### Authentication APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Authenticate and receive JWT |

### Student APIs

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/students` | Get all students |
| GET | `/api/students/{id}` | Get student by ID |
| POST | `/api/students` | Add a new student |
| PUT | `/api/students/{id}` | Update student |
| PATCH | `/api/students/{id}` | Partially update student |
| DELETE | `/api/students/{id}` | Delete student |

## Security

- Spring Security protects the API endpoints.
- JWT tokens are used for authenticated requests.
- BCrypt is used for password hashing.
- ADMIN and USER roles have different permissions.
- JWT tokens are sent using the Authorization header:

`Authorization: Bearer <your-token>`

## Database

MySQL is used to store application data.

Database configuration is managed through environment variables rather than exposing database credentials in the source code.

## Project Architecture

```text
React Frontend
      |
      | HTTP Requests + JWT
      |
Spring Boot REST API
      |
      | Spring Security
      | Service Layer
      | Repository Layer
      |
    MySQL
```

## Screenshots

### Swagger API Documentation

![Swagger Overview](screenshots/swagger-overview.png)

### Authentication APIs

![Authentication APIs](screenshots/auth-endpoints.png)

### Student APIs

![Student APIs](screenshots/student-endpoints.png)

### JWT Authorization

![JWT Authorization](screenshots/jwt-authorization.png)

## Deployment

- Backend deployed on Railway.
- MySQL database hosted on Railway.
- Frontend deployed on Vercel.
- Environment variables are configured securely in the deployment platform.

## Learning Outcomes

Through this project, I gained practical experience in:

- Building REST APIs using Spring Boot.
- Implementing JWT authentication and Spring Security.
- Working with MySQL and Spring Data JPA.
- Implementing role-based authorization.
- Handling exceptions and validations.
- Documenting APIs using Swagger.
- Deploying a full-stack application.

## Author

**Mahendra Chowdhary**

GitHub: https://github.com/mahendra-chowdhary
