package database;

import models.ImageModel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PicDatabaseAccess extends ISQLiteDatabaseAccess implements IDatabaseSync, IDatabaseSetup {

    /*
    calls constructor of ISQLiteDatabaseAccess
     */
    public PicDatabaseAccess() {
        super("picdb");
    }

    /**
     * adds a photographer to the database
     * @param firstname
     * @param surname
     * @param dateofbirth
     * @param address
     */
    public boolean addPhotographer(final String firstname, final String surname, final Date dateofbirth, final String address) {

        PreparedStatement stmt = null;

        try {

            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            stmt = conn.prepareStatement(PREPARED.PHOTOGRAPHER_INSERT);
            stmt.setString(1, firstname);
            stmt.setString(2, surname);
            stmt.setDate(3, dateofbirth);
            stmt.setString(4, address);

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

    /**
     * setting up the pic database, creating all tables
     * it they do not exist already
     */
    @Override
    public void setup() {

        PreparedStatement createPhotographer = null;
        PreparedStatement createImages = null;

        try {

            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            int resultUpdate;

            //create photographer table
            createPhotographer = conn.prepareStatement(PREPARED.PHOTOGRAPHER_CREATE);
            resultUpdate = createPhotographer.executeUpdate();

            //create images table
            createImages = conn.prepareStatement(PREPARED.IMAGES_CREATE);
            resultUpdate = createImages.executeUpdate();

            conn.commit();

        } catch (SQLException e) {

            System.err.println("SQL: Error while setting up database");
            e.printStackTrace();

            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {

            try {

                if(createPhotographer != null)
                    createPhotographer.close();

                if(createImages != null)
                    createImages.close();

                conn.setAutoCommit(true);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean sync() {
        return false;
    }

    private void closeStatement(PreparedStatement stmt) {
        try{

            conn.setAutoCommit(true);

            if(stmt != null)
                stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
