package com.wikischool.wikischool;


/**
 * This abstract class is used to store constants that can be used in Application tests.
 *
 * @author sean-harnett
 */
public abstract class WikiTestConstants {


    //SQL:
    static final public String SQL_ALL = "*";
    static final public String UPDATE_WHERE = "WHERE";
    static final public String UPDATE_PLACEHOLDER = "= ?";
    static final public String STUDENT_TABLE = "student";
    static final public String STUDENT_TABLE_QUERY = "SELECT FROM";
    static final public String STUDENT_TABLE_UPDATE = "UPDATE SET";

    //String Formatter Test:
    static final public String STRING_FORMATTING_STUDENT_RESULT_QUERY = "SELECT * FROM student WHERE first_name = ?";
    static final public String STRING_FORMATTING_STUDENT_RESULT_INSERT_QUERY = "INSERT INTO student (id,first_name,last_name) VALUES(?, ?, ?)";


    //Properties File:
    static final public String PROPERTY_USER_NAME = "postgres";
    static final public String PROPERTY_PASSWORD = "--password--";
    static final public String PROPERTY_URL = "jdbc:postgresql://localhost:5432/test";

    //Note:
    static final public String TEST_NOTE_ID = "e7291d47-53df-43cd-a629-785365d9912a";

    //Student:
    static final public String ID_FIELD = "id";
    static final public String STUDENT_COLUMN_FIRST_NAME = "first_name";
    static final public String TEST_STUDENT_ID = "45013d08-f486-46fc-b633-89b4b069849d";

}
