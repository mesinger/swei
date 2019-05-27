package database;

import models.ImageModel;

import java.sql.ResultSet;

interface IImageModelTranslator {

    ImageModel resultSetToImageModel(ResultSet rs);
}
