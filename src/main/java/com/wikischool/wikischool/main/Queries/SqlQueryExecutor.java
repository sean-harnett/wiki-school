package com.wikischool.wikischool.main.Queries;

import com.wikischool.wikischool.main.ConnectionObjects.JdbcConnection;
import com.wikischool.wikischool.main.Models.Interfaces.StandardQueries;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Class to prepare statements and execute sql queries into a connected database.
 * @author sean-harnett
 */

@Component
public class SqlQueryExecutor implements StandardQueries<UUID> {

    private PreparedStatement preparedStatement;
    private JdbcConnection jdbcConnection;

    public SqlQueryExecutor() {
    }

    /**
     * Method to prepare the SQL statement. Assumes all attributes other than primary key are Strings
     * ** Requires database connection.**
     * @see SqlQueryInformation
     * @param queryInformation Object holding all relevant attributes for the query.
     */
    public void prepareStatement( SqlQueryInformation queryInformation) {
        Connection conn = jdbcConnection.getConnectionObj();
        try {
            this.preparedStatement = conn.prepareStatement(queryInformation.getSqlStatement());
            int iy = 1;
            this.preparedStatement.setString(iy, queryInformation.getTableName());
            ++iy;
            this.preparedStatement.setObject(iy, queryInformation.getPrimaryKey());
            String[] recordAttributes =  queryInformation.getRecordAttributes();
            for(String attribute : recordAttributes){
                this.preparedStatement.setString(iy, attribute);
                iy++;
            }
        } catch (SQLException e) {
            System.out.println("Error preparing statement");
            System.out.println(e);
        }
    }

    /**
     * Method to implement an insert statement into a connected database.
     * @see SqlQueryInformation
     * @param queryInformation Object holding all relevant attributes for the query.
     */
    @Override
    public void insert(SqlQueryInformation<UUID> queryInformation) {

    }
    /**
     * Method to implement an update statement into a connected database.
     * @see SqlQueryInformation
     * @param queryInformation Object holding all relevant attributes for the query.
     */
    @Override
    public void update(SqlQueryInformation<UUID> queryInformation) {

    }
    /**
     * Method to implement a delete statement into a connected database.
     * @see SqlQueryInformation
     * @param queryInformation Object holding all relevant attributes for the query.
     */
    @Override
    public void delete(SqlQueryInformation<UUID> queryInformation) {

    }
    /**
     * Method to implement a find record query into a connected database.
     * @see SqlQueryInformation
     * @param queryInformation Object holding all relevant attributes for the query.
     */
    @Override
    public void retrieve(SqlQueryInformation<UUID> queryInformation) {

    }
}
