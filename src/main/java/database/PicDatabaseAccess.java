package database;

import models.ImageModel;
import models.PhotographerModel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PicDatabaseAccess extends ISQLiteDatabaseAccess implements IDatabaseSetup, IPhotographerDAL, IImageDAL {

    /*
    calls constructor of ISQLiteDatabaseAccess
     */
    public PicDatabaseAccess() {
        super("picdb");
        translator = new PicDbModelTranslator();
    }

    private PicDbModelTranslator translator;

    @Override
    public List<PhotographerModel> getAllPhotographers() {

        PreparedStatement stmt = null;
        var photographer = new ArrayList<PhotographerModel>();

        try {

            prepareConnectionForStatement(false, Connection.TRANSACTION_READ_COMMITTED);

            stmt = prepareStatementForCommit(
                    PREPARED.PHOTOGRAPHER_SELECT_ALL
            );

            var result = stmt.executeQuery();

            commitStatement(true);

            while(result. next()){
                photographer.add(translator.resultSetToPhotographModel(result));
            }

        } catch (SQLException ex) {

            handleExeption(ex);
        }
        finally {

            closeStatement(stmt);
        }

        return photographer;
    }

    @Override
    public void addPhotographer(PhotographerModel photographer) {

        PreparedStatement stmt = null;

        try {

            prepareConnectionForStatement(false, Connection.TRANSACTION_SERIALIZABLE);

            stmt = prepareStatementForCommit(
                    PREPARED.PHOTOGRAPHER_INSERT,
                    new StatementParam<>(photographer.getFirstName(), String.class),
                    new StatementParam<>(photographer.getSurName(), String.class),
                    new StatementParam<>(photographer.getBirthDate(), Date.class),
                    new StatementParam<>(photographer.getNotes(), String.class)
            );

            stmt.executeUpdate();

            commitStatement(true);

        } catch (SQLException ex) {

            handleExeption(ex);
        }
        finally {

            closeStatement(stmt);
        }
    }

    @Override
    public PhotographerModel getPhotographer(int id) {

        return getAllPhotographers().stream()
                .filter(photographer -> photographer.getId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public void editPhotographer(PhotographerModel model) {

        PreparedStatement stmt = null;

        try {

            prepareConnectionForStatement(false, Connection.TRANSACTION_SERIALIZABLE);

            stmt = prepareStatementForCommit(
                    PREPARED.PHOTOGRAPHER_UPDATE,
                    new StatementParam(model.getFirstName(), String.class),
                    new StatementParam(model.getSurName(), String.class),
                    new StatementParam(model.getBirthDate(), Date.class),
                    new StatementParam(model.getNotes(), String.class),
                    new StatementParam(model.getId(), Integer.class)
            );

            stmt.executeUpdate();

            commitStatement(true);

        } catch (SQLException ex) {

            handleExeption(ex);
        }
        finally {

            closeStatement(stmt);
        }
    }

    @Override
    public void deletePhotographer(int id) {

        PreparedStatement stmt = null;

        try {

            prepareConnectionForStatement(false, Connection.TRANSACTION_SERIALIZABLE);

            stmt = prepareStatementForCommit(
                    PREPARED.PHOTOGRAPHER_DELETE,
                    new StatementParam(id, Integer.class)
            );

            stmt.executeUpdate();

            commitStatement(true);

        } catch (SQLException ex) {

            handleExeption(ex);
        }
        finally {

            closeStatement(stmt);
        }
    }

    /**
     * setting up the pic database, creating all tables
     * it they do not exist already
     */
    @Override
    public void setup() {

        PreparedStatement createPhotographer = null;
        PreparedStatement createImages = null;

        try {

            prepareConnectionForStatement(false, Connection.TRANSACTION_SERIALIZABLE);

            createPhotographer = prepareStatementForCommit(
                    PREPARED.PHOTOGRAPHER_CREATE
            );

            createPhotographer.executeUpdate();

            createImages = prepareStatementForCommit(
                    PREPARED.IMAGES_CREATE
            );

            createImages.executeUpdate();

            commitStatement(true);

        } catch (SQLException ex) {

            handleExeption(ex);
        }finally {

            closeStatement(createPhotographer);
            closeStatement(createImages);
        }
    }

    @Override
    public List<ImageModel> getAllImages() {

        PreparedStatement stmt = null;
        var images = new ArrayList<ImageModel>();

        try {

            prepareConnectionForStatement(false, Connection.TRANSACTION_READ_COMMITTED);

            stmt = prepareStatementForCommit(
                    PREPARED.IMAGES_SELECT_ALL
            );

            var result = stmt.executeQuery();

            commitStatement(true);

            while(result. next()){
                images.add(translator.resultSetToJPEGImageModel(result));
            }

        } catch (SQLException ex) {

            handleExeption(ex);
        }
        finally {

            closeStatement(stmt);
        }

        return images;
    }

    @Override
    public List<ImageModel> getByKeyword(String keyword) {
        return getAllImages().parallelStream()
                .filter(img -> img.getKeywords().contains(keyword))
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageModel> getByTitle(String title) {
        return getAllImages().parallelStream()
                .filter(img -> img.getTitle().equals(title))
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageModel> getByPhotographer(int photographerID) {
        return getAllImages().parallelStream()
                .filter(img -> img.getPhotographerID() == photographerID)
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageModel> getByIso(String iso) {
        return getAllImages().parallelStream()
                .filter(img -> Integer.toString(img.getIso()).equals(iso))
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageModel> getByAperture(String aperture) {
        return getAllImages().parallelStream()
                .filter(img -> img.getAperture().equals(aperture))
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageModel> getByModel(String model) {
        return getAllImages().parallelStream()
                .filter(img -> img.getModel().equals(model))
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageModel> getByFocalLength(String focalLength) {
        return getAllImages().parallelStream()
                .filter(img -> img.getFocalLength().equals(focalLength))
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageModel> getByExposure(String exposure) {
        return getAllImages().parallelStream()
                .filter(img -> img.getExposure().equals(exposure))
                .collect(Collectors.toList());
    }

    @Override
    public void addImage(ImageModel img) {
        //TODO
    }

    @Override
    public ImageModel getImage(int id) {
        return getAllImages().parallelStream()
                .filter(img -> img.getId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public void editImage(ImageModel model) {
        //TODO
    }

    @Override
    public void deleteImage(int id) {
        //TODO
    }

    @Override
    public void clearImages() {
        //TODO
    }
}
