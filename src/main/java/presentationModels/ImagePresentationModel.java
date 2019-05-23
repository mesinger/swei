package presentationModels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import models.ImageModel;
import models.PhotographerModel;

public class ImagePresentationModel {
    public ImagePresentationModel(ImageModel model) {
        this.model = model;
    }

    public void loadDataFromModel() {
        path.setValue(model.getPath());
        width.setValue(model.getWidth());
        height.setValue(model.getHeight());
        orientation.setValue(model.getOrientation().toString());
        iso.setValue(String.valueOf(model.getIso()));
        modifyDate.setValue(model.getModifyDate().toString());
        keywords.setValue(model.getKeywords());
        aperture.setValue(model.getAperture());
        cameraModel.setValue(model.getModel());
        focalLength.setValue(model.getFocalLength());
        exposure.setValue(model.getExposure());
        title.setValue(model.getTitle());
        photographerID.setValue(model.getPhotographerID());
    }

    public void saveDataToModel() {
        model.setKeywords(getKeywords());
        model.setTitle(getTitle());
        model.setPhotographerID(getPhotographerID());
    }

    public String getPath() {
        return path.get();
    }

    public StringProperty pathProperty() {
        return path;
    }

    public void setPath(String path) {
        this.path.set(path);
    }

    public int getWidth() {
        return width.get();
    }

    public IntegerProperty widthProperty() {
        return width;
    }

    public void setWidth(int width) {
        this.width.set(width);
    }

    public int getHeight() {
        return height.get();
    }

    public IntegerProperty heightProperty() {
        return height;
    }

    public void setHeight(int height) {
        this.height.set(height);
    }

    public String getOrientation() {
        return orientation.get();
    }

    public StringProperty orientationProperty() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation.set(orientation);
    }

    public String getIso() {
        return iso.get();
    }

    public StringProperty isoProperty() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso.set(iso);
    }

    public String getModifyDate() {
        return modifyDate.get();
    }

    public StringProperty modifyDateProperty() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate.set(modifyDate);
    }

    public String getKeywords() {
        return keywords.get();
    }

    public StringProperty keywordsProperty() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords.set(keywords);
    }

    public String getAperture() {
        return aperture.get();
    }

    public StringProperty apertureProperty() {
        return aperture;
    }

    public void setAperture(String aperture) {
        this.aperture.set(aperture);
    }

    public String getCameraModel() {
        return cameraModel.get();
    }

    public StringProperty cameraModelProperty() {
        return cameraModel;
    }

    public void setCameraModel(String cameraModel) {
        this.cameraModel.set(cameraModel);
    }

    public String getFocalLength() {
        return focalLength.get();
    }

    public StringProperty focalLengthProperty() {
        return focalLength;
    }

    public void setFocalLength(String focalLength) {
        this.focalLength.set(focalLength);
    }

    public String getExposure() {
        return exposure.get();
    }

    public StringProperty exposureProperty() {
        return exposure;
    }

    public void setExposure(String exposure) {
        this.exposure.set(exposure);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public int getPhotographerID() {
        return photographerID.get();
    }

    public IntegerProperty photographerIDProperty() {
        return photographerID;
    }

    public void setPhotographerID(int photographerID) {
        this.photographerID.set(photographerID);
    }

    private ImageModel model;
    private PhotographerModel photographerModel;

    protected StringProperty path;
    protected IntegerProperty width;
    protected IntegerProperty height;
    protected StringProperty orientation;
    protected StringProperty iso;
    protected StringProperty modifyDate;
    protected StringProperty keywords;
    protected StringProperty aperture;
    protected StringProperty cameraModel;
    protected StringProperty focalLength;
    protected StringProperty exposure;
    protected StringProperty title;
    protected IntegerProperty photographerID;
}
