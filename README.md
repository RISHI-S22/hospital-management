# 🏥 Metropolitan Hospital Management System

A secure, role-based Hospital Management System built with **Spring Boot 3**, **Spring Data JPA**, **SQLite**, and a responsive **Vanilla Web UI**. The system streamlines patient registrations, clinical consultations, nurse care duties, pharmacy inventory, and medical billing under a unified platform.

---

## 📌 Key Highlights

- **Zero-Config Persistent Storage:** Powered by SQLite (`hospital.db`) using single-connection Hikari pool tuning to guarantee ACID transactions without file-lock contention.
- **Role-Based Access Control (RBAC):** Dedicated views and permissions for 5 core hospital roles: **Admin**, **Doctor**, **Nurse**, **Pharmacist**, and **Patient**.
- **Cryptographic Security:** BCrypt password hashing (`$2a$10$...`) with fallback support for legacy seed credentials.
- **Collision-Free Credential Provisioning:** Automated generation of 5-digit `PAT-xxxxx` IDs verified against the database, paired with high-entropy randomized temporary passwords.
- **Paperless Clinical Workflows:** Multi-medicine prescription staging carts, automatic billing ledger generation, nurse care delegation, and printable patient health summaries.

---

## 🛠️ Tech Stack & Tools

| Layer | Technologies & Tools |
| :--- | :--- |
| **Backend** | Java 17+, Spring Boot 3.2.4 (Spring Web, Spring Data JPA) |
| **ORM & Dialect** | Hibernate 6, `hibernate-community-dialects` (`SQLiteDialect`) |
| **Database** | SQLite (`sqlite-jdbc` v3.45.1.0), HikariCP (`maximum-pool-size=1`) |
| **Security** | Spring Security Crypto (`BCryptPasswordEncoder`), Custom RBAC |
| **Frontend** | Semantic HTML5, CSS3 (CSS Variables, Flexbox, Grid), Vanilla JavaScript (Fetch API, Web Storage API) |
| **Inspection Tools** | DB Browser for SQLite, Postman, VS Code Java Extension Pack |
| **Build Tool** | Apache Maven / Maven Wrapper (`mvnw`) |

---

## 🔐 Security Architecture

- **BCrypt Password Hashing:** Stored user credentials in the `register` table are cryptographically hashed using BCrypt. Direct database leaks reveal no plaintext user secrets.
- **Duplicate Registration Shield:** Self-service registration forms are validated prior to admin approval. If an applicant's phone number or email matches an existing account, approval is blocked with descriptive errors.
- **Identity Isolation:** Roles are prefixed and validated at both the API layer and the frontend (`PAT-xxxx`, `DOC-xxx`, `NUR-xxx`, `PHARM-xxx`, `ADM-xxx`).
- **Atomic Operations:** Multi-table writes (patient demographic admission, credential provisioning, and request tracking) are managed inside `@Transactional` blocks with `saveAndFlush` routines.

---

## 👥 Role Modules & Features

### 1. 🛡️ Administrator (`ADM-xxx`)
- **Online Application Review:** Live approval queue for public registration requests with automatic account generation.
- **Staff Provisioning:** Onboard Doctors, Nurses, Pharmacists, and fellow Admins.
- **In-Person Patient Admission:** Instant walk-in admission generating room allocations and auto-login credentials.

### 2. 👨‍⚕️ Doctor (`DOC-xxx`)
- **Consultation Ledger:** Real-time visibility into scheduled appointments.
- **Multi-Medicine Prescription Staging:** Draft and review multiple medicines, dosages, and notes in an active cart before dispatching.
- **Pharmacy Stock Lookup:** Search real-time medicine inventory and alternatives.
- **Nurse Assignment:** Delegate clinical and bedside care duties (`NUR-201` to `NUR-212`).

### 3. 💊 Pharmacist (`PHARM-xxx`)
- **Unified Billing Desk:** Real-time queue aggregating doctor-prescribed medications and direct patient orders.
- **Point of Sale (POS):** Settle bills via Cash or Card/UPI, automatically updating inventory and receipts.
- **Inventory Control:** Restock medicines, configure generic categories, set alternatives, and adjust unit pricing.

### 4. 🩺 Nurse (`NUR-xxx`)
- **Daily Care Worklist:** Dedicated view of patients assigned by attending physicians.
- **Task Management:** Review medical orders, dosage instructions, and mark care activities as completed.

### 5. 🧑‍🦱 Patient (`PAT-xxxx`)
- **Self-Service Registration:** 10-point online enrollment form with reference tracking.
- **Consultation Booking:** Schedule appointments with specialist physicians.
- **Pharmacy Store:** Order supplemental over-the-counter medications directly to the pharmacy desk.
- **Comprehensive Medical Report:** Exportable and printable summary containing admission details, active doctor prescriptions, and pharmacy billing history.

---

## 🚀 Getting Started

### Prerequisites
- **Java Development Kit (JDK 17 or higher)**
- **Maven** (or use the included `./mvnw`)
- **Git**

### Installation & Run

1. **Clone the Repository:**
   ```bash
   git clone [https://github.com/rishi-s22/hospital-management.git](https://github.com/rishi-s22/hospital-management.git)
   cd hospital-management
