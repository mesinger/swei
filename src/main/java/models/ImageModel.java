package models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
@NoArgsConstructor
public abstract class ImageModel {

    protected String path;
    protected int width;
    protected int height;
    @Setter(AccessLevel.PRIVATE)
    protected Orientation orientation;
    protected int iso;
    protected Date modifyDate;
    protected String title;
    protected String keywords;
    protected int id;
    protected int photographerID;
    protected String aperture;
    protected String model;
    protected String focalLength;
    protected String exposure;

    public ImageModel(int id) {
        this.id = id;
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

    public int getOrientationInt() {
        return orientation.getType();
    }

    public void setOrientation(int orientation){
        this.orientation = new Orientation(orientation);
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