package com.airtribe.learntrack;

import com.airtribe.learntrack.constants.AppConstants;
import com.airtribe.learntrack.constants.MenuOptions;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;
import com.airtribe.learntrack.util.InputValidator;

import java.util.List;
import java.util.Scanner;

/**
 * Console UI. Main only shows menus, reads input and calls service methods;
 * all business rules live in the service package.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final StudentService studentService = new StudentService(new StudentRepository());
    private static final CourseService courseService = new CourseService(new CourseRepository());
    private static final EnrollmentService enrollmentService =
            new EnrollmentService(new EnrollmentRepository(), studentService, courseService);

    public static void main(String[] args) {
        System.out.println("=== " + AppConstants.APP_NAME + " ===");

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readMenuChoice();

            switch (choice) {
                case MenuOptions.MAIN_STUDENTS:
                    studentMenu();
                    break;
                case MenuOptions.MAIN_COURSES:
                    courseMenu();
                    break;
                case MenuOptions.MAIN_ENROLLMENTS:
                    enrollmentMenu();
                    break;
                case MenuOptions.MAIN_EXIT:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose a number from the menu.");
            }
        }
        System.out.println("Goodbye!");
    }

    // ---------------------------------------------------------------- menus

    private static void printMainMenu() {
        System.out.println();
        System.out.println("---- MAIN MENU ----");
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Enrollment Management");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private static void studentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("---- STUDENT MENU ----");
            System.out.println("1. Add new student");
            System.out.println("2. View all students");
            System.out.println("3. Search student by ID");
            System.out.println("4. Deactivate a student");
            System.out.println("0. Back");
            System.out.print("Choose an option: ");

            try {
                switch (readMenuChoice()) {
                    case MenuOptions.STUDENT_ADD:
                        addStudent();
                        break;
                    case MenuOptions.STUDENT_VIEW_ALL:
                        viewAllStudents();
                        break;
                    case MenuOptions.STUDENT_SEARCH:
                        searchStudent();
                        break;
                    case MenuOptions.STUDENT_DEACTIVATE:
                        deactivateStudent();
                        break;
                    case MenuOptions.BACK:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please choose a number from the menu.");
                }
            } catch (EntityNotFoundException | InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void courseMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("---- COURSE MENU ----");
            System.out.println("1. Add new course");
            System.out.println("2. View all courses");
            System.out.println("3. Activate a course");
            System.out.println("4. Deactivate a course");
            System.out.println("0. Back");
            System.out.print("Choose an option: ");

            try {
                switch (readMenuChoice()) {
                    case MenuOptions.COURSE_ADD:
                        addCourse();
                        break;
                    case MenuOptions.COURSE_VIEW_ALL:
                        viewAllCourses();
                        break;
                    case MenuOptions.COURSE_ACTIVATE:
                        activateCourse();
                        break;
                    case MenuOptions.COURSE_DEACTIVATE:
                        deactivateCourse();
                        break;
                    case MenuOptions.BACK:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please choose a number from the menu.");
                }
            } catch (EntityNotFoundException | InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void enrollmentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("---- ENROLLMENT MENU ----");
            System.out.println("1. Enroll a student in a course");
            System.out.println("2. View enrollments for a student");
            System.out.println("3. Mark enrollment as completed");
            System.out.println("4. Mark enrollment as cancelled");
            System.out.println("0. Back");
            System.out.print("Choose an option: ");

            try {
                switch (readMenuChoice()) {
                    case MenuOptions.ENROLL_ADD:
                        enrollStudent();
                        break;
                    case MenuOptions.ENROLL_VIEW_FOR_STUDENT:
                        viewEnrollmentsForStudent();
                        break;
                    case MenuOptions.ENROLL_MARK_COMPLETED:
                        markEnrollmentCompleted();
                        break;
                    case MenuOptions.ENROLL_MARK_CANCELLED:
                        markEnrollmentCancelled();
                        break;
                    case MenuOptions.BACK:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please choose a number from the menu.");
                }
            } catch (EntityNotFoundException | InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ------------------------------------------------------ student actions

    private static void addStudent() {
        String firstName = readLine("First name: ");
        String lastName = readLine("Last name: ");
        String email = readLine("Email (press Enter to skip): ");
        String batch = readLine("Batch: ");

        Student student;
        if (InputValidator.isNullOrEmpty(email)) {
            student = studentService.addStudent(firstName, lastName, batch);
        } else {
            student = studentService.addStudent(firstName, lastName, email, batch);
        }
        System.out.println("Student added: " + student);
    }

    private static void viewAllStudents() {
        List<Student> students = studentService.listStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private static void searchStudent() {
        int id = readId("Student ID: ");
        Student student = studentService.findStudentById(id);
        System.out.println(student);
        System.out.println("Display name: " + student.getDisplayName());
    }

    private static void deactivateStudent() {
        int id = readId("Student ID to deactivate: ");
        Student student = studentService.deactivateStudent(id);
        System.out.println("Student deactivated: " + student);
    }

    // ------------------------------------------------------- course actions

    private static void addCourse() {
        String name = readLine("Course name: ");
        String description = readLine("Description: ");
        int duration = readId("Duration in weeks: ");

        Course course = courseService.addCourse(name, description, duration);
        System.out.println("Course added: " + course);
    }

    private static void viewAllCourses() {
        List<Course> courses = courseService.listCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    private static void activateCourse() {
        int id = readId("Course ID to activate: ");
        System.out.println("Course activated: " + courseService.activateCourse(id));
    }

    private static void deactivateCourse() {
        int id = readId("Course ID to deactivate: ");
        System.out.println("Course deactivated: " + courseService.deactivateCourse(id));
    }

    // -------------------------------------------------- enrollment actions

    private static void enrollStudent() {
        int studentId = readId("Student ID: ");
        int courseId = readId("Course ID: ");
        Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseId);
        System.out.println("Enrollment created: " + enrollment);
    }

    private static void viewEnrollmentsForStudent() {
        int studentId = readId("Student ID: ");
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsForStudent(studentId);
        if (enrollments.isEmpty()) {
            System.out.println("This student has no enrollments.");
            return;
        }
        for (Enrollment enrollment : enrollments) {
            System.out.println(enrollment);
        }
    }

    private static void markEnrollmentCompleted() {
        int id = readId("Enrollment ID: ");
        System.out.println("Updated: " + enrollmentService.markCompleted(id));
    }

    private static void markEnrollmentCancelled() {
        int id = readId("Enrollment ID: ");
        System.out.println("Updated: " + enrollmentService.markCancelled(id));
    }

    // ---------------------------------------------------------- input helpers

    /** Reads one line; exits cleanly if the input stream has ended. */
    private static String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            System.out.println();
            System.out.println("Input ended. Goodbye!");
            System.exit(0);
        }
        return scanner.nextLine();
    }

    /** Reads a menu choice; returns -1 for anything that is not a number. */
    private static int readMenuChoice() {
        if (!scanner.hasNextLine()) {
            System.out.println();
            System.out.println("Input ended. Goodbye!");
            System.exit(0);
        }
        String text = scanner.nextLine();
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /** Reads a positive whole number (used for IDs and durations). */
    private static int readId(String prompt) {
        String text = readLine(prompt);
        return InputValidator.parsePositiveInt(text, prompt.replace(":", "").trim());
    }
}
