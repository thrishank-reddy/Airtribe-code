package com.airtribe.learntrack.constants;

/** Menu option numbers, kept in one place so Main has no "magic numbers". */
public class MenuOptions {

    // Main menu
    public static final int MAIN_STUDENTS = 1;
    public static final int MAIN_COURSES = 2;
    public static final int MAIN_ENROLLMENTS = 3;
    public static final int MAIN_EXIT = 0;

    // Shared "go back" option for sub menus
    public static final int BACK = 0;

    // Student menu
    public static final int STUDENT_ADD = 1;
    public static final int STUDENT_VIEW_ALL = 2;
    public static final int STUDENT_SEARCH = 3;
    public static final int STUDENT_DEACTIVATE = 4;

    // Course menu
    public static final int COURSE_ADD = 1;
    public static final int COURSE_VIEW_ALL = 2;
    public static final int COURSE_ACTIVATE = 3;
    public static final int COURSE_DEACTIVATE = 4;

    // Enrollment menu
    public static final int ENROLL_ADD = 1;
    public static final int ENROLL_VIEW_FOR_STUDENT = 2;
    public static final int ENROLL_MARK_COMPLETED = 3;
    public static final int ENROLL_MARK_CANCELLED = 4;

    private MenuOptions() {
    }
}
