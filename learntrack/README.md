# LearnTrack - Student & Course Management System

LearnTrack is a console-based, menu-driven application built with **Core Java**. An admin can manage
**students**, **courses** and **enrollments**. All data is kept in memory (`ArrayList`), so it resets
every time the program stops.

## Features

- **Students:** add, view all, search by ID, deactivate (soft delete)
- **Courses:** add, view all, activate / deactivate
- **Enrollments:** enroll a student in a course, view a student's enrollments, mark as completed / cancelled
- Friendly error messages for invalid menu options, bad numbers, empty fields and unknown IDs

## Project structure

```
src/com/airtribe/learntrack/
├── Main.java            # menu + console input only
├── entity/              # Person, Student, Trainer, Course, Enrollment
├── repository/          # in-memory storage (ArrayList)
├── service/             # business logic
├── exception/           # EntityNotFoundException, InvalidInputException
├── util/                # IdGenerator, InputValidator
├── constants/           # MenuOptions, AppConstants
└── enums/               # EnrollmentStatus, CourseStatus
docs/                    # Setup_Instructions.md, JVM_Basics.md, Design_Notes.md
```

## Class diagram

The diagrams below are written in [Mermaid](https://mermaid.js.org/), which GitHub renders automatically in this README.

### 1. Entities and enums

`Student` and `Trainer` inherit from `Person`. An `Enrollment` links a student and a course by their IDs (`studentId`, `courseId`) instead of holding the objects, which keeps the entities simple.

```mermaid
classDiagram
    direction TB

    class Person {
        -int id
        -String firstName
        -String lastName
        -String email
        +getId() int
        +getFirstName() String
        +getLastName() String
        +getEmail() String
        +getDisplayName() String
    }
    class Student {
        -String batch
        -boolean active
        +getBatch() String
        +isActive() boolean
        +getDisplayName() String
    }
    class Trainer {
        -String expertise
        +getExpertise() String
        +getDisplayName() String
    }
    class Course {
        -int id
        -String courseName
        -String description
        -int durationInWeeks
        -boolean active
        +isActive() boolean
        +getStatus() CourseStatus
    }
    class Enrollment {
        -int id
        -int studentId
        -int courseId
        -LocalDate enrollmentDate
        -EnrollmentStatus status
        +getStatus() EnrollmentStatus
    }
    class EnrollmentStatus {
        <<enumeration>>
        ACTIVE
        COMPLETED
        CANCELLED
    }
    class CourseStatus {
        <<enumeration>>
        ACTIVE
        INACTIVE
    }

    Person <|-- Student : extends
    Person <|-- Trainer : extends
    Enrollment --> EnrollmentStatus : has status
    Course ..> CourseStatus : derives
    Enrollment ..> Student : refers to by studentId
    Enrollment ..> Course : refers to by courseId
```

### 2. Layers: UI, services, repositories and helpers

`Main` talks only to the services. Services hold the business rules and use repositories for storage. `EnrollmentService` also uses `StudentService` and `CourseService` to check that IDs exist.

```mermaid
classDiagram
    direction LR

    class Main {
        +main(String[] args)$
    }

    class StudentService {
        +addStudent(first, last, email, batch) Student
        +addStudent(first, last, batch) Student
        +listStudents() List~Student~
        +findStudentById(int id) Student
        +updateStudent(...) Student
        +removeStudent(int id)
        +deactivateStudent(int id) Student
    }
    class CourseService {
        +addCourse(name, description, weeks) Course
        +listCourses() List~Course~
        +findCourseById(int id) Course
        +updateCourse(...) Course
        +removeCourse(int id)
        +activateCourse(int id) Course
        +deactivateCourse(int id) Course
    }
    class EnrollmentService {
        +enrollStudent(studentId, courseId) Enrollment
        +getEnrollmentsForStudent(int studentId) List~Enrollment~
        +markCompleted(int id) Enrollment
        +markCancelled(int id) Enrollment
    }

    class StudentRepository {
        -List~Student~ students
        +save(Student)
        +findAll() List~Student~
        +findById(int id) Student
        +deleteById(int id) boolean
    }
    class CourseRepository {
        -List~Course~ courses
        +save(Course)
        +findAll() List~Course~
        +findById(int id) Course
        +deleteById(int id) boolean
    }
    class EnrollmentRepository {
        -List~Enrollment~ enrollments
        +save(Enrollment)
        +findAll() List~Enrollment~
        +findById(int id) Enrollment
        +findByStudentId(int id) List~Enrollment~
    }

    class IdGenerator {
        -int studentIdCounter$
        -int courseIdCounter$
        -int enrollmentIdCounter$
        +getNextStudentId()$ int
        +getNextCourseId()$ int
        +getNextEnrollmentId()$ int
    }
    class InputValidator {
        +requireNonEmpty(value, field)$ String
        +requireValidEmail(email)$ String
        +parsePositiveInt(text, field)$ int
    }
    class EntityNotFoundException {
        <<exception>>
    }
    class InvalidInputException {
        <<exception>>
    }

    Main --> StudentService : uses
    Main --> CourseService : uses
    Main --> EnrollmentService : uses
    Main ..> InputValidator : validates input

    StudentService --> StudentRepository : stores in
    CourseService --> CourseRepository : stores in
    EnrollmentService --> EnrollmentRepository : stores in
    EnrollmentService --> StudentService : checks student
    EnrollmentService --> CourseService : checks course

    StudentRepository o-- Student : holds many
    CourseRepository o-- Course : holds many
    EnrollmentRepository o-- Enrollment : holds many

    StudentService ..> IdGenerator
    CourseService ..> IdGenerator
    EnrollmentService ..> IdGenerator
    StudentService ..> InputValidator
    CourseService ..> InputValidator
    StudentService ..> EntityNotFoundException : throws
    CourseService ..> EntityNotFoundException : throws
    EnrollmentService ..> EntityNotFoundException : throws
    EnrollmentService ..> InvalidInputException : throws
    InputValidator ..> InvalidInputException : throws
```

**Reading the arrows:** `<|--` is inheritance, `-->` means "uses / has a reference to", `o--` means "holds a collection of", and `..>` is a weaker dependency (a helper is called or an exception is thrown).

## Requirements

- JDK 11 or newer (tested with JDK 21)

## How to compile and run

From the project root (the folder containing `src/`):

**Linux / macOS**
```bash
mkdir -p out
javac -d out $(find src -name "*.java")
java -cp out com.airtribe.learntrack.Main
```

**Windows (PowerShell)**
```powershell
mkdir out
javac -d out (Get-ChildItem -Recurse -Filter *.java src | ForEach-Object FullName)
java -cp out com.airtribe.learntrack.Main
```

**IDE (IntelliJ / Eclipse / VS Code):** open the folder, mark `src` as the source root and run
`com.airtribe.learntrack.Main`.

## Sample session

```
---- MAIN MENU ----
1. Student Management
2. Course Management
3. Enrollment Management
0. Exit
Choose an option: 1
...
Student added: Student{id=1, name=Asha Rao, email=asha@mail.com, batch=B1, active=true}
```

## Documentation

- [Setup Instructions](docs/Setup_Instructions.md)
- [JVM Basics](docs/JVM_Basics.md)
- [Design Notes](docs/Design_Notes.md)
