package database;

import models.PhotographerModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// TODO: implements all IDALs
public class MockDAL implements IPhotographerDAL {
    private HashMap<Integer, PhotographerModel> photographers = new HashMap<>();
    private int incrementingId = 1;


    @Override
    public List<PhotographerModel> getAllPhotographers() {
        List<PhotographerModel> photographerModelList = new ArrayList<>();
        for (var entry : photographers.entrySet()) {
            photographerModelList.add(entry.getValue());
        }
        return photographerModelList;
    }

    @Override
    public void addPhotographer(PhotographerModel photographer) {
        PhotographerModel newPhotographer = new PhotographerModel(incrementingId);
        copyFields(photographer, newPhotographer);

        this.photographers.put(incrementingId, newPhotographer);
        incrementingId++;
    }

    @Override
    public PhotographerModel getPhotographer(int id) {
        return this.photographers.get(id);
    }

    @Override
    public void editPhotographer(PhotographerModel photographer) {
        photographers.put(photographer.getId(), photographer);
    }

    @Override
    public void deletePhotographer(int id) {
        photographers.remove(id);
    }

    private PhotographerModel copyFields (PhotographerModel photographer, PhotographerModel newPhotographer) {
        newPhotographer.setBirthDate(photographer.getBirthDate());
        newPhotographer.setNotes(photographer.getNotes());
        newPhotographer.setFirstName(photographer.getFirstName());
        newPhotographer.setSurName(photographer.getSurName());
        newPhotographer.setDal(this);

        return newPhotographer;
    }
}
