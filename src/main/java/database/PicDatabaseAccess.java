package database;

public class PicDatabaseAccess implements ISQLiteDatabaseAccess, IDatabaseSync, IDatabaseSetup {

    @Override
    public void setup() {


    }

    @Override
    public boolean sync() {
        return false;
    }
}
