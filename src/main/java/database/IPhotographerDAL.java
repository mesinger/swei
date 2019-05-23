package database;

import models.PhotographerModel;

import java.util.List;

public interface IPhotographerDAL {
    List<PhotographerModel> getAllPhotographers();

    void addPhotographer();
    PhotographerModel getPhotographer();
    void editPhotographer();
    void deletePhotographer();
}
