package com.wikischool.wikischool;

import com.wikischool.wikischool.main.ConnectionObjects.ConnectionAbstraction.DatabaseConnection;
import com.wikischool.wikischool.main.ConnectionObjects.ConnectionAbstraction.JdbcConnector;
import com.wikischool.wikischool.main.ConnectionObjects.Properties.PropertiesFromFile;
import com.wikischool.wikischool.main.utilities.Constants.SizeConstants;
import com.wikischool.wikischool.main.utilities.DataStructures.LRUCache.LRUCache;
import com.wikischool.wikischool.main.utilities.DataStructures.LRUCache.LRUNode;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
/**
 * This class is used to perform tests on the application.
 * TODO: write new tests to test queries, that execute without utilising a service object.
 * TODO: extrapolate tests into specific test classes and files.
 * @author sean-harnett
 */
class WikiSchoolApplicationTests {


    private DatabaseConnection connection;

    /**
     * Test if the properties are read into the connection object correctly
     */

    @Test
    public void TestProperties(@Autowired JdbcConnector jdbcConn) {

        this.connection = new DatabaseConnection(jdbcConn);

        this.connection.setConnectionPropertyValues(new PropertiesFromFile());

        this.connection.readProperties();

        String[] currentProperties = this.connection.getCurrentPropertyValues();
        String[] expectedProperties = {WikiTestConstants.PROPERTY_USER_NAME, WikiTestConstants.PROPERTY_PASSWORD, WikiTestConstants.PROPERTY_URL};
        assertThat(Arrays.equals(currentProperties, expectedProperties)).isEqualTo(true);

    }

    /**
     * Check to see if connection can be established.
     */
    @Test
    public void TestConnection(@Autowired JdbcConnector jdbcConnector) {


        this.connection = new DatabaseConnection(jdbcConnector);

        this.connection.setConnectionPropertyValues(new PropertiesFromFile());

        this.connection.readProperties();

        try {
            this.connection.establishConnection();
        } catch (SQLException e) {
            System.out.println(e);
        }
        assertThat(this.connection.checkConnection()).isEqualTo(true);

    }





}
