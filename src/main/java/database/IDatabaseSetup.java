package database;

/**
 * Interface for database connections, which need to set the database up (e.g. sqlite)
 */
interface IDatabaseSetup {

    /**
     * Create the database and set up the required tables
     */
    void setup();
}
