package ui;

import business.IImageBL;
import business.IPicDbBusinessLayer;
import business.PicDbBusinessLayer;
import database.PicDatabaseAccess;

public abstract class IController {

    protected IPicDbBusinessLayer bl = new PicDbBusinessLayer();

    protected boolean initializeDatabaseConnection() {
        PicDatabaseAccess db = new PicDatabaseAccess();
        db.setup();

        boolean opened = db.open();

        if(opened) {
            bl.setDAL(db);
        }

        return opened;
    }
}
