package models;

import java.sql.Date;

public abstract class ImageModel {

    protected String path;
    protected int width;
    protected int height;
    protected Orientation orientation;
    protected int iso;
    protected Date modifyDate;
    protected String title;
    protected String keywords;

    public int getId() {
        return id;
    }

    protected int id;

    public ImageModel() {}

    public ImageModel(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPhotographerID() {
        return photographerID;
    }

    public void setPhotographerID(int photographerID) {
        this.photographerID = photographerID;
    }

    protected int photographerID;
    protected String aperture;
    protected String model;
    protected String focalLength;
    protected String exposure;

    public void setPath(String path) {
        this.path = path;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public void setIso(int iso) {
        this.iso = iso;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setAperture(String aperture) {
        this.aperture = aperture;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setFocalLength(String focalLength) {
        this.focalLength = focalLength;
    }

    public void setExposure(String exposure) {
        this.exposure = exposure;
    }

    public String getAperture() {
        return aperture;
    }

    public String getModel() {
        return model;
    }

    public String getFocalLength() {
        return focalLength;
    }

    public String getExposure() {
        return exposure;
    }

    public ImageModel(String path, int width, int height, int orientation, int iso, Date modifyDate, String keywords,
                      String aperture, String model, String focalLength, String exposure) {

        this.path = path;
        this.width = (width < 0) ? -1 : width;
        this.height = (height < 0) ? -1 : height;

        this.orientation = new Orientation(orientation);
        this.iso = iso;
        this.modifyDate = modifyDate;
        this.keywords = keywords;

        this.aperture = aperture;
        this.model = model;
        this.focalLength = focalLength;
        this.exposure = exposure;
    }

    public final class Orientation{

        private int type;

        public Orientation(int type){
            this.type = type;
        }

        @Override
        public String toString() {
            switch (type){
                case ORIENTATION.Horizontal:
                    return "Horizontal";
                case ORIENTATION.Mirror_Horizontal:
                    return "Mirror horizontal";
                case ORIENTATION.Rotate_180:
                    return "Rotate 180";
                case ORIENTATION.Mirror_Vertical:
                    return "Mirror vertical";
                case ORIENTATION.Mirror_Horizontal_And_Rotate_270_CW:
                    return "Mirror horizontal and rotate 270";
                case ORIENTATION.Rotate_90_CW:
                    return "Rotate 90";
                case ORIENTATION.Mirror_Horizontal_And_Rotate_90_CW:
                    return "Mirror horizontal and rotate 90";
                case ORIENTATION.Rotate_270_CW:
                    return "Rotate 270";
                default:
                    return "invalid orientation";
            }
        }

        public int getType() {
            return type;
        }
    }

    public final class AspectRatio{

        private int x, y;

        public AspectRatio(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return Integer.toString(x) + ":" + Integer.toString(y);
        }
    }

    public String getPath() {
        return path;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public int getIso() {
        return iso;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public String getKeywords() {
        return keywords;
    }
}

interface ORIENTATION {

    int Horizontal = 1;
    int Mirror_Horizontal = 2;
    int Rotate_180 = 3;
    int Mirror_Vertical = 4;
    int Mirror_Horizontal_And_Rotate_270_CW = 5;
    int Rotate_90_CW = 6;
    int Mirror_Horizontal_And_Rotate_90_CW = 7;
    int Rotate_270_CW = 8;
}