package database;

/**
 * Interface for database connection establishement
 */
public interface IDatabaseConnection {

    boolean open();
    boolean close();
}
