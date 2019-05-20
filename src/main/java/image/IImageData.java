package image;

import java.sql.Date;

public abstract class IImageData {

    protected String path;
    protected int width;
    protected int height;
    protected Orientation orientation;
    protected int iso;
    protected Date modifyDate;
    protected String keywords;
    protected String aperture;
    protected String model;
    protected String focal_length;
    protected String exposure;

    public String getAperture() {
        return aperture;
    }

    public String getModel() {
        return model;
    }

    public String getFocal_length() {
        return focal_length;
    }

    public String getExposure() {
        return exposure;
    }

    public IImageData(String path, int width, int height, int orientation, int iso, Date modifyDate, String keywords,
                      String aperture, String model, String focal_length, String exposure) {

        this.path = path;
        this.width = (width < 0) ? -1 : width;
        this.height = (height < 0) ? -1 : height;

        this.orientation = new Orientation(orientation);
        this.iso = iso;
        this.modifyDate = modifyDate;
        this.keywords = keywords;

        this.aperture = aperture;
        this.model = model;
        this.focal_length = focal_length;
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
