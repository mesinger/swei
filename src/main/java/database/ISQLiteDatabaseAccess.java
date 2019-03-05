package database;

/**
 * Interface for connections to sqlite databases
 */
interface ISQLiteDatabaseAccess extends IDatabaseConnection {

    @Override
    default boolean open(){



        return true;
    }

    @Override
    default boolean close(){

        return true;
    }
}
