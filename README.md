# Digital Wallet Management System

A production-style backend REST API project built using Java Spring Boot for managing digital wallets, wallet transactions, and money transfers.

---

# Tech Stack

* Java 21
* Spring Boot 3
* Spring Data JPA
* Hibernate
* MySQL
* Gradle
* Lombok
* ModelMapper
* Swagger OpenAPI

---

# Features

## User Management

* Create User
* Get User By ID
* Unique Email Validation
* Unique Mobile Number Validation

## Wallet Management

* Create Wallet
* One Wallet Per User
* Get Wallet Details
* Add Money To Wallet

## Money Transfer

* Transfer Money Between Wallets
* Sender/Receiver Validation
* Insufficient Balance Validation
* Atomic Transactions using `@Transactional`

## Transaction History

* View Wallet Transaction History
* Sent Transactions
* Received Transactions

## Validation & Exception Handling

* Bean Validation
* Global Exception Handling
* Custom Exceptions
* Proper HTTP Status Codes

---

# Project Architecture

```text
Controller Layer
        ↓
Service Layer
        ↓
Repository Layer
        ↓
Database
```

---

# Project Structure

```text
src/main/java/com/example/demo

├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── service
└── service/impl
```

---

# Database Tables

## users

* id
* name
* email
* mobile_number

## wallets

* wallet_id
* balance
* created_at
* user_id

## transactions

* transaction_id
* from_wallet_id
* to_wallet_id
* amount
* transaction_time
* status

---

# Entity Relationships

## User ↔ Wallet

* One-to-One

## Wallet ↔ Transaction

* One-to-Many
* Many-to-One

---

# API Endpoints

# User APIs

## Create User

```http
POST /api/users
```

### Request Body

```json
{
  "name": "Pushpak",
  "email": "pushpak@gmail.com",
  "mobileNumber": "9876543210"
}
```

---

## Get User By ID

```http
GET /api/users/{id}
```

---

# Wallet APIs

## Create Wallet

```http
POST /api/wallets/create/{userId}
```

---

## Get Wallet By ID

```http
GET /api/wallets/{walletId}
```

---

## Add Money

```http
POST /api/wallets/add-money
```

### Request Body

```json
{
  "walletId": 1,
  "amount": 5000
}
```

---

## Transfer Money

```http
POST /api/wallets/transfer
```

### Request Body

```json
{
  "fromWalletId": 1,
  "toWalletId": 2,
  "amount": 1000
}
```

---

## Get Wallet Transactions

```http
GET /api/wallets/{walletId}/transactions
```

---

# Validation Rules

* Email must be unique
* Mobile number must be unique
* Wallet balance cannot be negative
* Transfer amount must be greater than 0
* Sender and receiver wallets cannot be same
* Sender must have sufficient balance

---

# Exception Handling

Custom Exceptions Used:

* ResourceNotFoundException
* DuplicateResourceException
* InvalidTransactionException
* InsufficientBalanceException

Global exception handling implemented using:

```java
@RestControllerAdvice
```

---

# Transaction Management

Money transfer operation uses:

```java
@Transactional
```

to ensure atomicity.

If any failure occurs during transfer:

* Debit operation rolls back
* Credit operation rolls back

---

# Swagger Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

---

# How To Run The Project

## Clone Repository

```bash
git clone <repository-url>
```

---

## Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/digital_wallet_db
spring.datasource.username=root
spring.datasource.password=your_password
```

---

## Run Application

```bash
./gradlew bootRun
```

---

# Future Improvements

* JWT Authentication
* Redis Cache
* Docker
* Unit Testing
* Pagination
* Flyway Migration
* Kafka/RabbitMQ Integration

---

# Author

Pushpak Fasate

Backend Developer | Java Spring Boot Developer
