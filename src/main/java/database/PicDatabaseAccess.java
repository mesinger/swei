package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PicDatabaseAccess extends ISQLiteDatabaseAccess implements IDatabaseSync, IDatabaseSetup {

    public PicDatabaseAccess() {
        super("picdb");
    }

    /**
     * setting up the pic database, creating all tables
     * it they do not exist already
     */
    @Override
    public void setup() {

        PreparedStatement createPhotographer = null;

        try {

            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            int resultUpdate;

            createPhotographer = conn.prepareStatement(PREPARED.PHOTOGRAPHER_CREATE);
            resultUpdate = createPhotographer.executeUpdate();

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
}
