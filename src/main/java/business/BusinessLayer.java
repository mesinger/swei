package business;

import database.IDatabaseAccess;

public interface BusinessLayer {

    /**
     * @param dal The DAL to access in the functions of this business layer
     */
    void setDAL(IDatabaseAccess dal);
}
