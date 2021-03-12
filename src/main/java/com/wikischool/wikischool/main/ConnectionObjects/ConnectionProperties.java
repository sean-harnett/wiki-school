package com.wikischool.wikischool.main.ConnectionObjects;

import com.wikischool.wikischool.main.utilities.PropertiesAttributeIndex;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * This class reads, and stores properties from a properties file.
 *
 *
 * @author sean-harnett
 */
public class ConnectionProperties {

    /**
     * @variable connectionPropertiesFileLocation -> Path to file (can be relative to project src, or a full path)
     */
    private String connectionPropertiesFileLocation;

    private Properties props;
    private FileReader fileReader;

    /**
     * Main constructor
     * @param connectionPropertiesFileLocation Path to file, relevant to project, or absolute path
     */
    public ConnectionProperties(String connectionPropertiesFileLocation) {
        this.props = new Properties();
        this.connectionPropertiesFileLocation = connectionPropertiesFileLocation;

    }

    /**
     * Get a specific property.
     * @param targetProperty -> ENUM, whose value corresponds to the name of a property from the properties file i.e. "database.username"
     * @return String of the property.
     */
    public String getProperty(PropertiesAttributeIndex targetProperty){
       return (String)this.props.get(targetProperty.getProperty());

    }

    /**
     * Load the file into file reader, and load the properties into properties object.
     */
    public void loadAndReadProperties(){
        this.loadFileReader();
        this.loadPropertiesFile();
    }

    /**
     * Create a fileReader object, and load in the new target properties file.
     */
    public void loadFileReader() {
        try {
                fileReader = new FileReader(connectionPropertiesFileLocation);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    /**
     * Load a properties file into the Properties object:
     */
    public void loadPropertiesFile() {
        try {
            props.load(fileReader);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
