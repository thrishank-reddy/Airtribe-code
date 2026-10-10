package com.airtribe.learntrack.service;

import com.airtribe.learntrack.constants.AppConstants;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.List;

/** Business logic for students. */
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /** Adds a student with an email. */
    public Student addStudent(String firstName, String lastName, String email, String batch) {
        String validFirst = InputValidator.requireNonEmpty(firstName, "First name");
        String validLast = InputValidator.requireNonEmpty(lastName, "Last name");
        String validBatch = InputValidator.requireNonEmpty(batch, "Batch");
        String validEmail = InputValidator.requireValidEmail(email);

        Student student = new Student(IdGenerator.getNextStudentId(), validFirst, validLast, validEmail, validBatch);
        studentRepository.save(student);
        return student;
    }



    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    /** Throws EntityNotFoundException if the student does not exist. */
    public Student findStudentById(int id) {
        Student student = studentRepository.findById(id);
        if (student == null) {
            throw new EntityNotFoundException("Student", id);
        }
        return student;
    }

    public Student updateStudent(int id, String firstName, String lastName, String email, String batch) {
        Student student = findStudentById(id);
        student.setFirstName(InputValidator.requireNonEmpty(firstName, "First name"));
        student.setLastName(InputValidator.requireNonEmpty(lastName, "Last name"));
        student.setEmail(InputValidator.requireValidEmail(email));
        student.setBatch(InputValidator.requireNonEmpty(batch, "Batch"));
        return student;
    }

    /** Permanently removes a student from the list. */
    public void removeStudent(int id) {
        if (!studentRepository.deleteById(id)) {
            throw new EntityNotFoundException("Student", id);
        }
    }

    /** Soft delete: keeps the record but marks the student inactive. */
    public Student deactivateStudent(int id) {
        Student student = findStudentById(id);
        student.setActive(false);
        return student;
    }

    public boolean hasEmail(Student student) {
        return !AppConstants.DEFAULT_EMAIL.equals(student.getEmail());
    }
}
