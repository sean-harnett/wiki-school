package com.wikischool.wikischool;

import com.wikischool.wikischool.main.ConnectionObjects.ConnectionAbstraction.DatabaseConnection;
import com.wikischool.wikischool.main.ConnectionObjects.ConnectionAbstraction.JdbcConnector;
import com.wikischool.wikischool.main.ConnectionObjects.Properties.PropertiesFromFile;
import com.wikischool.wikischool.main.Models.People.Student;
import com.wikischool.wikischool.main.Queries.SqlQueryExecutor;
import com.wikischool.wikischool.main.Services.StudentService.StudentService;
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
 * @author sean-harnett
 */
class WikiSchoolApplicationTests {


    private DatabaseConnection connection;

    @Autowired
    private SqlQueryExecutor queryExecutor;


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


    /**
     * Test if LRUCache 'put' method implementation removes the least recently used element
     */
    @Test
    public void TestLRUCache() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(SizeConstants.DEFAULT_CACHE_LENGTH);
        int testArrayLength = 10;
        int ix;
        int[] arr = new int[testArrayLength];
        for (ix = 0; ix < testArrayLength; ix++) { //Initialize test values
            arr[ix] = ix;
        }
        System.out.println("<< Start fill cache to capacity>>");
        LRUNode<Integer, Integer> list_reference;
        for (ix = 0; ix < SizeConstants.DEFAULT_CACHE_LENGTH; ix++) { // Fill the cache
            cache.put(arr[ix], arr[ix]);
            list_reference = cache.testGetCacheList();
            System.out.println("----------------");
            while (!cache.isNodeTail(list_reference)) {
                System.out.print("List Node value:");
                System.out.println(list_reference.getValue());
                System.out.println(" ");
                list_reference = list_reference.next;
            }
            System.out.println("----------------");
        }
        System.out.println("<< End fill cache to capacity>>");
        System.out.println();
        //Adding another element should remove the entry value '0' from the Cache.
        System.out.println("<< Start push cache beyond capacity>>");
        cache.put(arr[ix], arr[ix]);
        list_reference = cache.testGetCacheList();
        System.out.println("----------------");
        System.out.println();
        while (!cache.isNodeTail(list_reference)) {
            System.out.print("List Node value:");
            System.out.println(list_reference.getValue());
            System.out.println(" ");
            list_reference = list_reference.next;
        }
        System.out.println("----------------");
        System.out.println("<< End push cache beyond capacity>>");

    }


    @Test
    public void TestStudentInsert(@Autowired StudentService studentService) {

        Student student = studentService.createStudent("Test", "Name");
        try {
            studentService.insertStudentIntoDatabase(student);
        } catch (SQLException e) {
            System.out.println(e);
        }

    }


}
