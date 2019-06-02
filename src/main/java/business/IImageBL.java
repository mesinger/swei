package business;

import models.ImageModel;

import java.util.List;
import java.util.Optional;

/**
 * Provides various functions for getting images by certain criteria, as well as adding, editing, deleting or clearing
 */
public interface IImageBL extends BusinessLayer {

    List<ImageModel> getAllImages();
    List<ImageModel> getByKeyword(String keyword);
    List<ImageModel> getByTitle(String title);
    List<ImageModel> getByPath(String path);
    List<ImageModel> getByPhotographer(int photographerID);
    List<ImageModel> getByIso(String iso);
    List<ImageModel> getByAperture(String aperture);
    List<ImageModel> getByModel(String model);
    List<ImageModel> getByFocalLength(String focalLength);
    List<ImageModel> getByExposure(String exposure);
    void addImage(ImageModel img);
    Optional<ImageModel> getImage(int id);
    void editImage(ImageModel model);
    void deleteImage(int id);
    void clearImages();
}
