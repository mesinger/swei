package business;

import database.IDatabaseAccess;

public interface BusinessLayer {

    void setDAL(IDatabaseAccess dal);
}
