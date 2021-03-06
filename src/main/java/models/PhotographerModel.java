package models;

import database.IPhotographerDAL;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
@NoArgsConstructor
public class PhotographerModel {

    private String firstName;
    private String surName;
    private Date birthDate;
    private String notes;
    private int id;

    public PhotographerModel(int id) {
        this.id = id;
    }

    public String toString() {
        return "Name: " + firstName + " " + surName +
                "\nBirthdate: " + birthDate.toString() +
                "\nNotes: " + notes;
    }
}
