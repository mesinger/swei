package image;

import java.sql.Date;

public class JPEGImageData extends IImageData {

    public JPEGImageData(String path, int width, int height, int orientation, int iso, Date modifyDate, String keywords, String aperture, String model, String focal_length, String exposure) {
        super(path, width, height, orientation, iso, modifyDate, keywords, aperture, model, focal_length, exposure);
    }
}
