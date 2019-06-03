package tests;

import database.DALFactory;
import database.MockDAL;
import database.PicDatabaseAccess;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DALFactoryTest {

    @Test
    void getImageDALValidID() {
        DALFactory factory = new DALFactory();

        assertEquals(PicDatabaseAccess.class, factory.getImageDAL(1).getClass());
    }

    @Test
    void getImageDALInvalidID() {
        DALFactory factory = new DALFactory();

        assertThrows(IllegalArgumentException.class, () -> factory.getImageDAL(2));
    }

    @Test
    void getImageDALInvalidID2() {
        DALFactory factory = new DALFactory();

        assertThrows(IllegalArgumentException.class, () -> factory.getImageDAL(0));
    }

    @Test
    void getPhotographerDALSQLite() {
        DALFactory factory = new DALFactory();

        assertEquals(PicDatabaseAccess.class, factory.getImageDAL(1).getClass());
    }

    @Test
    void getPhotographerDALMock() {
        DALFactory factory = new DALFactory();

        assertEquals(MockDAL.class, factory.getPhotographerDAL(2).getClass());
    }

    @Test
    void getPhotographerDALInvalidID() {
        DALFactory factory = new DALFactory();

        assertThrows(IllegalArgumentException.class, () -> factory.getPhotographerDAL(3));
    }
}