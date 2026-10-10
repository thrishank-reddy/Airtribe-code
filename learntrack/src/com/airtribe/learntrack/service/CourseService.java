package com.airtribe.learntrack.service;

import com.airtribe.learntrack.constants.AppConstants;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.List;

/** Business logic for courses. */
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course addCourse(String courseName, String description, int durationInWeeks) {
        String validName = InputValidator.requireNonEmpty(courseName, "Course name");
        String validDescription = InputValidator.requireNonEmpty(description, "Description");
        if (durationInWeeks <= 0 || durationInWeeks > AppConstants.MAX_DURATION_WEEKS) {
            throw new InvalidInputException("Duration must be between 1 and "
                    + AppConstants.MAX_DURATION_WEEKS + " weeks.");
        }

        Course course = new Course(IdGenerator.getNextCourseId(), validName, validDescription, durationInWeeks);
        courseRepository.save(course);
        return course;
    }

    public List<Course> listCourses() {
        return courseRepository.findAll();
    }

    public Course findCourseById(int id) {
        Course course = courseRepository.findById(id);
        if (course == null) {
            throw new EntityNotFoundException("Course", id);
        }
        return course;
    }

    public Course updateCourse(int id, String courseName, String description, int durationInWeeks) {
        Course course = findCourseById(id);
        course.setCourseName(InputValidator.requireNonEmpty(courseName, "Course name"));
        course.setDescription(InputValidator.requireNonEmpty(description, "Description"));
        if (durationInWeeks <= 0 || durationInWeeks > AppConstants.MAX_DURATION_WEEKS) {
            throw new InvalidInputException("Duration must be between 1 and "
                    + AppConstants.MAX_DURATION_WEEKS + " weeks.");
        }
        course.setDurationInWeeks(durationInWeeks);
        return course;
    }

    public void removeCourse(int id) {
        if (!courseRepository.deleteById(id)) {
            throw new EntityNotFoundException("Course", id);
        }
    }

    public Course activateCourse(int id) {
        Course course = findCourseById(id);
        course.setActive(true);
        return course;
    }

    public Course deactivateCourse(int id) {
        Course course = findCourseById(id);
        course.setActive(false);
        return course;
    }
}
