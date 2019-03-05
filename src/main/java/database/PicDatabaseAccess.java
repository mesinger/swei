package database;

public class PicDatabaseAccess extends SQLiteDatabaseAccess implements IDatabaseSync, IDatabaseSetup {

    public PicDatabaseAccess() {
        super("picdb");
    }

    @Override
    public void setup() {


    }

    @Override
    public boolean sync() {
        return false;
    }
}
