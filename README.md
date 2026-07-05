# 🏨 Hostel Management System (Backend API)

A robust and scalable RESTful backend architecture built to streamline and automate hostel operations, including student enrollment, room allocation, and data management. 

This project was developed with a primary focus on strengthening core backend architecture skills, implementing clean code principles, and designing an efficient relational database structure using Spring Boot and Hibernate.

## 💻 Tech Stack & Tools

*   **Language:** Java 17
*   **Framework:** Spring Boot (Spring Web, Spring Data JPA)
*   **Database:** MySQL
*   **Build Tool:** Maven
*   **Architecture:** MVC (Model-View-Controller) / RESTful API

## 🚀 Key Features

*   **Student Management:** Secure endpoints for registering new students and updating profiles.
*   **Room Allocation Engine:** Automated tracking of available rooms, bed capacity, and current occupancies.
*   **Data Persistence:** Highly structured MySQL relational database utilizing Entity relationships (One-to-Many, Many-to-One).
*   **Modular Architecture:** Clean separation of concerns across `Controllers`, `Services`, and `Repositories` for maximum scalability.

## 📂 Project Structure Overview

```text
src/main/java/com/project/hostelms
 ├── controller/       # REST endpoints handling HTTP requests
 ├── service/          # Core business logic and transaction management
 ├── repository/       # Data Access Layer (Spring Data JPA interfaces)
 ├── entity/           # Database models and mappings
 ├── config/           # Application configuration and security setups
 └── security/         # Authentication and authorization logic
