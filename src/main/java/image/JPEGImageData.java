package image;

import java.util.Date;

public class JPEGImageData extends IImageData{

    public JPEGImageData(String path, int width, int height, int orientation, int iso, Date modifyDate, String keywords){
        super(path, width, height, orientation, iso, modifyDate, keywords);
    }
}
