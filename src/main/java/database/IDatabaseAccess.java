package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * mostly utility functions used for communicating with
 * different databases
 */
abstract class IDatabaseAccess implements IDatabaseConnection{

    //connection instance
    protected Connection conn;

    /**
     * closes a given prepared statement
     * @param stmt
     */
    protected void closeStatement(PreparedStatement stmt) {
        try{

            conn.setAutoCommit(true);

            if(stmt != null)
                stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * prepares the connection for a prepared statement
     * @param autoCommit
     * @param transactionLevel
     * @throws SQLException
     */
    protected void prepareConnectionForStatement(boolean autoCommit, int transactionLevel) throws SQLException {
        conn.setAutoCommit(autoCommit);
        conn.setTransactionIsolation(transactionLevel);
    }

    /**
     * preapres the statment for commit
     * by applying the given params to the statement
     * @param sql prepared statement sql query
     * @param params parameters applied to stmt
     */
    protected PreparedStatement prepareStatementForCommit(String sql, StatementParam ... params) throws SQLException {

        final PreparedStatement stmt = conn.prepareStatement(sql);

        AtomicInteger parameterIndex = new AtomicInteger(1);

        Arrays.stream(params)
                .forEach(param -> {

                    try {

                        if(param.getCls().equals(Integer.class))
                            stmt.setInt(parameterIndex.getAndIncrement(), (Integer) param.getParameter());
                        else if(param.getCls().equals(String.class))
                            stmt.setString(parameterIndex.getAndIncrement(), (String) param.getParameter());
                        else if(param.getCls().equals(Date.class))
                            stmt.setDate(parameterIndex.getAndIncrement(), (Date) param.getParameter());

                    } catch (SQLException ex){
                        ex.printStackTrace();
                    }
                });

        return stmt;
    }

    /**
     * commits the statement and resets autocommit
     * @param autoCommit
     * @throws SQLException
     */
    protected void commitStatement(boolean autoCommit) throws SQLException {
        conn.commit();
        conn.setAutoCommit(autoCommit);
    }

    /**
     * handles any kind of exception
     */
    protected void handleExeption(SQLException ex){

        System.err.println("SQL: Error while inserting row photographer");
        ex.printStackTrace();

        try {
            conn.rollback();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
