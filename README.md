# 🏨 Hostel Management System (HostelMS)

A robust, full-stack web application built to streamline and automate hostel operations. From student enrollment and room allocation to complaint tracking and visitor management, HostelMS provides a comprehensive administrative interface and student portal.

This project was developed with a focus on clean architecture, secure data handling, and efficient relational database design using **Spring Boot**, **Hibernate**, and **Thymeleaf**.

---

## 💻 Tech Stack & Tools

*   **Backend Framework:** Java 17, Spring Boot, Spring MVC
*   **Data Access Layer:** Spring Data JPA / Hibernate
*   **Database:** MySQL (Relational mappings: One-to-Many, Many-to-One)
*   **Security:** Spring Security (BCrypt Password Encoding)
*   **Frontend Engine:** Thymeleaf, HTML5, CSS3, JavaScript
*   **Build Tool:** Maven

---

## 🚀 Key Features

### 👨‍💼 Admin Dashboard
*   **Student Management:** Review, approve, or reject new student registrations securely.
*   **Room Allocation Engine:** Automated tracking of blocks, available rooms, and current bed occupancies.
*   **Visitor Log:** Track and monitor visitor requests mapped to specific rooms and students.
*   **Complaints Resolution:** Manage and respond to lodged student complaints, updating states seamlessly from `LODGED` to `CLOSED`.

### 🎓 Student Portal
*   **Registration & Authentication:** Secure sign-up and login flow protected by Spring Security.
*   **Room & Fee Tracking:** View allocated rooms and monitor monthly rent status.
*   **Service Requests:** Lodge complaints and view administrative responses directly from the dashboard.

---

## 🏗️ Architectural Highlights

The codebase has been refactored to adhere to enterprise best practices:
*   **Service-Oriented Architecture:** Heavy business logic is decoupled from Controllers and encapsulated into dedicated `Service` layers (e.g., `AdminService`).
*   **Data Transfer Objects (DTOs):** Secure data transmission between the persistence layer and client views using DTOs (e.g., `VisitorDTO`).
*   **Type Safety & Enums:** Replaced "magic numbers" with strongly-typed Java Enums (`RegistrationStatus`, `ComplaintState`) for cleaner state tracking.
*   **Global Exception Handling:** Centralized `@ControllerAdvice` to gracefully capture and format system errors.
*   **Secure Configurations:** Database credentials are externalized into environment variables to prevent sensitive data leaks.

---

## 🛠️ Local Setup & Installation

### Prerequisites
*   Java 17 (JDK) installed and configured in your `PATH`.
*   MySQL Server running locally on port `3306`.

### 1. Database Setup
Create a new MySQL database named `spring_hostelms`:
```sql
CREATE DATABASE spring_hostelms;
```
*(Optional)* Import the provided `127_0_0_1_spring_hostelms.sql` file if you wish to pre-load sample data.

### 2. Environment Variables
To ensure security, the application expects environment variables for the database credentials. Set the following variables in your OS or IDE:
*   `DB_USERNAME` (defaults to `root`)
*   `DB_PASSWORD` (defaults to `Mysql@1234`)

### 3. Build & Run
Run the application using the Maven wrapper:
```bash
# On Windows
.\mvnw.cmd spring-boot:run

# On Mac/Linux
./mvnw spring-boot:run
```

Once started, the application will be accessible at: `http://localhost:8080`
