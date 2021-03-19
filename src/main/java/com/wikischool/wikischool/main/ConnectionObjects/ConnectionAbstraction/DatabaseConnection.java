package com.wikischool.wikischool.main.ConnectionObjects.ConnectionAbstraction;

/**
 * Class that is instantiated to work with database.
 * @author sean-harnett
 */
public class DatabaseConnection extends DatabaseConnector {
    /**
     * Main constructor
     * @param connector type of JdbcConnector to use.
     */
    public DatabaseConnection(JdbcConnector connector){
        this.jdbcConnector = connector;
        setJdbcConnector(connector);
    }
}
