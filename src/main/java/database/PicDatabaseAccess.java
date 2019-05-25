package database;

import models.ImageModel;
import models.PhotographerModel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PicDatabaseAccess extends ISQLiteDatabaseAccess implements IDatabaseSetup, IPhotographerDAL {

    /*
    calls constructor of ISQLiteDatabaseAccess
     */
    public PicDatabaseAccess() {
        super("picdb");
    }

    public boolean addImage(ImageModel data){

        PreparedStatement stmt = null;

        try {

            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            stmt = conn.prepareStatement(PREPARED.IMAGES_INSERT);
            stmt.setString(1, data.getPath());
            stmt.setInt(2, data.getWidth());
            stmt.setInt(3, data.getHeight());
            stmt.setInt(4, data.getOrientation().getType());
            stmt.setInt(5, data.getIso());
            stmt.setDate(6, data.getModifyDate());
            stmt.setString(7, data.getKeywords());

            int result = stmt.executeUpdate();

            conn.commit();

            conn.setAutoCommit(true);

            return result == 1;

        } catch (SQLException e) {

            System.err.println("SQL: Error while inserting row");
            e.printStackTrace();

            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        finally {

            closeStatement(stmt);

            return false;
        }
    }



    @Override
    public List<PhotographerModel> getAllPhotographers() {
        return null;
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
        return null;
    }

    @Override
    public void editPhotographer(PhotographerModel model) {

    }

    @Override
    public void deletePhotographer(int id) {

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
