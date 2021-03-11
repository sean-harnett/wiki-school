package com.wikischool.wikischool.main.ConnectionObjects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection implements PostgresqlConnection {

        private Connection connection;
       // private DriverManager driverManager;
        private String userName;
        private String password;
        private String dataBase;

        public void getConnection(){

                String connectionUrl = "jdbc:postgresql"; //temp placeholder, while I work on ConnectionProperties object/file

                try(Connection connection1 = DriverManager.getConnection(connectionUrl, userName, password)){

                }catch(SQLException e){
                        System.out.println(e.getStackTrace());
                }
        }

}
