package database;

import image.JPEGImageData;
import models.ImageModel;
import models.PhotographerModel;

import java.sql.ResultSet;
import java.sql.SQLException;

class PicDbModelTranslator implements IPhotographerModelTranslator, IImageModelTranslator {

    @Override
    public ImageModel resultSetToJPEGImageModel(ResultSet rs) throws SQLException {

        ImageModel model = new JPEGImageData(rs.getInt("id"));

        model.setPath(rs.getString("filepath"));
        model.setTitle(rs.getString("title"));
        model.setWidth(rs.getInt("width"));
        model.setHeight(rs.getInt("height"));
        model.setOrientation(rs.getInt("orientation"));
        model.setIso(rs.getInt("iso"));
        model.setPhotographerID(rs.getInt("fk_photographer"));
        model.setModifyDate(rs.getDate("modifyDate"));
        model.setAperture(rs.getString("aperture"));
        model.setModel(rs.getString("model"));
        model.setFocalLength(rs.getString("focalLength"));
        model.setExposure(rs.getString("exposure"));
        model.setKeywords(rs.getString("keywords"));

        return model;
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
