package com.wikischool.wikischool.main.ConnectionObjects.ConnectionAbstraction;

import com.wikischool.wikischool.main.ConnectionObjects.Properties.StandardConnectionProperties;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DatabaseConnector {

    protected JdbcConnector jdbcConnector;

    public void establishConnection()throws SQLException{
        this.jdbcConnector.establishConnection();
    }
    public void closeConnection()throws SQLException{
        this.jdbcConnector.closeConnection();
    }
    public boolean checkConnection(){
        return this.jdbcConnector.checkConnection();
    }

    public void readProperties(){
        this.jdbcConnector.readProperties();
    }

    public void setConnectionPropertyValues(StandardConnectionProperties properties){
        this.jdbcConnector.setConnectionProperties(properties);
    }
    public void setJdbcConnector(JdbcConnector connector){
        this.jdbcConnector = connector;
    }
    public String[] getCurrentPropertyValues(){
        return this.jdbcConnector.getSetProperties();
    }
    public Connection getConnectionObject(){
        return this.jdbcConnector.getConnectionObject();
    }
}
