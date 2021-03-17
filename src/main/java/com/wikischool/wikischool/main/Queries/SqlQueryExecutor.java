package com.wikischool.wikischool.main.Queries;

import com.wikischool.wikischool.main.ConnectionObjects.JdbcConnection;
import com.wikischool.wikischool.main.Models.Interfaces.QueryArchetype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Class to prepare statements and execute sql queries into a connected database.
 * @author sean-harnett
 */

@Component
public class SqlQueryExecutor implements QueryArchetype<UUID> {

    private PreparedStatement preparedStatement;

    private JdbcConnection jdbcConnection;

    private ResultSet resultSet;

    public SqlQueryExecutor() {
    }

    /**
     * Method to prepare the SQL statement.
     * ** Requires database connection.**
     * ** Requires a formatted sql statement.
     * @see SqlQueryInformation
     * @param queryInformation Object holding all relevant attributes for the query.
     *
     */
    private void prepareSqlStatement(SqlQueryInformation<UUID> queryInformation) {
        Connection conn;
        conn = jdbcConnection.getConnectionObj();
        if(conn == null){
            jdbcConnection.establishConnection();
            conn = jdbcConnection.getConnectionObj();
        }
        try {
            this.preparedStatement = conn.prepareStatement(queryInformation.getFormattedSqlStatement());
        } catch (SQLException e) {
            System.out.println("Error preparing statement");
            System.out.println(e);
        }
    }

    /**
     * Set the values for the previously prepared sql statements placeholders.
     *
     * @param queryInformation Object holding all relevant attributes for the query.
     * @see SqlQueryInformation
     */
    private void setAttributesToPreparedStatement(SqlQueryInformation<UUID> queryInformation) {
        try {
            int[] columnIndices = queryInformation.getAttributeSqlColumnIndices();
            Object[] attributes = queryInformation.getRecordAttributes();
            if(columnIndices !=null && attributes != null) {
                for (int ix = 0; ix < attributes.length; ix++) {
                    this.preparedStatement.setObject(columnIndices[ix], attributes[ix]);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error setting statement placeholder values");
            System.out.println(e);
        }
    }


    /**
     * Execute a statement that will make a change to a table, but does not return anything.
     * @param queryInformation Object holding all relevant attributes for the query.
     * @see SqlQueryInformation
     */
    public void executeUpdateStatement(SqlQueryInformation<UUID> queryInformation) {
        prepareSqlStatement(queryInformation);
        setAttributesToPreparedStatement(queryInformation);
        try {
            this.preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error executing" + queryInformation.getStatementType() + "statement");
            System.out.println(e);
        }finally {
          //  try { if (this.resultSet != null) this.resultSet.close(); } catch (Exception e) {};
         //   try { if (this.preparedStatement != null) this.preparedStatement.close(); } catch (Exception e) {};
         //   try { if (this.jdbcConnection.checkConnection()) this.jdbcConnection.closeConnection(); } catch (Exception e) {};
        }
    }
    /**
     * Execute a SELECT query, and put the result into the ResultSet class member.
     * @param queryInformation  Object holding all relevant attributes for the query.
     * @see SqlQueryInformation
     */
    @Override
    public void executeQueryStatement(SqlQueryInformation<UUID> queryInformation) {
        prepareSqlStatement(queryInformation);
        setAttributesToPreparedStatement(queryInformation);
        try {
          this.resultSet = this.preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error executing" + queryInformation.getStatementType() + "statement");
            System.out.println(e);
        } finally {
          //  try { if (this.resultSet != null) this.resultSet.close(); } catch (SQLException e) {};
         //   try { if (this.preparedStatement != null) this.preparedStatement.close(); } catch (SQLException e) {};
          //  try { if (this.jdbcConnection.checkConnection()) this.jdbcConnection.closeConnection(); } catch (SQLException e) {};
        }
    }

    /**
     * Use this method to get the ResultSet class member.
     * resultSet is set during a query previously executed.
     * @return ResultSet
     */
    public ResultSet getResultSet(){
        return this.resultSet;
    }
    public void setJdbcConnectionObject(JdbcConnection jdbcConnection){
        this.jdbcConnection = jdbcConnection;
    }
}
