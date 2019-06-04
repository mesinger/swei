package tests;

import database.MockDAL;
import models.PhotographerModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PhotographerDALTests {

    private Date makeDate(String yyyy_mm_dd) {
        return java.sql.Date.valueOf(yyyy_mm_dd);
    }

    private MockDAL prepareDAL(List<PhotographerModel> photographerModelList) {
        MockDAL dal = new MockDAL();
        for (var photographer : photographerModelList) {
            dal.addPhotographer(photographer);
        }

        return dal;
    }

    private PhotographerModel createPhotographerModel(String firstName, String surName, String yyyy_mm_dd, String notes) {
        PhotographerModel photographer = new PhotographerModel();
        photographer.setNotes(notes);
        photographer.setSurName(surName);
        photographer.setFirstName(firstName);
        photographer.setBirthDate(makeDate(yyyy_mm_dd));

        return photographer;
    }


    private MockDAL preparePhotographers() {
        List<PhotographerModel> photographers = new ArrayList<>();
        photographers.add(createPhotographerModel("Max", "Mustermann",
                "1990-12-3", "note"));
        photographers.add(createPhotographerModel("Martina", "Musterfrau",
                "1992-12-3", "note"));
        photographers.add(createPhotographerModel("Testperson", "Test",
                "1992-12-3", "note"));

        MockDAL dal = prepareDAL(photographers);

        return dal;
    }

    @Test
    public void addNewPhotographer () {
        MockDAL dal = preparePhotographers();

        assertNotNull(dal.getAllPhotographers());
        assertEquals(dal.getPhotographer(1).getFirstName(), "Max");
        assertEquals(dal.getPhotographer(1).getSurName(), "Mustermann");
        assertEquals(dal.getPhotographer(1).getNotes(), "note");
        assertEquals(dal.getPhotographer(1).getBirthDate(), makeDate("1990-12-3"));
    }

    @Test
    public void incrementingIdWorks() {
        MockDAL dal = preparePhotographers();

        dal.deletePhotographer(2);
        assertNotNull(dal.getAllPhotographers());
        assertNotNull(dal.getPhotographer(1));
        assertNull(dal.getPhotographer(2));
    }

    @Test
    public void accessAfterDeletedId() {
        MockDAL dal = preparePhotographers();

        dal.deletePhotographer(1);
        dal.deletePhotographer(2);
        assertNull(dal.getPhotographer(1));
        assertNull(dal.getPhotographer(2));
        assertNotNull(dal.getPhotographer(3));
        assertEquals(dal.getPhotographer(3).getFirstName(), "Testperson");
    }

    @Test
    public void addNewPhotographerAfterDeletingOthers() {
        MockDAL dal = preparePhotographers();

        dal.deletePhotographer(1);
        dal.deletePhotographer(2);
        dal.deletePhotographer(3);
        dal.addPhotographer(createPhotographerModel("Mathias", "Baumgartinger", "1990-12-11", null));
        assertEquals(dal.getPhotographer(4).getFirstName(), "Mathias");
    }

}
