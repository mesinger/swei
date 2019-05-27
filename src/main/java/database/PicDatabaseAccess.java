package database;

import models.ImageModel;
import models.PhotographerModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class PicDatabaseAccess extends ISQLiteDatabaseAccess implements IDatabaseSetup, IPhotographerDAL {

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

        PreparedStatement stmt = null;
        PhotographerModel model = new PhotographerModel();

        try {

            prepareConnectionForStatement(false, Connection.TRANSACTION_READ_COMMITTED);

            stmt = prepareStatementForCommit(
                    PREPARED.PHOTOGRAPHER_SELECT_BY_ID,
                    new StatementParam(id, Integer.class)
            );

            var result = stmt.executeQuery();

            commitStatement(true);

            if(result.next())
                model = translator.resultSetToPhotographModel(result);

        } catch (SQLException ex) {

            handleExeption(ex);
        }
        finally {

            closeStatement(stmt);
        }

        return model;
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
}
