package com.wikischool.wikischool.main.utilities.EnumIndices;

/**
 * This Enum corresponds to names of properties from a properties file.
 * @author sean-harnett
 */
public enum PropertiesAttributeIndex{
    USERNAME("database.username"),
    PASSWORD("database.password"),
    CONNECTION_URL("database.connection_url");

    private final String propertyValue;
    PropertiesAttributeIndex(String prop) {
        this.propertyValue = prop;
    }
    public String getProperty(){
        return this.propertyValue;
    }
}
