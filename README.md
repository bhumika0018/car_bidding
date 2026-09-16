# Car_Sell — Full-Stack Microservices Vehicle Marketplace

A full-stack vehicle sales and bidding platform built with **Java 17**, **Spring Boot 3.4.1**, **Spring Cloud**, and **Angular 19**.

---

## 🏗️ Architecture Overview

The system uses a microservices architecture with a centralized API Gateway and Service Discovery:

```
                      +-------------------+
                      | Angular Frontend  | (Port 4200)
                      +---------+---------+
                                |
                                v
                      +-------------------+
                      |    API Gateway    | (Port 8080)
                      +---------+---------+
                                |
        +-----------------------+-----------------------+
        |                       |                       |
        v                       v                       v
+---------------+       +---------------+       +---------------+
| Auth Service  |       |  Car Service  |       |  Bid Service  |
|  (Port 8081)  |       |  (Port 8082)  |       |  (Port 8083)  |
+---------------+       +---------------+       +---------------+
        |                       |                       |
        +-----------------------+-----------------------+
                                |
                                v
                      +-------------------+
                      |   Eureka Server   | (Port 8761)
                      +-------------------+
```

---

## 🛠️ Technology Stack

### Backend
- **Java**: 17 (LTS)
- **Framework**: Spring Boot 3.4.1, Spring Cloud 2024.0.0
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway with Custom JWT Authentication Filter
- **Security**: Spring Security, JWT (io.jsonwebtoken 0.11.5)
- **Database**: MySQL 8.0+ / JPA (Hibernate ORM 6.6)
- **Build Tool**: Apache Maven (via Maven Wrappers `./mvnw`)

### Frontend
- **Framework**: Angular 19.1.0
- **Language**: TypeScript 5.6
- **Styling**: TailwindCSS & Vanilla CSS
- **Testing**: Karma & Jasmine (ChromeHeadless)

---

## ⚙️ Prerequisites

Before building and running the project, ensure you have installed:
1. **Java Development Kit (JDK)**: Version 17+
2. **Node.js**: Version 20.x (LTS)
3. **npm**: Version 10.x+
4. **MySQL Server**: Version 8.0+

---

## 🔐 Environment Configuration

Create a `.env` file or export the following environment variables (see `.env.example` for defaults):

| Variable | Description | Default / Example |
| :--- | :--- | :--- |
| `DB_HOST` | MySQL Server Hostname | `localhost` |
| `DB_PORT` | MySQL Server Port | `3306` |
| `DB_USERNAME` | MySQL Database Username | `root` |
| `DB_PASSWORD` | MySQL Database Password | `root` |
| `JWT_SECRET` | Base64 Encoded 256-bit Key | `<BASE64_KEY>` |
| `AUTH_SERVICE_URL` | Auth Microservice Host URL | `http://localhost:8081` |

---

## 🗄️ Database Setup

Create the following MySQL databases prior to starting the backend microservices:

```sql
CREATE DATABASE IF NOT EXISTS car;
CREATE DATABASE IF NOT EXISTS auth;
CREATE DATABASE IF NOT EXISTS bid;
```

> **Note**: Hibernate JPA `ddl-auto=update` automatically generates and updates the schema tables upon application startup.

---

## 🚀 Building & Running the Application

### 1. Build and Run Backend Microservices

Launch the services in the following order:

```bash
# 1. Eureka Discovery Server (Port 8761)
cd sellcar_backend/eureka
./mvnw clean spring-boot:run

# 2. Auth Service (Port 8081)
cd sellcar_backend/auth
./mvnw clean spring-boot:run

# 3. Car Service (Port 8082)
cd sellcar_backend/car
./mvnw clean spring-boot:run

# 4. Bid Service (Port 8083)
cd sellcar_backend/bid
./mvnw clean spring-boot:run

# 5. API Gateway (Port 8080)
cd sellcar_backend/api-gateway
./mvnw clean spring-boot:run
```

### 2. Build and Run Frontend

```bash
cd sellcar_frontend

# Install dependencies
npm ci

# Start Angular development server
npm start
```

Access the frontend application at **`http://localhost:4200/`**.

---

## 🧪 Running Tests

### Backend Unit & Integration Tests
```bash
cd sellcar_backend/eureka && ./mvnw clean test
cd sellcar_backend/auth && ./mvnw clean test
cd sellcar_backend/car && ./mvnw clean test
cd sellcar_backend/bid && ./mvnw clean test
cd sellcar_backend/api-gateway && ./mvnw clean test
```

### Frontend Unit Tests
```bash
cd sellcar_frontend
npx ng test --watch=false --browsers=ChromeHeadless
```

---

## 📄 License
Personal Project — Distributed under standard open repository guidelines.
