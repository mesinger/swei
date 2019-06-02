package business;

import models.PhotographerModel;

import java.util.List;
import java.util.Optional;

/**
 * Provices functions to get and manage photographers (using the given DAL)
 */
public interface IPhotographerBL extends BusinessLayer {

    List<PhotographerModel> getAllPhotographers();
    void addPhotographer(PhotographerModel photographer);
    Optional<PhotographerModel> getPhotographer(int id);
    void editPhotographer(PhotographerModel model);
    void deletePhotographer(int id);
}
