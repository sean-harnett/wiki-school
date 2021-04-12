package com.wikischool.wikischool.main.ConnectionObjects.Queries;

import com.wikischool.wikischool.main.ConnectionObjects.ConnectionAbstraction.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Class to prepare statements and execute sql queries into a connected database.
 *
 * @author sean-harnett
 */
@Service
public class SqlQueryExecutor implements QueryArchetype<UUID> {

    private PreparedStatement preparedStatement;

    private DatabaseConnection databaseConnection;

    private ResultSet resultSet;

    @Autowired
    public SqlQueryExecutor(DatabaseConnection dbConnection) {
        this.databaseConnection = dbConnection;
    }

    public void readProperties(){
        this.databaseConnection.readProperties();
    }

    /**
     * Method to prepare the SQL statement.
     * ** Requires database connection.**
     * ** Requires a formatted sql statement.
     *
     * @param queryInformation Object holding all relevant attributes for the query.
     * @see SqlQueryInformation
     */
    private void prepareSqlStatement(SqlQueryInformation<UUID> queryInformation) throws SQLException {
        Connection conn;
        conn = databaseConnection.getConnectionObject();
        if (conn == null) {
            databaseConnection.establishConnection();
            conn = databaseConnection.getConnectionObject();

        }
        try {

            this.preparedStatement = conn.prepareStatement(queryInformation.getFormattedSqlStatement());



        } catch (SQLException e) {

            throw e;
        }
    }

    /**
     * Set the values for the previously prepared sql statements placeholders.
     *
     * @param queryInformation Object holding all relevant attributes for the query.
     * @see SqlQueryInformation
     */
    private void setAttributesToPreparedStatement(SqlQueryInformation<UUID> queryInformation) throws SQLException {
        try {
            int[] columnIndices = queryInformation.getAttributeSqlColumnIndices();
            Object[] attributes = queryInformation.getRecordAttributes();

            if (columnIndices != null && attributes != null) {
                for (int ix = 0; ix < attributes.length; ix++) {
                    this.preparedStatement.setObject(columnIndices[ix], attributes[ix]);
                }
            }
        } catch (SQLException e) {
            throw e;
        }
    }


    /**
     * Execute a statement that will make a change to a table, but does not return anything.
     *
     * @param queryInformation Object holding all relevant attributes for the query.
     * @see SqlQueryInformation
     */
    @Override
    public int executeUpdateStatement(SqlQueryInformation<UUID> queryInformation) throws SQLException {
        int effectedRecords;
        try {

            prepareSqlStatement(queryInformation);
            setAttributesToPreparedStatement(queryInformation);

            effectedRecords = this.preparedStatement.executeUpdate();

        } catch (SQLException e) {
          throw e;
        }
        return effectedRecords;
    }

    /**
     * Execute a SELECT query, and put the result into the ResultSet class member.
     *
     * @param queryInformation Object holding all relevant attributes for the query.
     * @see SqlQueryInformation
     */
    @Override
    public void executeQueryStatement(SqlQueryInformation<UUID> queryInformation) throws SQLException {
        try {
            prepareSqlStatement(queryInformation);
            setAttributesToPreparedStatement(queryInformation);

            this.resultSet = this.preparedStatement.executeQuery();

        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Use this method to get the ResultSet class member.
     * resultSet is set during a query previously executed.
     *
     * @return ResultSet
     */
    public ResultSet getResultSet() {
        return this.resultSet;
    }


    /**
     * Close SQL objects, and throw their exceptions if any are encountered.
     *
     * @throws SQLException The thrown exception
     */
    public void closeAll() throws SQLException {
        closeResultSet();
        closeConnection();
        closePreparedStatement();
    }

    /**
     * Method to close SQL objects, and handle the potential SQLExceptions.
     *
     * @return boolean, true if no exceptions, and false if exceptions.
     */
    public boolean closeAllAndHandleExceptions() {
        try {
            closeResultSet();
            closeConnection();
            closePreparedStatement();

        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public void closeResultSet() throws SQLException {
        if (this.resultSet == null) {
            return;
        }
        try {
            this.resultSet.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    public void closeConnection() throws SQLException {
        if (!this.databaseConnection.checkConnection()) {
            return;
        }
        try {
            this.databaseConnection.closeConnection();
        } catch (SQLException e) {
            throw e;
        }
    }

    public void closePreparedStatement() throws SQLException {
        if (this.preparedStatement == null) {
            return;
        }
        try {
            this.preparedStatement.close();
        } catch (SQLException e) {
            throw e;
        }
    }
}
