package com.airtribe.learntrack.entity;

/** A trainer. Shows a second subclass of Person (not heavily used by the menu). */
public class Trainer extends Person {

    private String expertise;

//    public Trainer() {
//        super();
//    }

    public Trainer(int id, String firstName, String lastName, String email, String expertise) {
        super(id, firstName, lastName, email);
        this.expertise = expertise;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    @Override
    public String getDisplayName() {
        return "Trainer " + super.getDisplayName() + " - " + expertise;
    }
}
