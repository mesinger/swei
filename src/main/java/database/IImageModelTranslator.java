package database;

import models.ImageModel;

import java.sql.ResultSet;
import java.sql.SQLException;

interface IImageModelTranslator {

    ImageModel resultSetToJPEGImageModel(ResultSet rs) throws SQLException;
}
