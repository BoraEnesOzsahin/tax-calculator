# 🧾 Tax Calculator API

A Spring Boot RESTful API that lets users manage products and calculate tax.  
This project was developed as part of the **Ayrotek Backend Coding Challenge**.

---

## 🚀 Features

- ✅ Create, update, and delete your own products
- ✅ View all products
- ✅ Calculate tax on any product
- ✅ Authentication via HTTP Basic Auth
- ✅ Authorization: Users can only modify their own products
- ✅ Includes a test suite to ensure correctness

---

## 🛠 Tech Stack

- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Lombok
- H2 Database
- JUnit & Mockito

---

## 🔐 Authentication

### 👥 Default Users (Preloaded at startup)

| Username   | Password | Role |
|------------|----------|------|
| testuser   | password | USER |
| secuser    | secpass  | USER |
| theuser    | thepass  | USER |

📌 These users are stored in the H2 database at runtime via a `DataInitializer`.

### 🔑 Usage

All requests must include a basic authentication header, e.g.:

```
Authorization: Basic dGVzdHVzZXI6cGFzc3dvcmQ=
```

(base64 for "testuser:password")

---

## 📦 Project Structure

```
tax-calculator/
├── config/            # Security setup and data seeding
├── controller/        # API endpoints
├── model/             # Entities: User, Product
├── repository/        # JPA repositories
├── service/           # Business logic
└── TaxCalculatorApplication.java
```

---

## 🧪 Running the Project

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/tax-calculator.git
cd tax-calculator
```

### 2. Build and Run

```bash
mvn clean install
mvn spring-boot:run
```

API will be available at: [http://localhost:8080/api](http://localhost:8080/api)

---

## 📬 API Endpoints

| Method | Endpoint                 | Description                        | Auth Required |
|--------|--------------------------|------------------------------------|----------------|
| GET    | `/api/products`          | View all products                  | ✅             |
| POST   | `/api/products`          | Create a new product               | ✅             |
| PUT    | `/api/products/{id}`     | Update your own product            | ✅             |
| DELETE | `/api/products/{id}`     | Delete your own product            | ✅             |
| GET    | `/api/products/{id}/tax` | Calculate tax for a product        | ✅             |

---

## 📄 Sample Product Payload

```json
{
  "name": "Monitor",
  "price": 1200.0,
  "taxRate": 0.18
}
```

### Sample Tax Response

```json
{
  "productId": 1,
  "name": "Monitor",
  "price": 1200.0,
  "taxRate": 0.18,
  "taxAmount": 216.0
}
```

---

## ✅ Test Suite

To run all tests:

```bash
mvn test
```

Includes tests for:
- Product CRUD logic
- Tax calculation accuracy
- Authentication and access control

---

## 📮 Contact

This project was developed for the Ayrotek Backend Coding Challenge.  
For any questions, feel free to contact me via GitHub or email.
