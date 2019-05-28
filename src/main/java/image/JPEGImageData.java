package image;

import lombok.NoArgsConstructor;
import models.ImageModel;

import java.sql.Date;

@NoArgsConstructor
public class JPEGImageData extends ImageModel {

    public JPEGImageData(int id){
        super(id);
    }

    public JPEGImageData(String path, int width, int height, int orientation, int iso, Date modifyDate, String keywords, String aperture, String model, String focal_length, String exposure) {
        super(path, width, height, orientation, iso, modifyDate, keywords, aperture, model, focal_length, exposure);
    }
}
