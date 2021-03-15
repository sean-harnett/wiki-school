package com.wikischool.wikischool;
//Project file imports:

import com.wikischool.wikischool.main.ConnectionObjects.JdbcConnection;
import com.wikischool.wikischool.main.Models.People.Student;
import com.wikischool.wikischool.main.Queries.SqlQueryExecutor;
import com.wikischool.wikischool.main.utilities.LRUCache;
import com.wikischool.wikischool.main.utilities.LRUNode;
import com.wikischool.wikischool.main.utilities.SizeConstants;
import com.wikischool.wikischool.main.utilities.StudentAttributeIndex;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
/**
 * This class is used to perform tests on the application.
 * @author sean-harnett
 */
class WikiSchoolApplicationTests {


    @Autowired
    private JdbcConnection jdbcTestObject;

    @Autowired
    private SqlQueryExecutor queryExecutor;

    /**
     * Test if the properties are read into the connection object correctly
     */
    @Test
    public void TestConnectionProperties() {

        jdbcTestObject.setPropertiesFile(WikiTestConstants.TEST_DATABASE_CONNECTION_PROPERTIES_FILE_LOCATION);
        jdbcTestObject.setPropertiesFromFile();
        assertThat(jdbcTestObject.getPassword()).isEqualTo("-password-"); //Test that password is stored correctly
        assertThat(jdbcTestObject.getUserName()).isEqualTo("postgres");
        assertThat(jdbcTestObject.getConnectionUrl()).isEqualTo("jdbc:postgresql://localhost:5432/test");
    }

    /**
     * Test if the connection object works, and a connection can be established
     */
    @Test
    public void TestIfConnectionEstablished() {
        jdbcTestObject.setPropertiesFile(WikiTestConstants.TEST_DATABASE_CONNECTION_PROPERTIES_FILE_LOCATION);
        jdbcTestObject.setPropertiesFromFile();
        jdbcTestObject.establishConnection();
        assertThat(jdbcTestObject.checkConnection()).isEqualTo(true);
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

   
}
