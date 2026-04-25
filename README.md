# Hi, I'm Jaswanth Sai 👋

## 📚 eLibrary – Scalable Digital Library Backend System

A backend-driven digital library application designed to handle secure document management, user authentication, and scalable storage using modern backend technologies.

🔗 **Live Demo:** http://ec2-13-236-200-65.ap-southeast-2.compute.amazonaws.com:8080/

---

## 🚀 Key Highlights

* Designed and developed a **RESTful backend system** using Spring Boot for managing users and digital resources
* Implemented **stateless authentication using JWT** to secure APIs and enforce access control
* Built **file handling APIs** for efficient upload and retrieval of PDF documents
* Ensured **user-specific authorization**, restricting access to only owned resources
* Integrated **PostgreSQL (Supabase)** for persistent and scalable data storage
* Deployed application on **AWS EC2**, enabling real-world accessibility

---

## ⚙️ Features

### 🔐 Authentication & Authorization (JWT-Based)

* Implemented **JSON Web Token (JWT) authentication** for secure and stateless sessions
* Generated JWT tokens upon successful login
* Secured endpoints using **Spring Security filters**
* Validated JWT for every protected API request via Authorization headers
* Ensured only authenticated users can access upload/download functionalities

---

### 📂 File Management

* Upload, store, and download PDF files using backend APIs
* Maintains metadata for efficient document organization
* Handles file storage with structured backend logic

---

### 🔎 Search Functionality

* Search PDFs using keywords and filenames
* Optimized database queries for faster retrieval

---

## 🏗️ Tech Stack

* **Backend:** Java, Spring Boot, Spring Security
* **Database:** PostgreSQL (Supabase)
* **Frontend:** HTML, CSS, JavaScript
* **Deployment:** AWS EC2

---

## 🔗 API Overview

| Method | Endpoint       | Description                 |
| ------ | -------------- | --------------------------- |
| POST   | /auth/login    | Authenticate user & get JWT |
| POST   | /upload        | Upload PDF file             |
| GET    | /files         | Fetch all user files        |
| GET    | /download/{id} | Download specific file      |

---

## 🧠 Backend Design Focus

* REST API design and request handling
* Secure authentication and authorization
* Database schema design and optimization
* File storage and retrieval efficiency
* Scalable backend architecture

---

## 🔐 Security Considerations

* JWT-based stateless authentication
* Secured endpoints using Spring Security
* User-specific data isolation
* Protected routes from unauthorized access

---

## 🚀 Deployment

The application is deployed on AWS EC2 and accessible publicly:
http://ec2-13-236-200-65.ap-southeast-2.compute.amazonaws.com:8080/

---

## 📜 License

MIT License © 2025 Jaswanth Sai

---

## 📬 Contact

For queries or collaboration: **[jaswanthsai2406@gmail.com](mailto:jaswanthsai2406@gmail.com)**
