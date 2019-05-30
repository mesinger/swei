package database;

import models.ImageModel;

import java.util.List;

public interface IImageDAL {

    List<ImageModel> getAllImages();
    void addImage(ImageModel img);
    ImageModel getImage(int id);
    void editImage(ImageModel model);
    void deleteImage(int id);
}
