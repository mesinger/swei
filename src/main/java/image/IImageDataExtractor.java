package image;

import models.ImageModel;

public interface IImageDataExtractor {

    ImageModel extractExifAndIPTC(String path);
}
