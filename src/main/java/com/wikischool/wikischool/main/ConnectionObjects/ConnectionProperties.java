package com.wikischool.wikischool.main.ConnectionObjects;

import com.wikischool.wikischool.main.utilities.PropertiesAttributeIndex;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConnectionProperties {
    private Properties props;
    private String connectionPropertiesFileLocation;
    private FileReader fileReader;


    public ConnectionProperties(String connectionPropertiesFileLocation) {
        this.props = new Properties();
        this.connectionPropertiesFileLocation = connectionPropertiesFileLocation;

    }

    public void loadFileReader() {
        try {
            if (fileReader == null) {
                fileReader = new FileReader(connectionPropertiesFileLocation);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public void loadPropertiesFile() {
        try {
            props.load(fileReader);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }
}
