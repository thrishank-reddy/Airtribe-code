package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Student;

import java.util.ArrayList;
import java.util.List;

/** In-memory storage for students (backed by an ArrayList). */
public class StudentRepository {

    private final List<Student> students = new ArrayList<>();

    public void save(Student student) {
        students.add(student);
    }

    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    /** Returns the student with the given id, or null if there is none. */
    public Student findById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    /** Returns true if something was removed. */
    public boolean deleteById(int id) {
        Student student = findById(id);
        if (student == null) {
            return false;
        }
        return students.remove(student);
    }
}
