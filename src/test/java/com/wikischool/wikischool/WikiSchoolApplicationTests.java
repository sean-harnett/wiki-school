package com.wikischool.wikischool;
//Project file imports:

import com.wikischool.wikischool.main.ConnectionObjects.JdbcConnection;
import com.wikischool.wikischool.main.Queries.SqlQueryExecutor;
import com.wikischool.wikischool.main.Queries.SqlQueryInformation;
import com.wikischool.wikischool.main.utilities.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

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
        assertThat(jdbcTestObject.getPassword()).isEqualTo("--password--"); //Test that password is stored correctly
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
    //  @Test
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
    public void TestStringFormatter() {
        SqlQueryInformation<UUID> queryInformation = new SqlQueryInformation();
        StringFormatter stringFormatter = new StringFormatter();

        queryInformation.setSqlStatement(WikiTestConstants.TEST_STUDENT_TABLE_QUERY); // Does not contain table name.

        String query = WikiTestConstants.TEST_STUDENT_TABLE_QUERY;

        //Create two formatt attributes to insert into the query:

        int insertIndex = (query.indexOf("SELECT") + "SELECT".length()); // find where to insert the first one:
        FormatterNode formatAttributeTestNode_1 = new FormatterNode(insertIndex, WikiTestConstants.TEST_SQL_ALL);

        insertIndex = (query.indexOf("FROM") + "FROM".length()); // find where to insert the second one:
        FormatterNode formatAttributeTestNode_2 = new FormatterNode(insertIndex, WikiTestConstants.TEST_STUDENT_TABLE);

        FormatterNode[] formatterNodes = {formatAttributeTestNode_1, formatAttributeTestNode_2};

        queryInformation.setFormattedSqlStatement(stringFormatter.constructNewString(queryInformation.getSqlStatement(), formatterNodes)); // format query

        assertThat(queryInformation.getFormattedSqlStatement()).isEqualTo(WikiTestConstants.TEST_STRING_FORMATTING_STUDENT_RESULT_QUERY);
    }

    /**
     * Test execution of sql query that does not update a table in the databse.
     */
    @Test
    public void TestSelectStatement() {
        SqlQueryInformation<UUID> queryInformation = new SqlQueryInformation();
        StringFormatter stringFormatter = new StringFormatter();

        queryInformation.setSqlStatement(WikiTestConstants.TEST_STUDENT_TABLE_QUERY); // Does not contain table name.

        String query = WikiTestConstants.TEST_STUDENT_TABLE_QUERY;
        FormatterNode Node = new FormatterNode((query.indexOf("SELECT") + "SELECT".length()), WikiTestConstants.TEST_SQL_ALL);
        FormatterNode Node2 = new FormatterNode((query.indexOf("FROM") + "FROM".length()), WikiTestConstants.TEST_STUDENT_TABLE);
        FormatterNode[] formatterNodes = {Node, Node2};

        queryInformation.setFormattedSqlStatement(stringFormatter.constructNewString(queryInformation.getSqlStatement(), formatterNodes));

        this.jdbcTestObject.setPropertiesFile(WikiTestConstants.TEST_DATABASE_CONNECTION_PROPERTIES_FILE_LOCATION);
        this.jdbcTestObject.setPropertiesFromFile();
        this.jdbcTestObject.establishConnection();

        this.queryExecutor.setJdbcConnectionObject(this.jdbcTestObject);
        this.queryExecutor.executeQueryStatement(queryInformation);

        ResultSet rs = queryExecutor.getResultSet();

        try {
            rs.next();
            UUID testIndexPK = rs.getObject(1, UUID.class);
            System.out.println(testIndexPK);
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

}
