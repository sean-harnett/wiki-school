package com.wikischool.wikischool.main.ConnectionObjects.ConnectionAbstraction;

import com.wikischool.wikischool.main.ConnectionObjects.Properties.Connection_Properties;
import com.wikischool.wikischool.main.ConnectionObjects.Properties.StandardConnectionProperties;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface for abstracting JdbcConnection functionality.
 * @author sean-harnett
 */
public interface JdbcConnector {

    public void establishConnection()throws SQLException;

    public void closeConnection()throws SQLException;

    /**
     * Read the properties, from the properties object.
     * @see StandardConnectionProperties
     */
    public void readProperties();

    /**
     * Check if connection to database exists.
     * @return boolean, if connection = true, !connection = false.
     */
    public boolean checkConnection();

    /**
     * Set the properties object implementation to use in this connector.
     * @see PostgresJdbcConnector for example implementation
     * @see StandardConnectionProperties
     * @param properties the implementation to use.
     */
    public void setConnectionProperties(StandardConnectionProperties properties);


    public String[] getSetProperties();

    public Connection getConnectionObject();
}
