package database;

import models.ImageModel;

import java.util.List;

public interface IImageDAL {
    List<ImageModel> getAllImages();

    List<ImageModel> getByKeyword(String keyword);

    List<ImageModel> getByTitle(String title);

    List<ImageModel> getByPhotographer(int photographerID);

    List<ImageModel> getByIso(String iso);

    List<ImageModel> getByAperture(String aperture);

    List<ImageModel> getByModel(String model);

    List<ImageModel> getByFocalLength(String focalLength);

    List<ImageModel> getByExposure(String exposure);

    void addImage(ImageModel img);

    ImageModel getImage(int id);

    void editImage(ImageModel model);

    void deleteImage(int id);

    void clearImages();
}
