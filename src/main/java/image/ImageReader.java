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
import java.util.Date;

/*
abstract class for extracting
exif and iptc data from images
 */
public class ImageReader {

    private JpegDirectory jpegDirectory;
    private ExifIFD0Directory exifIFD0Directory;
    private ExifSubIFDDirectory exifSubIFDDirectory;
    private IptcDirectory iptcDirectory;

    /*
    extracts image data from file in given path
    and returns data as Image
     */
    public Image extractExifAndIPTC(String path){

        Image res = null;

        Metadata metadata = null;

        try {

            metadata = ImageMetadataReader.readMetadata(new File(path));

            jpegDirectory = metadata.getFirstDirectoryOfType(new JpegDirectory().getClass());
            exifIFD0Directory = metadata.getFirstDirectoryOfType(new ExifIFD0Directory().getClass());
            exifSubIFDDirectory = metadata.getFirstDirectoryOfType(new ExifSubIFDDirectory().getClass());
            iptcDirectory = metadata.getFirstDirectoryOfType(new IptcDirectory().getClass());

            int width = 0;
            int height = 0;
            int orientation = -1;
            Date modifydate = null;
            int iso = 0;

            if(exifIFD0Directory.containsTag(EXIFID.ImageWidth))
                width = exifIFD0Directory.getInt(EXIFID.ImageWidth);

            if(exifIFD0Directory.containsTag(EXIFID.ImageHeight))
                height = exifIFD0Directory.getInt(EXIFID.ImageHeight);

            if(exifIFD0Directory.containsTag(EXIFID.Orientation))
                orientation = exifIFD0Directory.getInt(EXIFID.Orientation);

            if(exifIFD0Directory.containsTag(EXIFID.ModifyDate))
                modifydate = exifIFD0Directory.getDate(EXIFID.ModifyDate);

            if(exifSubIFDDirectory.containsTag(EXIFID.ISO))
                iso = exifSubIFDDirectory.getInt(EXIFID.ISO);

            res = new Image(path, width, height, orientation, iso, modifydate);

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
