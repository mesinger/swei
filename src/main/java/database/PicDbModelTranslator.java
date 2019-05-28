package database;

import models.ImageModel;
import models.PhotographerModel;

import java.sql.ResultSet;
import java.sql.SQLException;

class PicDbModelTranslator implements IPhotographerModelTranslator, IImageModelTranslator {

    @Override
    public ImageModel resultSetToImageModel(ResultSet rs) {
        return null;
    }

    @Override
    public PhotographerModel resultSetToPhotographModel(ResultSet rs) throws SQLException {

        PhotographerModel model = new PhotographerModel(rs.getInt("id"));

        model.setBirthDate(rs.getDate("dateofbirth"));
        model.setFirstName(rs.getString("firstname"));
        model.setSurName(rs.getString("lastname"));
        model.setNotes(rs.getString("notes"));

        return model;
    }
}
