package database;

/**
 * Interface for database connections, which need to set the database up (e.g. sqlite)
 */
interface IDatabaseSetup {

    void setup();
}
