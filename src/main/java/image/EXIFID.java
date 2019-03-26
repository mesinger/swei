package image;

/*
https://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/EXIF.html
 */
interface EXIFID {

    int ImageWidth = 0x100;     //ifd0
    int ImageHeight = 0x101;    //ifd0
    int Orientation = 0x112;    //ifd0
    int ModifyDate = 0x132;     //ifd0
    int ISO = 0x8827;           //exififd (subifd)
}
