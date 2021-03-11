package com.wikischool.wikischool.main.ConnectionObjects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection implements PostgresqlConnection {

    ConnectionProperties properties;
    private Connection connection;
    private String userName;
    private String password;
    private String dataBase;

    public JdbcConnection() {
        this.properties = new ConnectionProperties("src/main/java/com/wikischool/wikischool/databaseConnectionPropertiesFile.properties");
    }

    public void getConnection() {

        String connectionUrl = "jdbc:postgresql"; //temp placeholder, while I work on ConnectionProperties object/file

        try (Connection connection1 = DriverManager.getConnection(connectionUrl, userName, password)) {

        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
        }
    }

}
