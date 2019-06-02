package tests;

import image.JPEGImageData;
import models.ImageModel;
import org.junit.jupiter.api.Test;
import presentationModels.ImagePresentationModel;

import static org.junit.jupiter.api.Assertions.*;

class ImagePresentationModelTest {

    @Test
    void loadDataFromModelGetsTitle() {
        ImageModel model = new JPEGImageData();

        model.setTitle("My title");

        ImagePresentationModel pres = new ImagePresentationModel(model);

        pres.loadDataFromModel();

        assertEquals("My title", pres.getTitle());
    }

    @Test
    void saveDataToModelChangesTitle() {
        ImageModel model = new JPEGImageData();

        model.setTitle("My title");

        ImagePresentationModel pres = new ImagePresentationModel(model);

        pres.loadDataFromModel();

        pres.setTitle("Other title");

        pres.saveDataToModel();

        assertEquals("Other title", model.getTitle());
    }

    @Test
    void saveDataToModelChangesKeywords() {
        ImageModel model = new JPEGImageData();

        model.setKeywords("key1");

        ImagePresentationModel pres = new ImagePresentationModel(model);

        pres.loadDataFromModel();

        pres.setKeywords("key1 key2");

        pres.saveDataToModel();

        assertEquals("key1 key2", model.getKeywords());
    }

    @Test
    void getOrientationAsString() {
        ImageModel model = new JPEGImageData();
        model.setOrientation(1);

        ImagePresentationModel pres = new ImagePresentationModel(model);
        pres.loadDataFromModel();

        assertEquals("Horizontal", pres.getOrientation());
    }
}