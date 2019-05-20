package image;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.iptc.IptcDirectory;
import com.drew.metadata.jpeg.JpegDirectory;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

/*
abstract class for extracting
exif and iptc data from images
 */
public class JPEGImageDataExtractor implements IImageDataExtractor{

    /*
    extracts image data from jpeg in given path
    and returns data as JPEGImageData
     */
    public JPEGImageData extractExifAndIPTC(String path){

        if(!path.toLowerCase().endsWith(".jpg") && !path.toLowerCase().endsWith(".jpeg"))
            return null;

        JPEGImageData res = null;

        Metadata metadata = null;

        try {

            metadata = ImageMetadataReader.readMetadata(new File(path));

            JpegDirectory jpegDirectory = metadata.getFirstDirectoryOfType(new JpegDirectory().getClass());
            ExifIFD0Directory exifIFD0Directory = metadata.getFirstDirectoryOfType(new ExifIFD0Directory().getClass());
            ExifSubIFDDirectory exifSubIFDDirectory = metadata.getFirstDirectoryOfType(new ExifSubIFDDirectory().getClass());
            IptcDirectory iptcDirectory = metadata.getFirstDirectoryOfType(new IptcDirectory().getClass());

            int width = 0;
            int height = 0;
            int orientation = -1;
            Date modifydate = null;
            int iso = 0;
            String keywords = null;
            String aperture = null;
            String model = null;
            String focal_length = null;
            String exposure = null;

            if(exifIFD0Directory != null){

                if(exifIFD0Directory.containsTag(EXIFID.ImageWidth))
                    width = exifIFD0Directory.getInt(EXIFID.ImageWidth);

                if(exifIFD0Directory.containsTag(EXIFID.ImageHeight))
                    height = exifIFD0Directory.getInt(EXIFID.ImageHeight);

                if(exifIFD0Directory.containsTag(EXIFID.Orientation))
                    orientation = exifIFD0Directory.getInt(EXIFID.Orientation);

                if(exifIFD0Directory.containsTag(EXIFID.ModifyDate))
                    modifydate = new Date(exifIFD0Directory.getDate(EXIFID.ModifyDate).getTime());

                if (exifIFD0Directory.containsTag(exifIFD0Directory.TAG_MODEL))
                    model = exifIFD0Directory.getString(exifIFD0Directory.TAG_MODEL);
            }

            if(exifSubIFDDirectory != null){

                if(exifSubIFDDirectory.containsTag(EXIFID.ISO))
                    iso = exifSubIFDDirectory.getInt(EXIFID.ISO);

                if (exifSubIFDDirectory.containsTag(exifSubIFDDirectory.TAG_APERTURE))
                    aperture = String.valueOf(exifSubIFDDirectory.getDouble(exifSubIFDDirectory.TAG_APERTURE));

                if (exifSubIFDDirectory.containsTag(exifSubIFDDirectory.TAG_FOCAL_LENGTH))
                    focal_length = exifSubIFDDirectory.getString(exifSubIFDDirectory.TAG_FOCAL_LENGTH);

                if (exifSubIFDDirectory.containsTag(exifSubIFDDirectory.TAG_EXPOSURE_TIME))
                    exposure = exifSubIFDDirectory.getString(exifSubIFDDirectory.TAG_EXPOSURE_TIME);
            }

            if(iptcDirectory != null){

                if(iptcDirectory.containsTag(IPTCID.Keywords))
                    keywords = iptcDirectory.getString(IPTCID.Keywords);
            }

            res = new JPEGImageData(path, width, height, orientation, iso, modifydate, keywords, aperture, model, focal_length, exposure);

        } catch (ImageProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MetadataException e) {
            e.printStackTrace();
        }

        return res;
    }
}
