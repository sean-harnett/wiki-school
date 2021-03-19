package com.wikischool.wikischool.main.ConnectionObjects.Properties;

import com.wikischool.wikischool.main.utilities.PropertiesAttributeIndex;

/**
 * Interface guaranteeing the ability to obtain properties from somewhere, and get them through a propertiesIndex Enum.
 * @see PropertiesFromFile for example implementation
 * @see PropertiesAttributeIndex
 */
public interface StandardConnectionProperties {
    /**
     * Obtain a property as a string through a properties ENUM.
     * @param targetProperty
     * @return String of the property.
     */
    public String getProperty(PropertiesAttributeIndex targetProperty);

    /**
     * Obtain properties from somewhere and read them into memory.
     *
     */
    public void loadAndReadProperties();
}
