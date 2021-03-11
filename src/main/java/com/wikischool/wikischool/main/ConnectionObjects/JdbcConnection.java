package com.wikischool.wikischool.main.ConnectionObjects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConnection implements PostgresConnection {

        private Connection connection;
       // private DriverManager driverManager;
        private String userName;
        private String password;
        private String dataBase;



        public void getConnection(){

                String connectionUrl = "jdbc:postgresql";
                Properties props = new Properties();



                try(Connection connection1 = DriverManager.getConnection(connectionUrl, userName, password)){

                }catch(SQLException e){
                        System.out.println(e.getStackTrace());
                }
        }

}
