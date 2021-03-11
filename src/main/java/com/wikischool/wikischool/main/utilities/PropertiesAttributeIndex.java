package com.wikischool.wikischool.main.utilities;

public enum PropertiesAttributeIndex {
    USERNAME("username"),
    PASSWORD("password"),
    CONNECTION_URL("connection_url");

    private final String propertyValue;
    PropertiesAttributeIndex(String prop) {
        this.propertyValue = prop;
    }
    public String getProperty(){
        return this.propertyValue;
    }
}
