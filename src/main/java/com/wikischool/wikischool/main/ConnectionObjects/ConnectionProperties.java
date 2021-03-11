package com.wikischool.wikischool.main.ConnectionObjects;

import com.wikischool.wikischool.main.utilities.PropertiesAttributeIndex;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class ConnectionProperties {
    private Properties props;
    private String connectionPropertiesFileLocation;
    private FileReader fileReader;
    private Map<PropertiesAttributeIndex, String> propertiesMap;

    public ConnectionProperties(String connectionPropertiesFileLocation) {
        this.props = new Properties();
        this.connectionPropertiesFileLocation = connectionPropertiesFileLocation;

    }
    public void loadFileReader(){
        try{
            if(fileReader == null){
                fileReader = new FileReader(connectionPropertiesFileLocation);
            }
        }catch(FileNotFoundException e){System.out.println(e.getStackTrace());}
    }
    public void loadPropertiesFile(){
        try{
            props.load(fileReader);
        }catch (IOException e){System.out.println(e.getStackTrace());}
    }
    public void setPropertiesFromLoadedFile(){
        propertiesMap.put(PropertiesAttributeIndex.USERNAME, props.getProperty(PropertiesAttributeIndex.USERNAME.getProperty()));
        propertiesMap.put(PropertiesAttributeIndex.PASSWORD, props.getProperty(PropertiesAttributeIndex.PASSWORD.getProperty()));
        propertiesMap.put(PropertiesAttributeIndex.CONNECTION_URL, props.getProperty(PropertiesAttributeIndex.CONNECTION_URL.getProperty()));
    }

}
