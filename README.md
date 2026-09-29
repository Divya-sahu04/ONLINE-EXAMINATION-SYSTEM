# Online Examination System

A desktop-based Online Examination System developed using Java Swing, MySQL, JDBC, Maven, and BCrypt. The application provides separate modules for students and administrators to conduct and manage online examinations.

---

## 📌 Project Overview

The Online Examination System is designed to simplify the process of conducting computer-based examinations.

The system provides:

- Student registration and authentication
- Secure password storage using BCrypt
- Admin dashboard and management
- Examination creation and management
- MCQ question management
- Timed examinations
- Automatic exam submission
- Result calculation
- Answer storage and review
- Student result history
- Student profile management
- Exam attempt tracking

The application follows a modular architecture using Java Swing for the user interface, JDBC for database communication, and MySQL for persistent data storage.

---

## ✨ Features

### 👨‍🎓 Student Module

- Student registration
- Secure student login
- View available examinations
- View examination instructions
- Start examination
- Countdown timer
- MCQ-based questions
- Next/Previous navigation
- Mark questions for review
- Answer selection
- Unanswered-question detection
- Manual examination submission
- Automatic submission when time expires
- Automatic score calculation
- Percentage calculation
- Result display
- Result history
- Detailed answer review
- Profile management
- Password change

---

### 👨‍💼 Admin Module

- Secure administrator login
- Dashboard statistics
- Exam management
- Add examinations
- Update examinations
- Delete examinations
- Question management
- Add MCQ questions
- Update questions
- Delete questions
- Automatic question count updates
- View registered students
- View all examination results
- View detailed student answers
- Logout functionality

---

## 🔐 Security Features

The application implements several security-related features:

- BCrypt password hashing
- Password verification using BCrypt
- Prepared statements for database operations
- Role-based login routing
- Duplicate active exam attempt prevention
- Database transactions during exam submission
- Foreign-key constraints
- Input validation
- Password confirmation during password changes

Passwords are never stored as plain text.

---

## 🛠️ Technologies Used

| Technology | Purpose |
|------------|---------|
| Java 17 | Application development |
| Java Swing | Graphical User Interface |
| MySQL | Database |
| JDBC | Database connectivity |
| Maven | Dependency management and build |
| BCrypt | Password hashing |
| IntelliJ IDEA | Development environment |

---

## 🏗️ System Architecture

The application follows a layered architecture:

```text
                    ┌─────────────────────┐
                    │     Java Swing UI   │
                    │      UI Layer       │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │       Service       │
                    │       Layer         │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │        DAO Layer    │
                    │  Database Operations│
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │     JDBC / MySQL    │
                    │     Database Layer  │
                    └─────────────────────┘

online-examination-system/
│
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── onlineexam/
│
│                   ├── Main.java
│                   │
│                   ├── config/
│                   │   └── DatabaseConnection.java
│                   │
│                   ├── dao/
│                   │   ├── AnswerDAO.java
│                   │   ├── DashboardDAO.java
│                   │   ├── ExamAttemptDAO.java
│                   │   ├── ExamDAO.java
│                   │   ├── ProfileDAO.java
│                   │   ├── QuestionDAO.java
│                   │   ├── ResultDAO.java
│                   │   └── UserDAO.java
│                   │
│                   ├── model/
│                   │   ├── Exam.java
│                   │   ├── Question.java
│                   │   ├── Result.java
│                   │   └── User.java
│                   │
│                   ├── service/
│                   │   └── ExamSubmissionService.java
│                   │
│                   ├── ui/
│                   │   ├── LoginFrame.java
│                   │   ├── RegistrationFrame.java
│                   │   ├── AdminDashboard.java
│                   │   ├── StudentDashboard.java
│                   │   ├── ExamManagementFrame.java
│                   │   ├── QuestionManagementFrame.java
│                   │   ├── StudentManagementFrame.java
│                   │   ├── ResultManagementFrame.java
│                   │   ├── AvailableExamsFrame.java
│                   │   ├── ExamInstructionsFrame.java
│                   │   ├── ExamFrame.java
│                   │   ├── ResultFrame.java
│                   │   ├── ResultHistoryFrame.java
│                   │   ├── AnswerReviewFrame.java
│                   │   └── ProfileFrame.java
│                   │
│                   └── util/
│                       ├── PasswordUtil.java
│                       └── UIStyle.java
│
├── pom.xml
├── README.md
└── .gitignore

users
   │
   ├──────────────┐
   │              │
   ▼              ▼
results       exam_attempts
   │
   ▼
answers
   │
   ▼
questions
   │
   ▼
exams

APPLICATION WORKFLOW
Registration
     ↓
Login
     ↓
Student Dashboard
     ↓
Available Exams
     ↓
Exam Instructions
     ↓
Start Exam
     ↓
Answer Questions
     ↓
Submit Exam
     ↓
Calculate Score
     ↓
Save Result + Answers
     ↓
Result Screen
     ↓
Result History
     ↓
Answer Review

ADMIN WORKFLOW
Admin Login
     ↓
Admin Dashboard
     ↓
Manage Exams
     ↓
Manage Questions
     ↓
View Students
     ↓
View Results
     ↓
View Answer Deta