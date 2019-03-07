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
            "id INT ASC PRIMARY KEY NOT NULL," +
            "firstname VARCHAR(255) NOT NULL," +
            "surname VARCHAR(255) NOT NULL," +
            "dateofbirth DATE NOT NULL," +
            "address VARCHAR(255)" +
            ");";

    String IMAGES_CREATE =
            "CREATE TABLE IF NOT EXISTS images (" +
                    "id INT ASC PRIMARY KEY NOT NULL," +
                    "filename VARCHAR(255) NOT NULL," +
                    "surname VARCHAR(255) NOT NULL," +
                    "dateofbirth DATE NOT NULL," +
                    "address VARCHAR(255)" +
                    ");";
}
