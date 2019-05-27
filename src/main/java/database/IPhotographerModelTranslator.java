package database;

import models.PhotographerModel;

import java.sql.ResultSet;
import java.sql.SQLException;

interface IPhotographerModelTranslator {

    PhotographerModel resultSetToPhotographModel(ResultSet rs) throws SQLException;
}
