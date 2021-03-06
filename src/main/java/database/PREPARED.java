package database;

/**
 * prepared statements for pic database
 * naming conventions: table_task
 */
interface PREPARED {

    String TABLE_CHECK_IF_EXISTS =
            "IF EXISTS (" +
                    "SELECT * FROM INFORMATION_SCHEMA.TABLES" +
                    "WHERE TABLE_NAME = ?" +
                    ")\n" +
                    "BEGIN\n" +
                    "PRINT 'YES'\n" +
                    "END";

    String PHOTOGRAPHER_CREATE =
            "CREATE TABLE IF NOT EXISTS photographer (" +
                    "id INTEGER PRIMARY KEY," +
                    "firstname VARCHAR(255) NULL," +
                    "lastname VARCHAR(255) NOT NULL," +
                    "dateofbirth DATE NOT NULL," +
                    "notes VARCHAR(255)" +
                    ");";

    String PHOTOGRAPHER_INSERT =
            "INSERT INTO photographer " +
                    "VALUES (NULL, ?, ?, ?, ?);";

    String IMAGES_CREATE =
            "CREATE TABLE IF NOT EXISTS images (" +
                    "id INTEGER PRIMARY KEY," +
                    "filepath VARCHAR(255) NOT NULL," +
                    "title VARCHAR(255) NULL," +
                    "width INTEGER NULL," +
                    "height INTEGER NULL," +
                    "orientation INTEGER NULL," +
                    "iso INTEGER NULL," +
                    "fk_photographer INTEGER NOT NULL," +
                    "modifydate DATETIME NULL," +
                    "aperture VARCHAR(255) NULL," +
                    "model VARCHAR(255) NULL," +
                    "focallength VARCHAR(255) NULL," +
                    "exposure VARCHAR(255) NULL," +
                    "keywords VARCHAR(255) NULL" +
                    ");";

    String IMAGES_INSERT =
            "INSERT INTO images (filepath, title, width, height, orientation, iso, fk_photographer, modifydate," +
                    " aperture, model, focallength, exposure, keywords)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    String PHOTOGRAPHER_SELECT_ALL =
            "SELECT * FROM photographer;";

    String PHOTOGRAPHER_UPDATE =
            "UPDATE photographer " +
                    "SET " +
                    "firstname = ?, " +
                    "lastname = ?, " +
                    "dateofbirth = ?, " +
                    "notes = ? " +
                    "WHERE id = ?;";

    String PHOTOGRAPHER_DELETE =
            "DELETE FROM photographer" +
                    "WHERE id = ?;";

    String IMAGES_SELECT_ALL =
            "SELECT * FROM images;";

    String IMAGES_UPDATE =
            "UPDATE images " +
                    "SET " +
                    "filepath = ?, " +
                    "title = ?, " +
                    "width = ?, " +
                    "height = ?, " +
                    "orientation = ?, " +
                    "iso = ?, " +
                    "fk_photographer = ?, " +
                    "modifydate = ?, " +
                    "aperture = ?, " +
                    "model = ?, " +
                    "focallength = ?, " +
                    "exposure = ?, " +
                    "keywords = ? " +
                    "WHERE id = ?;";

    String IMAGES_DELETE =
            "DELETE FROM images " +
                    "WHERE id = ?;";
}
