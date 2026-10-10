package com.airtribe.learntrack.entity;

import com.airtribe.learntrack.constants.AppConstants;

/** A learner. Extends Person and adds batch + active flag. */
public class Student extends Person {

    private String batch;
    private boolean active;

    public Student() {
        super();
        this.active = true;
    }

    /** Constructor overloading: student without an email. */
//    public Student(int id, String firstName, String lastName, String batch) {
//        this(id, firstName, lastName, AppConstants.DEFAULT_EMAIL, batch);
//    }

    /** Full constructor. */
    public Student(int id, String firstName, String lastName, String email, String batch) {
        super(id, firstName, lastName, email);
        this.batch = batch;
        this.active = true;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /** Method overriding: adds the batch to the base display name. */
    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " (Batch: " + batch + ")";
    }

    @Override
    public String toString() {
        return "Student{id=" + getId()
                + ", name=" + getFirstName() + " " + getLastName()
                + ", email=" + getEmail()
                + ", batch=" + batch
                + ", active=" + active + "}";
    }
}
