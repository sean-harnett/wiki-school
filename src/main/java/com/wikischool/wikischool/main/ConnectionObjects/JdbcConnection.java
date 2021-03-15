package com.wikischool.wikischool.main.ConnectionObjects;

import com.wikischool.wikischool.main.utilities.PropertiesAttributeIndex;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class to create a jdbc connection dependant on postgresql.
 *
 * @author sean-harnett
 */
@Component
public class JdbcConnection implements PostgresqlConnection {

    ConnectionProperties properties;
    private Connection connection;
    private String userName;
    private String password;
    private String connectionUrl;

    /**
     * Constructor -> used to immediately initialize the properties file.
     */
    public JdbcConnection(String connectionPropertiesFileLocation) {
        this.properties = new ConnectionProperties("src/main/java/com/wikischool/wikischool/databaseConnectionPropertiesFile.properties");
    }
    public JdbcConnection(){

    }

    public void setPropertiesFile(String connectionPropertiesFileLocation){
        this.properties = new ConnectionProperties("src/main/java/com/wikischool/wikischool/databaseConnectionPropertiesFile.properties");
    }
    public Connection getConnectionObj(){
        return this.connection;
    }
    /**
     * Use the ConnectionProperties object to load and read the properties from a file,
     * and then set them to corresponding field in this object.
     */
    public void setPropertiesFromFile() {

        this.properties.loadAndReadProperties();

        this.userName = this.properties.getProperty(PropertiesAttributeIndex.USERNAME);
        this.password = this.properties.getProperty(PropertiesAttributeIndex.PASSWORD);
        this.connectionUrl = this.properties.getProperty(PropertiesAttributeIndex.CONNECTION_URL);

    }

    /**
     * Establish connection to the database.
     *
     */
    public void establishConnection() {

        try (Connection connection1 = DriverManager.getConnection(connectionUrl, userName, password)) {

            this.connection = connection1;
            System.out.println("Connection Established");

        }
        catch (SQLException e) {
            System.out.println("Connection Failed");

            System.out.println("Connection Fields Used:");
            System.out.println(this.userName);
            System.out.println(this.password);
            System.out.println(this.connectionUrl);

            System.out.println("Error Message:");
            System.out.println(e);
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }


    public String getConnectionUrl() {
        return connectionUrl;
    }

    /**
     * Check whether a connection to a database has been made
     * @return boolean conneciton = true, !connection = false
     */
    public boolean checkConnection(){
      return this.connection != null;
    }
}
