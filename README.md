<div align="center">

# 🏦 FinanceGuard — Finance Data Processing and Access Control Backend

### A production-ready RESTful API backend for a Finance Dashboard System
### Built with Java 17 · Spring Boot 3.2 · MySQL · JWT Security · Docker

[![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2-green?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)](https://www.mysql.com/)
[![JWT](https://img.shields.io/badge/JWT-Auth-black?style=for-the-badge&logo=jsonwebtokens)](https://jwt.io/)
[![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED?style=for-the-badge&logo=docker)](https://www.docker.com/)
[![Railway](https://img.shields.io/badge/Deployed-Railway-8B5CF6?style=for-the-badge&logo=railway)](https://railway.app/)

---

## 🌐 Live API URL
## `https://finance-backend-guard-production.up.railway.app`


---

## 📖 About the Project

**FinanceGuard** is a backend system for a **Finance Dashboard** where different
users interact with financial records based on their assigned role.

The system handles:
- **User Authentication** using JWT — stateless and secure
- **Role-Based Access Control** — VIEWER, ANALYST, and ADMIN roles
- **Financial Records Management** — income and expense transactions with full CRUD
- **Dashboard Analytics** — total income, expenses, net balance, category totals, monthly trends
- **Data Security** — BCrypt password hashing, soft delete for audit history

> This project demonstrates clean backend architecture, proper security
> implementation, business logic separation, and real-world financial
> system design patterns.

---

## 🛠 Tech Stack

| Layer | Technology | Purpose |
|-------|-----------|---------|
| Language | Java 17 | Core programming language |
| Framework | Spring Boot 3.2 | Backend REST API framework |
| Security | Spring Security + JWT | Authentication and Authorization |
| Database | MySQL 8.0 | Relational data persistence |
| ORM | Spring Data JPA + Hibernate | Database interaction via Java objects |
| Validation | Spring Boot Validation | Input validation using annotations |
| Boilerplate | Lombok | Auto-generate getters, setters, builders |
| Build Tool | Maven | Dependency management and build |
| Container | Docker + Docker Compose | Application containerization |
| Deployment | Railway | Cloud hosting platform |
| Version Control | Git + GitHub | Source code management |

---

## Demo Credentials

| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | ADMIN |
| analyst | analyst123 | ANALYST |
| viewer | viewer123 | VIEWER |

---

## Run Locally with Docker

```bash
# Clone the repo
git clone https://github.com/vikramsharma1506/finance-backend.git
cd finance-backend

# Create .env file from example
cp .env.example .env
# Fill in your values in .env

# Start with Docker
docker-compose up --build

# API runs at
http://localhost:8080
```

---

## Run Locally without Docker

```bash
# Create MySQL database
CREATE DATABASE finance_db;

# Set environment variables
DATABASE_URL=jdbc:mysql://localhost:3306/finance_db
DATABASE_USERNAME=root
DATABASE_PASSWORD=your_password
JWT_SECRET=myFinanceSecretKey2024SuperLongKeyForSecurity

# Run
mvn spring-boot:run
```

---

---

## ✅ Features

- [x] JWT based stateless authentication
- [x] BCrypt password hashing
- [x] Role based access control — VIEWER, ANALYST, ADMIN
- [x] Financial records CRUD
- [x] Soft delete for records
- [x] Filter by type, category, date range
- [x] Dashboard summary with aggregated analytics
- [x] Monthly income vs expense trends
- [x] Input validation with meaningful error messages
- [x] Global exception handling
- [x] Consistent API response wrapper
- [x] Auto seeded demo data on first run
- [x] Docker containerization
- [x] Deployed on Railway cloud platform

---

## 📝 Design Decisions & Assumptions

| Decision | Reason |
|----------|--------|
| **Soft Delete** | Financial data must be preserved for audit trails |
| **JWT over Sessions** | Stateless auth is more scalable |
| **BCrypt** | Industry standard one-way hashing, cannot be reversed |
| **DECIMAL(15,2)** | Floating point causes precision errors with money |
| **DTO Pattern** | Prevents Hibernate proxy issues and hides sensitive fields |
| **@PreAuthorize** | Fine-grained method-level security, cleaner than URL config |
| **DataSeeder** | Allows immediate testing without manual setup |
| **Docker** | Ensures app runs identically on all environments |

---

## 🔮 Future Improvements

- [ ] Pagination on records endpoint
- [ ] Refresh token mechanism
- [ ] Redis caching for dashboard
- [ ] Audit log table for all changes
- [ ] Swagger UI documentation
- [ ] Rate limiting
- [ ] Export to CSV or PDF

---

## 👨‍💻 Author

**Vikram Krishna Sharma**
- GitHub: [@vikramsharma1506](https://github.com/vikramsharma1506)
- Live API: [finance-backend-guard-production.up.railway.app](https://finance-backend-guard-production.up.railway.app)

---

<div align="center">

### 🌐 Live API
### https://finance-backend-guard-production.up.railway.app

⭐ If you found this project helpful, please give it a star!

</div>
