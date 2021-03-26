package com.wikischool.wikischool.main.ConnectionObjects.ConnectionAbstraction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Class that is instantiated to work with database.
 * @author sean-harnett
 */
@Component
public class DatabaseConnection extends DatabaseConnector {
    /**
     * Main constructor
     * @param connector type of JdbcConnector to use.
     */
    @Autowired
    public DatabaseConnection(@Qualifier("PostgresJdbcConnector")JdbcConnector connector){
        this.jdbcConnector = connector;
    }
}
