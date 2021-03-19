package com.wikischool.wikischool.main.ConnectionObjects.ConnectionAbstraction;

import com.wikischool.wikischool.main.ConnectionObjects.Properties.StandardConnectionProperties;
import com.wikischool.wikischool.main.utilities.PropertiesAttributeIndex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * jdbc connection dependant on postgresql.
 * @author sean-harnett
 */
public class PostgresJdbcConnector implements JdbcConnector {

    StandardConnectionProperties connectionProperties;

    private Connection connection;
    private String userName;
    private String password;
    private String connectionUrl;

    /**
     * Main Constructor
     */
    public PostgresJdbcConnector() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    /**
     * Read the properties, and set them to class members.
     * @see PropertiesAttributeIndex
     */
    @Override
    public void readProperties() {
        connectionProperties.loadAndReadProperties();
        this.userName = connectionProperties.getProperty(PropertiesAttributeIndex.USERNAME);
        this.password = connectionProperties.getProperty(PropertiesAttributeIndex.PASSWORD);
        this.connectionUrl = connectionProperties.getProperty(PropertiesAttributeIndex.CONNECTION_URL);
    }
    /**
     * Establish connection to the database.
     */
    @Override
    public void establishConnection() throws SQLException {
        try {
            this.connection = DriverManager.getConnection(connectionUrl, userName, password);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Check if there is a connection, if yes, close it.
     * @throws SQLException Exception occuring when trying to close connection.
     */

    @Override
    public void closeConnection() throws SQLException {
        if (!this.checkConnection()) { //if connection already closed.
            return;
        }
        try {

            this.connection.close();

            this.connection = null;

        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
    /**
     * Check whether a connection to a database has been made
     * @return boolean conneciton = true, !connection = false
     */
    @Override
    public boolean checkConnection() {
        return this.connection != null;
    }

    public StandardConnectionProperties getConnectionProperties() {
        return connectionProperties;
    }

    /**
     * Retrieve the current set properties values used to connect to the database.
     * @return String[] of the current properties.
     */

    @Override
    public String[] getSetProperties(){
        String[] props = {userName, password, connectionUrl};
        return  props;
    }

    /**
     *
     * @return sql.Connection object
     */
    @Override
    public Connection getConnectionObject() {
        return this.connection;
    }

    /**
     * Set the type of connection properties object to use.
     * @see StandardConnectionProperties
     * @param properties implementation of StandardConnectionProperties
     */
    @Override
    public void setConnectionProperties(StandardConnectionProperties properties) {
        this.connectionProperties = properties;
    }
}
