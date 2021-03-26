package com.wikischool.wikischool.main.Services.ServiceAbstraction;

/**
 * This interface is used to implement a model containing information for a table in the database.
 * @author sean-harnett
 */
public interface TableAttributes {

     int getColumnCount();
     String[] getColumns();
     String getTableName();

}
