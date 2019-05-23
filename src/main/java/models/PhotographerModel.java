package models;

import java.util.Date;

public class PhotographerModel {

    private String firstName;
    private String surName;
    private Date birthDate;
    private String notes;
    private int id;

    public PhotographerModel(int id) {
        this.id = id;
    }

    public PhotographerModel() {}

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // For debugging
    // TODO: remove this for final version
    public String toString() {
        return "Name: " + firstName + " " + surName +
                "\nBirthdate: " + birthDate.toString() +
                "\nNotes: " + notes;
    }
}
