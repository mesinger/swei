package database;

import models.ImageModel;

import java.util.List;

/**
 * General DAL for getting, adding, editing and deleting images.
 * Loads data e.g. from a database and returns them as an ImageModel.
 */
public interface IImageDAL {

    List<ImageModel> getAllImages();
    void addImage(ImageModel img);
    ImageModel getImage(int id);
    void editImage(ImageModel model);
    void deleteImage(int id);
}
