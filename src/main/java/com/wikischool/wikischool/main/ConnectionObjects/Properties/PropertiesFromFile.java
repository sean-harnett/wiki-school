package com.wikischool.wikischool.main.ConnectionObjects.Properties;

import com.wikischool.wikischool.main.utilities.EnumIndices.PropertiesAttributeIndex;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 *Class to obtain properties for database connection from database.
 * @author sean-harnett
 */

public class PropertiesFromFile implements StandardConnectionProperties {

    private Properties properties;
    private FileReader fileReader;

    private String connectionPropertiesFileLocation;

    /**
     * Main Constructor
     * @param connectionPropertiesFileLocation the location of the properties file.
     */
    public PropertiesFromFile(String connectionPropertiesFileLocation){
        this.properties = new Properties();
        this.connectionPropertiesFileLocation = connectionPropertiesFileLocation;

    }
    /**
     * Get a specific property.
     * @param targetProperty -> ENUM, whose value corresponds to the name of a property from the properties file i.e. "database.username"
     * @return String of the property.
     */
    @Override
    public String getProperty(PropertiesAttributeIndex targetProperty) {

        return this.properties.getProperty(targetProperty.getProperty());
    }

    /**
     * Load the file into file reader, and load the properties into properties object.
     */
    @Override
    public void loadAndReadProperties(){
        try {
            this.loadFileReader();
            this.loadPropertiesFile();
        }catch(IOException e){
            System.out.println(e);
        }
    }

    /**
     * Create a fileReader object, replacing any existing one, and load in the new target properties file.
     */
    private void loadFileReader() throws IOException{
        try {
            fileReader = null;
            fileReader = new FileReader(connectionPropertiesFileLocation);
        } catch (FileNotFoundException e) {
            throw new IOException(e);
        }
    }
    /**
     * Load a properties file into the Properties object:
     */
    public void loadPropertiesFile() throws IOException {
        try {
            properties.load(fileReader);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}
