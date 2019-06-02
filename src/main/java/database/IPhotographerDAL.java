package database;

import models.PhotographerModel;

import java.util.List;

/**
 * General DAL for getting, adding, editing and deleting photographers.
 * Loads data e.g. from a database and returns them as an PhotographerModel.
 */
public interface IPhotographerDAL {

    List<PhotographerModel> getAllPhotographers();

    void addPhotographer(PhotographerModel photographer);

    PhotographerModel getPhotographer(int id);

    void editPhotographer(PhotographerModel model);

    void deletePhotographer(int id);
}
