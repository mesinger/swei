package database;

public class DALFactory {
    public IImageDAL getImageDAL(int id) {
        if (id == 1) {
            return new PicDatabaseAccess();
        } else {
            throw new IllegalArgumentException("Invalid DAL id!");
        }
    }

    public IPhotographerDAL getPhotographerDAL(int id) {
        if (id == 1) {
            return new PicDatabaseAccess();
        } else if (id == 2) {
            return new MockDAL();
        } else {
            throw new IllegalArgumentException("Invalid DAL id!");
        }
    }
}
