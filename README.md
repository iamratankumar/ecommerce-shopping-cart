# eCommerce Backend – Java Spring Boot

A complete backend system for an eCommerce application built with Java Spring Boot and Maven. This project handles core features such as product management, category assignment, user authentication, and a shopping cart system.

---

## Features

### 🛍️ Product & Category Management
- Create, update, delete products
- Associate products with categories
- Upload and retrieve product images

### 🛒 Shopping Cart
- Add and remove items from the cart
- Update item quantities
- Track cart by authenticated user

### 🔐 User Authentication
- Register and log in users
- JWT-based authentication
- Password encryption using Spring Security

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Security + JWT
- JPA / Hibernate
- MySQL(configurable)
- Maven

---

## Getting Started

### Prerequisites
- Java 17+
- Maven
- MySQL or PostgreSQL

### Setup Instructions

1. **Clone the repository**
   ```bash
    https://github.com/iamratankumar/ecommerce-shopping-cart.git

2. **application.properties**
   ```bash
    spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    jwt.secret=your_jwt_secret

3. **Build and Run**
   ```bash
    mvn clean install
    mvn spring-boot:run
