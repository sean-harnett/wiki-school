package com.wikischool.wikischool.main.ConnectionObjects.Properties;

import com.wikischool.wikischool.main.utilities.PropertiesAttributeIndex;


/**
 * Abstract class used to work with connection property types.
 * Apart of a strategy pattern implementation,
 * to allow the loose coupling of reading properties for database connection.
 * @see StandardConnectionProperties
 * @see PropertiesFromFile
 * @see Connection_Properties
 * @author sean-harnett
 */
public abstract class DatabaseProperties {

    protected StandardConnectionProperties connectionProperties; //interface type

    /**
     * Retrieve a property, from the class member.
     *
     * @param targetProperty Property to get.
     * @return String of the desired property.
     * @see PropertiesAttributeIndex
     */
    public String getProperty(PropertiesAttributeIndex targetProperty) {
        return this.connectionProperties.getProperty(targetProperty);
    }

    /**
     * Load and read properties into the
     */
    public void loadAndReadProperties() {
        this.connectionProperties.loadAndReadProperties();
    }
}
