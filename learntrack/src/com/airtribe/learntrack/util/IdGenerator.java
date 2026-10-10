package com.airtribe.learntrack.util;

/**
 * Hands out unique, auto-incrementing IDs.
 * Counters are static because they belong to the application, not to any single object.
 */
public class IdGenerator {

    private static int studentIdCounter = 1;
    private static int courseIdCounter = 1;
    private static int enrollmentIdCounter = 1;
    private static int trainerIdCounter = 1;

    // Utility class: no need to create objects of it.
    private IdGenerator() {
    }

    public static int getNextStudentId() {
        return studentIdCounter++;
    }

    public static int getNextCourseId() {
        return courseIdCounter++;
    }

    public static int getNextEnrollmentId() {
        return enrollmentIdCounter++;
    }

    public static int getNextTrainerId() {
        return trainerIdCounter++;
    }
}
