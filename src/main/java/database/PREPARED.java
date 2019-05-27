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
            "firstname VARCHAR(255) NOT NULL," +
            "surname VARCHAR(255) NOT NULL," +
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
                    "width INTEGER NULL," +
                    "height INTEGER NULL," +
                    "orientation INTEGER NULL," +
                    "iso INTEGER NULL," +
                    "modifyDate DATETIME NULL," +
                    "keywords VARCHAR(255) NULL" +
                    ");";

    String IMAGES_INSERT =
            "INSERT INTO images " +
                    "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?);";

    String PHOTOGRAPHER_SELECT_ALL =
            "SELECT * FROM photographer;";

    String PHOTOGRAPHER_SELECT_BY_ID =
            "SELECT * FROM photographer " +
                    "WHERE id = ?;";

    String PHOTOGRAPHER_UPDATE =
            "UPDATE photographer" +
                    "SET firstname = ?, surname = ?, dateofbirth = ?, notes = ?" +
                    "WHERE id = ?;";

    String PHOTOGRAPHER_DELETE =
            "DELETE FROM photographer" +
                    "WHERE id = ?;";
}
