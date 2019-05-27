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
    @Setter(AccessLevel.PRIVATE)
    private int id;
    private IPhotographerDAL dal;

    public PhotographerModel(int id) {
        this.id = id;
    }

    public void save() {
        if (dal == null) {
            throw new IllegalStateException("Can't save model without a DAL!");
        }

        if (id == 0) {
            dal.addPhotographer(this);
        } else {
            dal.editPhotographer(this);
        }
    }

    // For debugging
    // TODO: remove this for final version
    public String toString() {
        return "Name: " + firstName + " " + surName +
                "\nBirthdate: " + birthDate.toString() +
                "\nNotes: " + notes;
    }
}
