package database;

/**
 * Interface for database connection establishement
 */
public interface IDatabaseConnection {

    //opens a connection to a given database
    boolean open();

    //closes a connection to a opened database, and all opened statements, results, ...
    boolean close();
}
