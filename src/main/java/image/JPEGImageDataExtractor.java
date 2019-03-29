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
public class JPEGImageDataExtractor implements IImageDataExtractor{

    /*
    extracts image data from jpeg in given path
    and returns data as JPEGImageData
     */
    public JPEGImageData extractExifAndIPTC(String path){

        if(!path.endsWith(".jpg") && !path.endsWith(".jpeg"))
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

            if(exifIFD0Directory != null){

                if(exifIFD0Directory.containsTag(EXIFID.ImageWidth))
                    width = exifIFD0Directory.getInt(EXIFID.ImageWidth);

                if(exifIFD0Directory.containsTag(EXIFID.ImageHeight))
                    height = exifIFD0Directory.getInt(EXIFID.ImageHeight);

                if(exifIFD0Directory.containsTag(EXIFID.Orientation))
                    orientation = exifIFD0Directory.getInt(EXIFID.Orientation);

                if(exifIFD0Directory.containsTag(EXIFID.ModifyDate))
                    modifydate = exifIFD0Directory.getDate(EXIFID.ModifyDate);
            }

            if(exifSubIFDDirectory != null){

                if(exifSubIFDDirectory.containsTag(EXIFID.ISO))
                    iso = exifSubIFDDirectory.getInt(EXIFID.ISO);
            }

            if(iptcDirectory != null){

                if(iptcDirectory.containsTag(IPTCID.Keywords))
                    keywords = iptcDirectory.getString(IPTCID.Keywords);
            }

            res = new JPEGImageData(path, width, height, orientation, iso, modifydate, keywords);

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
