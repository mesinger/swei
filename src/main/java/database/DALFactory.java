package database;

public class DALFactory {
    public IImageDAL getImageDAL(int id) {
        if (id == 1) {
            return new PicDatabaseAccess();
        } else {
            System.out.println("Invalid DAL id!");
            return null;
        }
    }

    public IPhotographerDAL getPhotographerDAL(int id) {
        if (id == 1) {
            return new PicDatabaseAccess();
        } else if (id == 2) {
            return new MockDAL();
        } else {
            System.out.println("Invalid DAL id!");
            return null;
        }
    }
}
