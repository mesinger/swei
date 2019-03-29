package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * base class for connections to sqlite databases
 */
abstract class ISQLiteDatabaseAccess implements IDatabaseConnection {

    //class name for jdbc connector
    private final static String JDBC_CLASSNAME = "org.sqlite.JDBC";

    //connection instance
    protected Connection conn;

    //database name
    protected final String databaseName;
    //connection url for DriverManager
    private final String connectionUrl;

    ISQLiteDatabaseAccess(String databaseName){

        this.databaseName = databaseName;
        connectionUrl = "jdbc:sqlite:" + databaseName + ".db";
    }

    @Override
    public final boolean open(){

        try {

            Class.forName(JDBC_CLASSNAME);
            conn = DriverManager.getConnection(connectionUrl);

        } catch (ClassNotFoundException e) {

            System.err.println("SQL: JDBC connector not found. Is the lib missing?");
            e.printStackTrace();

            return false;
        } catch (SQLException e) {

            System.err.println("SQL: Error while connecting");
            e.printStackTrace();

            return false;
        }

        return true;
    }

    @Override
    public final boolean close(){

        try {

            conn.close();

        } catch (SQLException e) {

            System.err.println("SQL: Error while closing connection");
            e.printStackTrace();

            return false;
        }

        return true;
    }
}
