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

### Authentication (Public)
POST /api/auth/register — Register a new user
POST /api/auth/login    — Login and receive JWT token

### Users (ADMIN only)
GET    /api/users              — List all users
GET    /api/users/{id}         — Get user by ID
PUT    /api/users/{id}/role    — Update user role
PUT    /api/users/{id}/toggle-status — Activate or deactivate user
DELETE /api/users/{id}         — Delete user

### Financial Records
GET    /api/records                        — All records (VIEWER+)
GET    /api/records?type=INCOME            — Filter by type
GET    /api/records?category=Salary        — Filter by category
GET    /api/records?startDate=2024-01-01&endDate=2024-03-31 — Filter by date
GET    /api/records/{id}                   — Get by ID (VIEWER+)
POST   /api/records                        — Create record (ADMIN)
PUT    /api/records/{id}                   — Update record (ADMIN)
DELETE /api/records/{id}                   — Soft-delete record (ADMIN)

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
