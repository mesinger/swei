package database;

import models.PhotographerModel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for converting a database result set to a PhotographerModel.
 * Used in the DAL.
 */
interface IPhotographerModelTranslator {

    PhotographerModel resultSetToPhotographModel(ResultSet rs) throws SQLException;
}
