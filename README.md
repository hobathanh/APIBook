# SpringAPIBook

## Build status

[![CircleCI](https://dl.circleci.com/status-badge/img/gh/hobathanh/APIBook/tree/setup.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/hobathanh/APIBook/tree/setup)

## Test coverage

[![codecov](https://codecov.io/gh/hobathanh/APIBook/branch/master/graph/badge.svg?token=2ATX52JL18)](https://codecov.io/gh/hobathanh/APIBook)

---

## Introduction

_The goal of this exercise is to write a web API using Spring Boot Framework. The API allows a frontend to handle books
in a library._

---

## Step

1. Initialize Repository with the following stuff were correctly set up
    - Git ignore
    - Circle CI
    - Code COV
    - Flyway
    - SpringBoot
    - Some libraries (eg: Loombok)
2. Integrate Swagger
    - Add an empty API (eg GET api/v1/books)
    - Add library
    - Check the Swagger UI and make sure it works correctly
3. Add database
    - Add migration files to create the database tables following the requirement
    - Add migration file to create two roles
    - Add migration file to create an admin
    - Run the application with docker started
    - Make sure the database tables were created with the correct structure and relationships
    - Check the roles were created
4. Create CRUD for users
5. Create CRUD for books
6. Add Login endpoint
7. Integrate security and define role foreach endpoints
8. Create POSTMAN collections
9. Deployment

---

## Models

### Role

| Property | Required |
|:--------:|:--------:|
|    id    |    ✓     |
|   name   |    ✓     |

**We have fixed 2 roles:**

- Admin
- Contributor

### User

| Property  | Required |
|:---------:|:--------:|
|    id     |    ✓     |
| username  |    ✓     |
| password  |    ✓     |
| firstName |    ✗     |
| lastName  |    ✗     |
|  enabled  |    ✓     |
|  avatar   |    ✗     |
|  role_id  |    ✓     |

### Book

|  Property   | Required |
|:-----------:|:--------:|
|     id      |    ✓     |
|    title    |    ✓     |
|   author    |    ✓     |
| description |    ✗     |
| created_at  |    ✓     |
| updated_at  |    ✓     |
|    image    |    ✗     |
|   user_id   |    ✓     |

---

## Endpoints

### Auths

Base URL: `api/v1/auths`

- POST: (Allow anonymous)
    - Login with username/password
    - Request: username/password
    - Response
        - Success: An object with JWT token
        - Failed: 401 error

### Profiles

Base URL: `api/v1/profiles`. All endpoints required logged in

- GET (Required authenticated)
    - Get the current user's information
- PUT
    - Update current user's profile

### Users

Base URL: `api/v1/users`. All endpoints required ADMIN role

- POST: Create user
- GET: Get all users
- GET `{id}` Get user by id
- GET: Find users by username, firstname, lastname
- PUT: Update user information
- DELETE: Delete a specific user

### Books

Base URL: `api/v1/books`.

- POST: create a book (require logged in)
- GET: Get all available books (allow anonymous)
- GET: Get book by ID (allow anonymous)
- GET: Find books by title, author, description
- PUT: Update a book
    - If user is ADMIN --> Allow
    - If user is Contributor --> Check if the user is the book's owner
- DELETE: delete a book (Check role like PUT)