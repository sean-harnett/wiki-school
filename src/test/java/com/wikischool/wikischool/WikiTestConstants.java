package com.wikischool.wikischool;


/**
 * This abstract class is used to store constants that can be used in Application tests.
 * @author sean-harnett
 */
public abstract class WikiTestConstants {
    /**
     * Location of the properties file to test database connectivity.
     */
    //Files:
    static final public String TEST_DATABASE_CONNECTION_PROPERTIES_FILE_LOCATION =  "src/main/java/com/wikischool/wikischool/databaseConnectionPropertiesFile.properties";

    //SQL:
    static final public String TEST_STUDENT_TABLE_QUERY = "SELECT FROM";

    static final public String TEST_STUDENT_TABLE = " student";
    static final public String TEST_SQL_ALL = " *";


    //String Formatter Test:
    static final public String TEST_STRING_FORMATTING_STUDENT_RESULT_QUERY = "SELECT * FROM student";


    //Student Model Test Attributes:
    static final public String TEST_STUDENT_ID = "108c406a-a072-4fce-a93c-61ee2e1aa5ca";

    //Properties File:
        static final public String PROPERTY_USER_NAME = "postgres";
        static final public String PROPERTY_PASSWORD = "--password--";
        static final public String PROPERTY_URL = "jdbc:postgresql://localhost:5432/test";
}
