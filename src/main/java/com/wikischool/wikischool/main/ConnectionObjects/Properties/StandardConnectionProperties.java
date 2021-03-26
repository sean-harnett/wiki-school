package com.wikischool.wikischool.main.ConnectionObjects.Properties;

import com.wikischool.wikischool.main.utilities.EnumIndices.PropertiesAttributeIndex;

/**
 * Interface guaranteeing the ability to obtain properties from somewhere, and get them through a propertiesIndex Enum.
 * @see PropertiesFromFile for example implementation
 * @see PropertiesAttributeIndex
 */
public interface StandardConnectionProperties {
    /**
     * Obtain a property as a string through a properties ENUM.
     * @param targetProperty property to retrieve the value of.
     * @return String of the property.
     */
     String getProperty(PropertiesAttributeIndex targetProperty);

    /**
     * Obtain properties from somewhere and read them into memory.
     *
     */
     void loadAndReadProperties();

}
