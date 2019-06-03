package tests;

import database.MockDAL;
import models.PhotographerModel;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PhotographerDALTests {

    private Date makeDate(String yyyy_mm_dd) {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd");
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = isoFormat.parse(yyyy_mm_dd);
            return date;
        } catch (ParseException e) {
            e.getStackTrace();
        }
        return null;
    }

    private MockDAL prepareDAL(List<PhotographerModel> photographerModelList) {
        MockDAL dal = new MockDAL();
        for (var photographer : photographerModelList) {
            dal.addPhotographer(photographer);
        }

        return dal;
    }

    private PhotographerModel createPhotographerModel() {
        PhotographerModel photographer = new PhotographerModel();

        return null;
    }

    @Test
    public void addNewPhotographer () {
        PhotographerModel max = new PhotographerModel();
        max.setFirstName("Max");
        max.setSurName("Mustermann");

        List<PhotographerModel> photographers = new ArrayList<>();
        photographers.add(max);
        MockDAL dal = prepareDAL(photographers);



        assertNotNull(dal.getAllPhotographers());
        assertEquals(dal.getPhotographer(1).getFirstName(), "Max");

    }
}
