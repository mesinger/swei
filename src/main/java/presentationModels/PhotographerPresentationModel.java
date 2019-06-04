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
import java.util.logging.Logger;

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
                if (getFirstName() == null && getSurName() == null) {
                    return "";
                }
                return String.format("%s %s", getFirstName(), getSurName().trim());
            }
        };
    }

    /**
     * Sets all variables to the values in the model which was passed in the constructor
     */
    public void loadDataFromModel() {
        firstName.setValue(model.getFirstName());
        surName.setValue(model.getSurName());
        notes.setValue(model.getNotes());
        if (model.getBirthDate() != null) {
            birthDate.setValue(model.getBirthDate().toLocalDate());
        } else {
            Logger.getGlobal().warning("Birth date was null - why does this photographer exist?");
        }
    }

    /**
     * Saves the modifiable values back to the model which was passed at the beginning
     */
    public void saveDataToModel() {
        Logger.getGlobal().info("Saving presentationModel data to model");

        if (!isValid()) {
            throw new IllegalStateException("Can only save to model if the presentation model is valid!");
        }

        model.setFirstName(firstName.getValue());
        model.setSurName(surName.getValue());
        model.setNotes(notes.getValue());

        java.util.Date util_date = (java.util.Date) Date.from(birthDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sql_date = new java.sql.Date(util_date.getTime());
        model.setBirthDate(sql_date);
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

    /**
     * @return True if the surname and the birth date have been set, and the birth date is not after today.
     */
    public boolean isValid() {
        return (getSurName() != null && !getSurName().isEmpty() && getBirthDate() != null &&
                !getBirthDate().isAfter(LocalDate.now()));
    }
}
