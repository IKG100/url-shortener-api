# URL Shortener. Rest API.

This project provides a simple API for URL shortening, user authentication, and statistics tracking.

## Table of Contents

1. [Project Description](#project-description)
2. [Technologies Used](#technologies-used)
3. [API Documentation](#api-documentation)
    - [User Authentication](#user-authentication)
    - [URL Shortener](#url-shortener)
    - [URL Statistics](#url-statistics)
4. [Setup Instructions](#setup-instructions)

---

## Project Description

The URL Shortener Project provides the following features:

- User Registration & Authentication
- URL Shortening
- URL Statistics

---

## Technologies Used

- Java 21
- Spring Boot
- Spring Data
- Spring MVC
- Spring Security
- JWT
- Flyway
- Lombok
- Swagger OpenAPI
- JUnit
- Mockito
- PostgreSQL
- Docker

---

## API Documentation

### User Authentication

1. **Register a new user**
    - **Endpoint**: `POST /api/v1/auth/register`
    - **Request Body**: `RegisterUserRequest`
    - **Response**: `RegisterUserResponse`
    - **Response Codes**:
        - `201`: User registered successfully
        - `400`: Incorrect input data
        - `409`: User already exists
- **Request Example (`RegisterUserRequest`)**:
```json
{
  "login": "user123",
  "email": "test@email.com",
  "password": "passWord123"
}
```
- **Response Example (`RegisterUserResponse`)**:
```json
{
  "login": "user123",
  "email": "test@email.com"
}
```

2. **Authentication user**
    - **Endpoint**: `POST /api/v1/auth/login`
    - **Request Body**: `AuthUserRequest`
    - **Response**: `AuthUserResponse`
    - **Response Codes**:
        - `201`: User authenticated successfully
        - `400`: Incorrect input data
        - `401`: User not authenticated
- **Request Example (`AuthUserRequest`)**:
```json
{
   "identifier": "test@email.com",
   "password": "passWord123"
}
```
- **Response Example (`AuthUserResponse`)**:
```json
{
   "accessToken": "accessToken", 
   "refreshToken": "refreshToken"
}
```

3. **Refresh JWT token**

- **Endpoint**: `POST /api/v1/auth/refresh`
    - **Request Body**: `RefreshTokenRequest`
    - **Response**: `RefreshTokenResponse`
    - **Response Codes**:
        - `200`: JWT token refreshed successfully
        - `401`: JWT token is not valid"
- **Request Example (`RefreshTokenReques`)**:
```json
{
  "refreshToken": "refreshToken"
}
```
- **Response Example (`RefreshTokenResponse`)**:
```json
{
   "accessToken": "accessToken", 
   "refreshToken": "refreshToken"
}
```

### URL Shortener

1. **Shorten a long URL**
    - **Endpoint**: `POST /api/v1/url`
    - **Request Body**: `GetShortUrlRequest`
    - **Response**: `UrlResponse`
    - **Response Codes**:
        - `201`: Short URL created
        - `400`: Incorrect input data
        - `403`: Forbidden
- **Request Example (`GetShortUrlRequest`)**:
```json
{
  "longUrl": "https://www.youtube.com/",
  "expiresAt": "2028-08-30T00:00:00.000Z"
}
```
- **Response Example (`UrlResponse`)**:
```json
{
  "url": {
  "shortUrlCode": "GO0Fqb",
  "longUrl": "https://www.youtube.com/",
  "createdAt": "2025-04-16T17:41:02.533925",
  "expiresAt": "2028-08-30T00:00:00",
  "author": "user123"
  }
}
```

2. **Retrieve a long URL from a short URL**
    - **Endpoint**: `POST /api/v1/url/{shortUrlCode}`
    - **Response**: `UrlResponse`
    - **Response Codes**:
        - `200`: Long URL returned
        - `400`: Incorrect input data
        - `404`: URL not found
- **Response Example (`UrlResponse`)**:
```json
{
"url": {
"longUrl": "https://www.youtube.com/"
  }
}
```

3. **Update a shortened URL**
    - **Endpoint**: `PATCH /api/v1/url`
    - **Request Body**: `UpdateUrlRequest`
    - **Response**: `UrlResponse`
    - **Response Codes**:
        - `200`: URL updated
        - `400`: Incorrect input data
        - `403`: Forbidden
        - `404`: URL not found
- **Response Example (`UrlResponse`)**:
```json
{
"url": {
"shortUrlCode": "QPx7nkSi",
"longUrl": "https://www.youtube.com/",
"createdAt": "2025-04-16T17:41:02.533925",
"expiresAt": "2028-08-30T00:00:00",
"author": "user123"
  }
}
```

4. **Delete a shortened URL**
    - **Endpoint**: `DELETE /api/v1/url/{shortUrlCode}`
    - **Response Codes**:
        - `204`: URL deleted
        - `403`: Forbidden
        - `404`: URL not found

### URL Statistics

1. **Get all URLs for a user**
    - **Endpoint**: `GET /api/v1/url/all`
    - **Response**: `StatsListUrlResponse`
    - **Response Codes**:
        - `200`: List of URLs returned
        - `403`: Forbidden
        - `404`: No URLs found
- **Response Example(StatsListUrlResponse)**:
```json
{
   "totalVisits": 1140,
   "urls": [
      {
         "visits": 948,
         "shortUrlCode": "skY8gWu",
         "longUrl": "https://www.youtube.com",
         "createdAt": "2025-04-16T14:54:13.248Z",
         "expiresAt": "2028-08-30T00:00:00.000Z",
         "isActive": true
      },
      {
         "visits": 54,
         "shortUrlCode": "aB12cD3",
         "longUrl": "https://www.wikipedia.org",
         "createdAt": "2025-03-10T09:23:47.128Z",        
         "isActive": false
      },
      {
         "visits": 73,
         "shortUrlCode": "zX9mVnQ",
         "longUrl": "https://news.ycombinator.com",
         "createdAt": "2025-02-22T11:05:32.500Z",
         "expiresAt": "2025-04-31T00:00:00.000Z",
         "isActive": false
      },
      {
         "visits": 65,
         "shortUrlCode": "LmT7oPz",
         "longUrl": "https://stackoverflow.com",
         "createdAt": "2025-01-15T08:12:00.000Z",
         "expiresAt": "2029-05-15T00:00:00.000Z",
         "isActive": true
      }
   ]
}
```

2. **Get active (non-expired) URLs for a user**
    - **Endpoint**: `GET /api/v1/url/active`
    - **Response**: `StatsListUrlResponse`
    - **Response Codes**:
        - `200`: List of active URLs returned
        - `403`: Forbidden
        - `404`: No active URLs found
- **Response Example(StatsListUrlResponse)**:
```json
{
   "totalVisits": 1140,
   "urls": [
      {
         "visits": 948,
         "shortUrlCode": "skY8gWu",
         "longUrl": "https://www.youtube.com",
         "createdAt": "2025-04-16T14:54:13.248Z",
         "expiresAt": "2028-08-30T00:00:00.000Z",
         "isActive": true
      },
      {
         "visits": 54,
         "shortUrlCode": "aB12cD3",
         "longUrl": "https://www.wikipedia.org",
         "createdAt": "2025-03-10T09:23:47.128Z",
         "expiresAt": "2026-01-01T00:00:00.000Z",
         "isActive": true
      },
      {
         "visits": 73,
         "shortUrlCode": "zX9mVnQ",
         "longUrl": "https://news.ycombinator.com",
         "createdAt": "2025-02-22T11:05:32.500Z",
         "expiresAt": "2027-12-31T00:00:00.000Z",
         "isActive": true
      },
      {
         "visits": 65,
         "shortUrlCode": "LmT7oPz",
         "longUrl": "https://stackoverflow.com",
         "createdAt": "2025-01-15T08:12:00.000Z",
         "expiresAt": "2029-05-15T00:00:00.000Z",
         "isActive": true
      }
   ]
}
```

3. **Get visit count for a specific short URL**
    - **Endpoint**: `GET /api/v1/url/visits/{shortUrlCode}`
    - **Response**: `StatsVisitsUrlResponse`
    - **Response Codes**:
        - `200`: Visit count returned
        - `403`: Forbidden
        - `404`: URL not found
- **Response Example(StatsVisitsUrlResponse)**:
```json
{
"visits": 1057
}
```

## Setup Instructions

### Development Environment (dev profile, port: 8080)

- **Start the development database stack**:  
docker-compose -p url_shortener_api_devstack -f ./docker-compose-dev.yml up --build

- **Run the application**:  
mvn clean spring-boot:run -Pdev

### Test Environment (test profile)

- **Start tests (with test database)**:  
mvn clean test -Dspring.profiles.active=test

### Production Environment (prod profile, port: 9999)

- **Build the application**:  
mvn clean package -Pdev

- **Start the full application stack**:  
docker-compose -p url_shortener_api_prodstack -f ./docker-compose-prod.yml up --build

## Swagger UI

You can access the REST API documentation via Swagger once the full application stack is running 
with the corresponding profile:

- 🛠️ [Dev Environment](http://localhost:8080/swagger-ui/index.html)
- 🚀 [Prod Environment](http://localhost:9999/swagger-ui/index.html)

## Author

Developed by [Ihor Kolybaba](https://github.com/IKG100)


