package tests;

import models.PhotographerModel;
import org.junit.jupiter.api.Test;
import presentationModels.PhotographerPresentationModel;

import java.sql.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class PhotographerPresentationModelTest {

    @Test
    void getFullName() {
        PhotographerModel model = new PhotographerModel();

        model.setFirstName("Max");
        model.setSurName("Mustermann");

        PhotographerPresentationModel pres = new PhotographerPresentationModel(model);

        pres.loadDataFromModel();

        assertEquals("Max Mustermann", pres.getFullName());
    }

    @Test
    void loadDataFromModel() {
        PhotographerModel model = new PhotographerModel();

        model.setFirstName("Max");
        model.setSurName("Mustermann");

        PhotographerPresentationModel pres = new PhotographerPresentationModel(model);

        pres.loadDataFromModel();

        assertNotNull(pres.getFirstName());
    }

    @Test
    void saveDataToModelFailsWhenInvalid() {
        PhotographerModel model = new PhotographerModel();

        model.setFirstName("Max");
        model.setSurName("Mustermann");

        PhotographerPresentationModel pres = new PhotographerPresentationModel(model);

        pres.loadDataFromModel();

        // Birth date is not set, so this should throw an error
        assertThrows(IllegalStateException.class, pres::saveDataToModel);
    }

    @Test
    void saveDataToModelSucceeds() {
        PhotographerModel model = new PhotographerModel();

        model.setFirstName("Max");
        model.setSurName("Mustermann");
        model.setBirthDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));

        PhotographerPresentationModel pres = new PhotographerPresentationModel(model);

        pres.loadDataFromModel();

        pres.setFirstName("Hans");

        pres.saveDataToModel();

        assertEquals("Hans", pres.getFirstName());
    }

    @Test
    void getFirstName() {
        PhotographerModel model = new PhotographerModel();

        model.setFirstName("Max");
        model.setSurName("Mustermann");

        PhotographerPresentationModel pres = new PhotographerPresentationModel(model);

        pres.loadDataFromModel();

        assertEquals("Max", pres.getFirstName());
    }

    @Test
    void setFirstName() {
        PhotographerModel model = new PhotographerModel();

        model.setFirstName("Max");
        model.setSurName("Mustermann");

        PhotographerPresentationModel pres = new PhotographerPresentationModel(model);

        pres.loadDataFromModel();

        pres.setFirstName("Hans");

        assertEquals("Hans", pres.getFirstName());
    }

    @Test
    void isValidWithCurrentDate() {
        PhotographerModel model = new PhotographerModel();

        model.setFirstName("Max");
        model.setSurName("Mustermann");
        model.setBirthDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));

        PhotographerPresentationModel pres = new PhotographerPresentationModel(model);

        pres.loadDataFromModel();

        assertTrue(pres.isValid());
    }

    @Test
    void isValidWithEarlyDate() {
        PhotographerModel model = new PhotographerModel();

        model.setFirstName("Max");
        model.setSurName("Mustermann");

        model.setBirthDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis() - 133622650363L));

        PhotographerPresentationModel pres = new PhotographerPresentationModel(model);

        pres.loadDataFromModel();

        assertTrue(pres.isValid());
    }

    @Test
    void isValidFalseWithLaterDate() {
        PhotographerModel model = new PhotographerModel();

        model.setFirstName("Max");
        model.setSurName("Mustermann");

        model.setBirthDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis() + 133622650363L));

        PhotographerPresentationModel pres = new PhotographerPresentationModel(model);

        pres.loadDataFromModel();

        assertFalse(pres.isValid());
    }
}