package database;

import models.PhotographerModel;

import java.util.List;

public interface IPhotographerDAL {

    List<PhotographerModel> getAllPhotographers();

    void addPhotographer(PhotographerModel photographer);

    PhotographerModel getPhotographer(int id);

    void editPhotographer(PhotographerModel model);

    void deletePhotographer(int id);
}
