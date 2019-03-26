package image;

/*
https://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/EXIF.html
 */
interface ORIENTATION {

    int Horizontal = 1;
    int Mirror_Horizontal = 2;
    int Rotate_180 = 3;
    int Mirror_Vertical = 4;
    int Mirror_Horizontal_And_Rotate_270_CW = 5;
    int Rotate_90_CW = 6;
    int Mirror_Horizontal_And_Rotate_90_CW = 7;
    int Rotate_270_CW = 8;
}
