package com.wikischool.wikischool.main.ConnectionObjects.ConnectionAbstraction;

import com.wikischool.wikischool.main.ConnectionObjects.Properties.StandardConnectionProperties;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface for abstracting JdbcConnection functionality.
 * @author sean-harnett
 */
public interface JdbcConnector {

     void establishConnection()throws SQLException;

     void closeConnection()throws SQLException;

    /**
     * Read the properties, from the properties object.
     * @see StandardConnectionProperties
     */
     void readProperties();

    /**
     * Check if connection to database exists.
     * @return boolean, if connection = true, !connection = false.
     */
     boolean checkConnection();

    /**
     * Set the properties object implementation to use in this connector.
     * @see PostgresJdbcConnector for example implementation
     * @see StandardConnectionProperties
     * @param properties the implementation to use.
     */
     void setConnectionProperties(StandardConnectionProperties properties);


     String[] getSetProperties();

     Connection getConnectionObject();
}
