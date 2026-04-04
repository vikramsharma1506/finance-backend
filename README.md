# 🏦 Finance Data Processing and Access Control Backend

A RESTful API backend for a Finance Dashboard built with
Spring Boot, MySQL, JWT Security, and Role-Based Access Control (RBAC).

## 🛠 Tech Stack
| Technology | Purpose |
|-----------|---------|
| Java 17 | Core language |
| Spring Boot 3.2 | Backend framework |
| Spring Security + JWT | Authentication & Authorization |
| MySQL + JPA/Hibernate | Database & ORM |
| Lombok | Reduce boilerplate code |
| Maven | Dependency management |

## 🔐 Role-Based Access Control

| Role | Permissions |
|------|-------------|
| VIEWER | View financial records only |
| ANALYST | View records + access dashboard summaries and trends |
| ADMIN | Full access: manage records, users, dashboard |

## 🚀 Quick Start

### Prerequisites
- Java 17+
- MySQL 8+
- Maven 3.8+

### Setup

1. Clone the repository:
   git clone https://github.com/YOUR_USERNAME/finance-backend.git
   cd finance-backend

2. Create the database:
   CREATE DATABASE finance_db;

3. Configure database credentials in:
   src/main/resources/application.properties
   Change spring.datasource.password to your MySQL password.

4. Run the application:
   mvn spring-boot:run

5. The server starts at: http://localhost:8080

### Demo Credentials (auto-seeded on first run)
| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | ADMIN |
| analyst | analyst123 | ANALYST |
| viewer | viewer123 | VIEWER |

## 📡 API Endpoints

### 🔓 Authentication — Public

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/auth/register` | Register a new user account |
| `POST` | `/api/auth/login` | Login and receive a JWT token |

---

### 👥 User Management — ADMIN only

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/users` | Get all registered users |
| `GET` | `/api/users/{id}` | Get a specific user by ID |
| `PUT` | `/api/users/{id}/role?role=ANALYST` | Update a user's role |
| `PUT` | `/api/users/{id}/toggle-status` | Activate or deactivate a user |
| `DELETE` | `/api/users/{id}` | Permanently delete a user |

---

### 💰 Financial Records — Role restricted

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| `GET` | `/api/records` | VIEWER, ANALYST, ADMIN | Get all active records |
| `GET` | `/api/records?type=INCOME` | VIEWER, ANALYST, ADMIN | Filter by transaction type |
| `GET` | `/api/records?category=Salary` | VIEWER, ANALYST, ADMIN | Filter by category |
| `GET` | `/api/records?startDate=2024-01-01&endDate=2024-03-31` | VIEWER, ANALYST, ADMIN | Filter by date range |
| `GET` | `/api/records/{id}` | VIEWER, ANALYST, ADMIN | Get a single record by ID |
| `POST` | `/api/records` | ADMIN only | Create a new financial record |
| `PUT` | `/api/records/{id}` | ADMIN only | Update an existing record |
| `DELETE` | `/api/records/{id}` | ADMIN only | Soft-delete a record |

---


### Dashboard (ANALYST+)
GET /api/dashboard/summary — Total income, expenses, net balance, category totals

GET /api/dashboard/trends  — Monthly income and expense trends

## 🔑 Using the API

1. Login to get your token:
POST /api/auth/login
{ "username": "admin", "password": "admin123" }

2. Use the token in all protected requests:
Authorization: Bearer <your_token_here>

## ✅ Features
- JWT-based stateless authentication
- Role-based access control on every endpoint
- Financial records CRUD with soft delete
- Filtering by type, category, date range
- Dashboard summary with aggregated analytics
- Monthly trend analysis
- Input validation with meaningful error messages
- Global exception handling 

## 🏗 Project Structure
src/main/java/com/finance/finance_backend/

├── config/       Security, Jackson

├── controller/   Auth, User, FinancialRecord, Dashboard

├── dto/          Request/Response objects

├── entity/       User, FinancialRecord, Role, TransactionType

├── exception/    GlobalExceptionHandler

├── repository/   JPA repositories with custom queries

├── security/     JWT utilities and filters

└── service/      Business logic layer


## 📝 Assumptions Made
- Soft delete is used for financial records (deleted flag) to preserve audit history
- JWT tokens expire after 24 hours
- Admin users are trusted to assign roles at registration for demo purposes
- All monetary amounts are stored as DECIMAL(15,2)
