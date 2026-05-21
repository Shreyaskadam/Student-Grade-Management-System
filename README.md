# Student Grade Management System

A console-based Java application that manages student grades using arrays.

## Features

- Store up to 50 students with roll number, name, and marks in 3 subjects (Math, Science, English)
- Calculate **total**, **average**, and **letter grade** (A / B / C / F)
- Search a student by **roll number**

## Grade Scale

| Average | Grade |
|---------|-------|
| ≥ 80    | A     |
| ≥ 60    | B     |
| ≥ 40    | C     |
| < 40    | F     |

## Requirements

- Java JDK 8 or higher

## How to Run

```bash
cd "Student Grade Management System"
javac StudentGradeManagementSystem.java
java StudentGradeManagementSystem
```

## Menu Options

1. **Add student** — Enter roll number, name, and marks for each subject
2. **Display all students** — List every student with totals and grades
3. **Search by roll number** — Find one student by roll number
4. **Exit**
