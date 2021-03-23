package com.wikischool.wikischool;
//Project file imports:

import com.wikischool.wikischool.main.ConnectionObjects.ConnectionAbstraction.DatabaseConnection;
import com.wikischool.wikischool.main.ConnectionObjects.ConnectionAbstraction.PostgresJdbcConnector;
import com.wikischool.wikischool.main.ConnectionObjects.Properties.PropertiesFromFile;
import com.wikischool.wikischool.main.Queries.SqlQueryExecutor;
import com.wikischool.wikischool.main.Queries.SqlQueryInformation;
import com.wikischool.wikischool.main.utilities.DataStructures.LRUCache.LRUCache;
import com.wikischool.wikischool.main.utilities.DataStructures.LRUCache.LRUNode;
import com.wikischool.wikischool.main.utilities.Constants.SizeConstants;
import com.wikischool.wikischool.main.utilities.StringFormatting.StringFormatter;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
/**
 * This class is used to perform tests on the application.
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
    public void TestProperties() {

        this.connection = new DatabaseConnection(new PostgresJdbcConnector());
        this.connection.setConnectionPropertyValues(new PropertiesFromFile(WikiTestConstants.DATABASE_CONNECTION_PROPERTIES_FILE_LOCATION));

        this.connection.readProperties();
        String[] currentProperties = this.connection.getCurrentPropertyValues();
        String[] expectedProperties = {WikiTestConstants.PROPERTY_USER_NAME, WikiTestConstants.PROPERTY_PASSWORD, WikiTestConstants.PROPERTY_URL};
        assertThat(Arrays.equals(currentProperties, expectedProperties)).isEqualTo(true);
    }

    /**
     * Check to see if connection can be established.
     */
    @Test
    public void TestConnection() {

        this.connection = new DatabaseConnection(new PostgresJdbcConnector());
        this.connection.setConnectionPropertyValues(new PropertiesFromFile(WikiTestConstants.DATABASE_CONNECTION_PROPERTIES_FILE_LOCATION));

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
    public void TestStringFormatter() {
        SqlQueryInformation<UUID> queryInformation = new SqlQueryInformation();

        StringFormatter stringFormatter = new StringFormatter();

        queryInformation.setSqlStatement(WikiTestConstants.STUDENT_TABLE_QUERY); // Does not contain table name.

        String query = WikiTestConstants.STUDENT_TABLE_QUERY;

        //Create two format attributes to insert into the query:

        int insertIndex = (query.indexOf("SELECT") + "SELECT".length()); // find where to insert the first one:

        stringFormatter.insertNewFormatterNode(WikiTestConstants.SQL_ALL, insertIndex);

        insertIndex = (query.indexOf("FROM") + "FROM".length()); // find where to insert the second one:

        stringFormatter.insertNewFormatterNode(WikiTestConstants.STUDENT_TABLE, insertIndex);

        stringFormatter.setSource(queryInformation.getSqlStatement());

        queryInformation.setFormattedSqlStatement(stringFormatter.constructNewString());

        assertThat(queryInformation.getFormattedSqlStatement()).isEqualTo(WikiTestConstants.STRING_FORMATTING_STUDENT_RESULT_QUERY);


    }

    /**
     * Test execution of sql query that does not update a table in the databse.
     */

    @Test
    public void TestSelectStatement() {

        SqlQueryInformation<UUID> queryInformation = new SqlQueryInformation();

        StringFormatter stringFormatter = new StringFormatter();

        queryInformation.setSqlStatement(WikiTestConstants.STUDENT_TABLE_QUERY); // Does not contain table name.

        String query = WikiTestConstants.STUDENT_TABLE_QUERY;

        //Create two format attributes to insert into the query:

        int insertIndex = (query.indexOf("SELECT") + "SELECT".length()); // find where to insert the first one:

        stringFormatter.insertNewFormatterNode(WikiTestConstants.SQL_ALL, insertIndex);

        insertIndex = (query.indexOf("FROM") + "FROM".length()); // find where to insert the second one:

        stringFormatter.insertNewFormatterNode(WikiTestConstants.STUDENT_TABLE, insertIndex);

        stringFormatter.setSource(queryInformation.getSqlStatement());

        queryInformation.setFormattedSqlStatement(stringFormatter.constructNewString());


        DatabaseConnection conn = new DatabaseConnection(new PostgresJdbcConnector());
        conn.setConnectionPropertyValues(new PropertiesFromFile(WikiTestConstants.DATABASE_CONNECTION_PROPERTIES_FILE_LOCATION));

        conn.readProperties();

        this.queryExecutor.setDatabaseConnection(conn);
        try {
            this.queryExecutor.executeQueryStatement(queryInformation);

            ResultSet rs = queryExecutor.getResultSet();

            rs.next();

            UUID testIndexPK = rs.getObject(1, UUID.class);

            System.out.println(testIndexPK);

        } catch (SQLException e) {
            System.out.println(e);
        }

        //Close all SQL objects:
        try {
            this.queryExecutor.closeAll();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Test execution of sql query that does update a table in the database.
     */
    @Test
    public void TestUpdateQuery(){
        SqlQueryInformation<UUID> queryInformation = new SqlQueryInformation();

        StringFormatter stringFormatter = new StringFormatter();


        UUID id = UUID.fromString(WikiTestConstants.TEST_STUDENT_ID);
        Object[] attributes = {"Xuan", id};
        int[] columnIndices = {1,2};

        queryInformation.setRecordAttributes(attributes);
        queryInformation.setAttributeSqlColumnIndices(columnIndices);

        queryInformation.setSqlStatement(WikiTestConstants.STUDENT_TABLE_UPDATE + " " + WikiTestConstants.UPDATE_WHERE); // Does not contain table name.

        String query = WikiTestConstants.STUDENT_TABLE_UPDATE + " " + WikiTestConstants.UPDATE_WHERE;

        //Create two format attributes to insert into the query:

        int insertIndex = (query.indexOf("UPDATE") + "UPDATE".length()); // find where to insert the first one:

        stringFormatter.insertNewFormatterNode(WikiTestConstants.STUDENT_TABLE, insertIndex);

        insertIndex = (query.indexOf("SET") + "SET".length()); // find where to insert the second one:

        String newInsert = WikiTestConstants.STUDENT_COLUMN_FIRST_NAME + WikiTestConstants.UPDATE_PLACEHOLDER;

        stringFormatter.insertNewFormatterNode(newInsert, insertIndex);

        insertIndex = (query.indexOf("WHERE") + "WHERE".length());

        newInsert = WikiTestConstants.ID_FIELD + " " + WikiTestConstants.UPDATE_PLACEHOLDER;

        stringFormatter.insertNewFormatterNode(newInsert, insertIndex);

        stringFormatter.setSource(queryInformation.getSqlStatement());

        queryInformation.setFormattedSqlStatement(stringFormatter.constructNewString());
        System.out.println(queryInformation.getFormattedSqlStatement());


        DatabaseConnection conn = new DatabaseConnection(new PostgresJdbcConnector());
        conn.setConnectionPropertyValues(new PropertiesFromFile(WikiTestConstants.DATABASE_CONNECTION_PROPERTIES_FILE_LOCATION));

        conn.readProperties();

        this.queryExecutor.setDatabaseConnection(conn);
        try{
            this.queryExecutor.executeUpdateStatement(queryInformation);
        }catch(SQLException e){
            System.out.println(e);
        }

    }


}
