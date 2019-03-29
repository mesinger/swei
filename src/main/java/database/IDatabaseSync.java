package database;

/**
 * Interface for database connections, which need to sync data on startup
 */
interface IDatabaseSync {

    boolean sync();
}
