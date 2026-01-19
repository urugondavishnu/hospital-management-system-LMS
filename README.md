# Hospital Management System – Backend

A Spring Boot–based RESTful backend application for managing patients, appointments, medical records, payments, and admin reports.

This project was developed as part of the **Landmine Soft Java Developer Assignment (LMS-A-002)**.

---

## 🛠 Tech Stack

- Java 17
- Spring Boot 3.2.x
- Spring Web
- Spring Data JPA (Hibernate)
- PostgreSQL
- Bean Validation
- Maven
- Postman

---

## 📁 Project Structure
```
src/main/java/com/hospital/management
├── controller        // REST APIs
├── service           // Business logic
├── repository        // JPA repositories
├── entity            // Database entities
├── exception         // Global exception handling
└── HospitalManagementApplication.java
```


---

## ⚙️ Setup & Run (5 Steps)

### 1️⃣ Clone the repository
```bash
git clone <repository-url>
cd hospital-management
```

### 2️⃣ Create PostgreSQL database
```bash 
CREATE DATABASE hospital_db;
```

### 3️⃣ Configure database
Update ```src/main/resources/application.yml```
```bash
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/hospital_db
    username: postgres
    password: your_password

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### 4️⃣ Run the application
```bash 
mvn spring-boot:run
```
OR run ```HospitalManagementApplication``` from IntelliJ.

### 5️⃣ Test APIs
Use the provided Postman collection to test all APIs.

### 🗄 Database Entities
**Patient**
- id (PK)
- name
- age
- phone
- email (unique)
- password
- address
- gender
- bloodGroup
- emergencyContact

**Appointment**
- id (PK)
- patientId (FK)
- doctorId (FK)
- appointmentDate
- timeSlot
- status (PENDING / CANCELLED / COMPLETED)
- symptoms
- createdAt

**MedicalRecord**
- id (PK)
- patientId (FK)
- doctorId (FK)
- appointmentId (FK)
- diagnosis
- prescription
- dosageInstructions
- notes
- recordDate
- attachmentPath

**Payment**
- id (PK)
- appointmentId (FK)
- patientId (FK)
- doctorId (FK)
- amount
- paymentStatus (PENDING / PAID / FAILED / REFUNDED)
- paymentMethod
- transactionId
- paidAt

### 🔗 API Endpoints
**👤 Patient APIs**
- ```POST /api/patients```
- ```GET /api/patients```
- ```GET /api/patients/{id}```
- ```PUT /api/patients/{id}```
- ```DELETE /api/patients/{id}```

**📅 Appointment APIs**
- ```POST /api/patients/{patientId}/appointments```
- ```GET /api/patients/{patientId}/appointments```
- ```PATCH /api/patients/appointments/{appointmentId}/cancel```

**Business Rule:**
Appointments cannot be cancelled within 24 hours of the scheduled time.

**🩺 Medical Record APIs**
- ```POST /api/doctors/{doctorId}/records```
- ```GET /api/doctors/patients/{patientId}/records```

Adding a medical record automatically marks the appointment as COMPLETED.

**🛡 Admin APIs**
- ```GET /api/admin/revenue?doctorId=&from=&to=```
- ```GET /api/admin/stats```

### 🚦 Validation & Error Handling
- Bean Validation (```@NotBlank```, ```@Email```, ```@NotNull```)
- Global exception handling using ```@RestControllerAdvice```
- Proper HTTP status codes:
  - 400 – Bad Request 
  - 404 – Resource Not Found 
  - 200 / 204 – Success

### 📬 Postman Collection
Collection name:
```Hospital Management System – Landmine Soft```
- Includes all APIs grouped by module
- Uses ```{{baseUrl}}``` variable
- Exported in **Postman v2.1** format

### ✅ Features Implemented
- Full Patient CRUD 
- Appointment booking & cancellation (24-hour rule)
- Medical records management
- Payment lifecycle handling
- Admin revenue & dashboard statistics
- Clean layered architecture
- PostgreSQL persistence

### 📌 Notes
- Authentication & authorization are not implemented (out of scope)
- Doctor entity is mocked using doctorId
- Designed for backend evaluation and extensibility

### 📑 Swagger API Documentation
Swagger UI is integrated using **Springdoc OpenAPI** to provide interactive API documentation.
Access Swagger UI
```bash
http://localhost:8080/swagger-ui.html
```

**Features**
- Auto-generated API documentation
- Request & response schema visualization
- Interactive API testing support

**Screenshots are added in the docs folder :**
```hospital-management\hospital-management\docs```

### 👨‍💻 Author

**Vishnu Urugonda**
Java Backend Developer
