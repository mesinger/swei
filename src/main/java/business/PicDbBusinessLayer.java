package business;

import database.IDatabaseAccess;
import database.PicDatabaseAccess;
import models.ImageModel;
import models.PhotographerModel;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class PicDbBusinessLayer implements IPicDbBusinessLayer {

    private PicDatabaseAccess dal = null;

    @Override
    public List<ImageModel> getAllImages() {
        return dal.getAllImages();
    }

    @Override
    public List<ImageModel> getByKeyword(String keyword) {
        return getAllImages().parallelStream()
                .filter(img -> Objects.requireNonNullElse(img.getKeywords(), "").contains(Objects.requireNonNullElse(keyword, "")))
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageModel> getByTitle(String title) {
        return getAllImages().parallelStream()
                .filter(img -> img.getTitle() != null)
                .filter(img -> img.getTitle().equals(Objects.requireNonNullElse(title, "")))
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageModel> getByPath(String path) {
        return getAllImages().parallelStream()
                .filter(img -> img.getPath().equals(Objects.requireNonNullElse(path, "")))
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageModel> getByPhotographer(int photographerID) {

        if(photographerID < 0) return List.of();

        return getAllImages().parallelStream()
                .filter(img -> img.getPhotographerID() == photographerID)
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageModel> getByIso(String iso) {
        return getAllImages().parallelStream()
                .filter(img -> Integer.toString(img.getIso()).equals(Objects.requireNonNullElse(iso, "")))
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageModel> getByAperture(String aperture) {
        return getAllImages().parallelStream()
                .filter(img -> img.getAperture().equals(Objects.requireNonNullElse(aperture, "")))
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageModel> getByModel(String model) {
        return getAllImages().parallelStream()
                .filter(img -> img.getModel().equals(Objects.requireNonNullElse(model, "")))
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageModel> getByFocalLength(String focalLength) {
        return getAllImages().parallelStream()
                .filter(img -> img.getFocalLength().equals(Objects.requireNonNullElse(focalLength, "")))
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageModel> getByExposure(String exposure) {
        return getAllImages().parallelStream()
                .filter(img -> img.getExposure().equals(Objects.requireNonNullElse(exposure, "")))
                .collect(Collectors.toList());
    }

    @Override
    public void addImage(ImageModel img) {
        if(img == null) return;
        dal.addImage(img);
    }

    @Override
    public Optional<ImageModel> getImage(int id) {
        if(id < 0) return Optional.empty();
        return Optional.ofNullable(dal.getImage(id));
    }

    @Override
    public void editImage(ImageModel model) {
        if(model != null){
            dal.editImage(model);
        }
    }

    @Override
    public void deleteImage(int id) {
        if(id >= 0){
            dal.deleteImage(id);
        }
    }

    @Override
    public void clearImages() {
        getAllImages().parallelStream()
                .forEach(img -> deleteImage(img.getId()));
    }

    @Override
    public List<PhotographerModel> getAllPhotographers() {
        return dal.getAllPhotographers();
    }

    @Override
    public void addPhotographer(PhotographerModel photographer) {
        if(photographer != null){
            dal.addPhotographer(photographer);
        }
    }

    @Override
    public Optional<PhotographerModel> getPhotographer(int id) {
        if(id < 0) return Optional.empty();
        return Optional.ofNullable(dal.getPhotographer(id));
    }

    @Override
    public void editPhotographer(PhotographerModel model) {
        if(model != null){
            dal.editPhotographer(model);
        }
    }

    @Override
    public void deletePhotographer(int id) {
        if(id >= 0){
            dal.deletePhotographer(id);
        }
    }

    @Override
    public void setDAL(IDatabaseAccess dal) {

        if(dal instanceof PicDatabaseAccess){
            this.dal = (PicDatabaseAccess) dal;
        }
    }
}
