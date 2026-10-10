package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.util.IdGenerator;

import java.util.List;

/** Business logic for enrollments. Uses StudentService and CourseService to validate IDs. */
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             StudentService studentService,
                             CourseService courseService) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public Enrollment enrollStudent(int studentId, int courseId) {
        Student student = studentService.findStudentById(studentId);   // throws if missing
        Course course = courseService.findCourseById(courseId);        // throws if missing

        if (!student.isActive()) {
            throw new InvalidInputException("Student " + studentId + " is inactive and cannot be enrolled.");
        }
        if (!course.isActive()) {
            throw new InvalidInputException("Course " + courseId + " is inactive and cannot accept enrollments.");
        }
        if (hasActiveEnrollment(studentId, courseId)) {
            throw new InvalidInputException("Student " + studentId + " is already enrolled in course " + courseId + ".");
        }

        Enrollment enrollment = new Enrollment(IdGenerator.getNextEnrollmentId(), studentId, courseId);
        enrollmentRepository.save(enrollment);
        return enrollment;
    }

    /** Lists enrollments for one student (the student must exist). */
    public List<Enrollment> getEnrollmentsForStudent(int studentId) {
        studentService.findStudentById(studentId);
        return enrollmentRepository.findByStudentId(studentId);
    }

    public List<Enrollment> listEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment findEnrollmentById(int id) {
        Enrollment enrollment = enrollmentRepository.findById(id);
        if (enrollment == null) {
            throw new EntityNotFoundException("Enrollment", id);
        }
        return enrollment;
    }

    public Enrollment markCompleted(int enrollmentId) {
        return changeStatus(enrollmentId, EnrollmentStatus.COMPLETED);
    }

    public Enrollment markCancelled(int enrollmentId) {
        return changeStatus(enrollmentId, EnrollmentStatus.CANCELLED);
    }

    // Only ACTIVE enrollments can move to COMPLETED or CANCELLED.
    private Enrollment changeStatus(int enrollmentId, EnrollmentStatus newStatus) {
        Enrollment enrollment = findEnrollmentById(enrollmentId);
        if (enrollment.getStatus() != EnrollmentStatus.ACTIVE) {
            throw new InvalidInputException("Enrollment " + enrollmentId + " is already "
                    + enrollment.getStatus() + " and cannot be changed.");
        }
        enrollment.setStatus(newStatus);
        return enrollment;
    }

    private boolean hasActiveEnrollment(int studentId, int courseId) {
        for (Enrollment enrollment : enrollmentRepository.findByStudentId(studentId)) {
            if (enrollment.getCourseId() == courseId && enrollment.getStatus() == EnrollmentStatus.ACTIVE) {
                return true;
            }
        }
        return false;
    }
}
