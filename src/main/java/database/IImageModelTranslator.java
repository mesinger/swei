package database;

import models.ImageModel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for converting a database result set to an ImageModel.
 * Used in the DAL.
 */
interface IImageModelTranslator {

    ImageModel resultSetToJPEGImageModel(ResultSet rs) throws SQLException;
}
