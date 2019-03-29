package image;

import java.util.Date;

public abstract class IImageData {

    protected String path;
    protected int width;
    protected int height;
    protected Orientation orientation;
    protected ISO iso;
    protected Date modifyDate;
    protected String keywords;

    public IImageData(String path, int width, int height, int orientation, int iso, Date modifyDate, String keywords) {

        this.path = path;
        this.width = width;
        this.height = height;
        this.orientation = new Orientation(orientation);
        this.iso = new ISO(iso);
        this.modifyDate = modifyDate;
        this.keywords = keywords;
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
    }

    public final class ISO {

        private int iso;

        public ISO(int iso) {
            this.iso = iso;
        }

        public int getIso() {
            return iso;
        }

        @Override
        public String toString() {
            return Integer.toString(iso);
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
