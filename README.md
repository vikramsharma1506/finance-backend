# 🏦 Finance Data Processing and Access Control Backend

A production-ready RESTful API backend for a Finance Dashboard built with
**Java 17, Spring Boot 3.2, MySQL, and JWT Security**.

## 🛠 Tech Stack

| Technology | Purpose |
|-----------|---------|
| Java 17 | Core language |
| Spring Boot 3.2 | Backend framework |
| Spring Security + JWT | Authentication & Authorization |
| MySQL + JPA/Hibernate | Database & ORM |
| Lombok | Reduce boilerplate code |
| Maven | Dependency management |

---

## 🔐 Role-Based Access Control (RBAC)

| Role | What They Can Do |
|------|-----------------|
| **VIEWER** | View financial records only |
| **ANALYST** | View records + access dashboard summaries and trends |
| **ADMIN** | Full access — manage records, users, and dashboard |

---

## 🚀 Quick Start

### Prerequisites
- Java 17+
- MySQL 8+
- Maven 3.8+

### Step 1 — Clone the repository
```bash
git clone https://github.com/YOUR_USERNAME/finance-backend.git
cd finance-backend
```

### Step 2 — Create MySQL database
```sql
CREATE DATABASE finance_db;
```

### Step 3 — Configure database
Open `src/main/resources/application.properties` and update:
```properties
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### Step 4 — Run the application
```bash
mvn spring-boot:run
```

### Step 5 — Server is running at
http://localhost:8080/
