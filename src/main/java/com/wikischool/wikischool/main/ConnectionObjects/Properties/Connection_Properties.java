package com.wikischool.wikischool.main.ConnectionObjects.Properties;


/**
 * Actual class instantiated to work with properties types.
 *   @see StandardConnectionProperties
 *   @see PropertiesFromFile
 *   @see DatabaseProperties
 * @author sean-harnett
 */
public class Connection_Properties extends DatabaseProperties {
    /**
     * Main constructor
     * @param connectionProperties The implementation of StandardConnectionProperties to use.
     */
    public Connection_Properties(StandardConnectionProperties connectionProperties){
        this.connectionProperties = connectionProperties;

    }


}
