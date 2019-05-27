package presentationModels;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import models.PhotographerModel;

import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;

public class PhotographerPresentationModel {
    private PhotographerModel model;

    private StringProperty firstName = new SimpleStringProperty();

    private StringProperty surName = new SimpleStringProperty();

    private StringProperty notes = new SimpleStringProperty();

    private ObjectProperty<LocalDate> birthDate = new SimpleObjectProperty<>();

    public String getFullName() {
        return fullName.get();
    }

    public StringBinding fullNameProperty() {
        return fullName;
    }

    private StringBinding fullName;


    public PhotographerPresentationModel (PhotographerModel model) {
        this.model = model;

        firstName.addListener((s,o,n) -> fullName.invalidate());
        surName.addListener((s,o,n) -> fullName.invalidate());

        fullName = new StringBinding() {
            @Override
            protected String computeValue() {
                return String.format("%s %s", getFirstName(), getSurName().trim());
            }
        };
    }

    public void loadDataFromModel() {
        firstName.setValue(model.getFirstName());
        surName.setValue(model.getSurName());
        notes.setValue(model.getNotes());
        birthDate.setValue(model.getBirthDate().toLocalDate());
    }

    public void saveDataToModel() {
        model.setFirstName(firstName.getValue());
        model.setSurName(surName.getValue());
        model.setNotes(notes.getValue());

        java.util.Date util_date = (java.util.Date) Date.from(birthDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sql_date = new java.sql.Date(util_date.getTime());
        model.setBirthDate(sql_date);

        model.save();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getSurName() {
        return surName.get();
    }

    public StringProperty surNameProperty() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName.set(surName);
    }

    public String getNotes() {
        return notes.get();
    }

    public StringProperty notesProperty() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

    public LocalDate getBirthDate() {
        return birthDate.get();
    }

    public ObjectProperty<LocalDate> birthDateProperty() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate.set(birthDate);
    }

    public boolean isValid() {
        return (getSurName() != null && !getSurName().isEmpty() && getBirthDate() != null &&
                getBirthDate().isBefore(LocalDate.now()));
    }
}
