package com.wikischool.wikischool.main.Services.StudentService;

import com.wikischool.wikischool.main.Models.People.Student;
import com.wikischool.wikischool.main.Queries.SqlQueryExecutor;
import com.wikischool.wikischool.main.Queries.SqlQueryInformation;
import com.wikischool.wikischool.main.Services.GeneralService;
import com.wikischool.wikischool.main.utilities.Constants.SizeConstants;
import com.wikischool.wikischool.main.utilities.Constants.StudentConstants.StudentFields;
import com.wikischool.wikischool.main.utilities.DataStructures.LRUCache.LRUCache;
import com.wikischool.wikischool.main.utilities.StringFormatting.StringFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class to work with student objects and their functionality
 * TODO: implement the rest of the student methods
 * TODO: abstract SQL syntax.
 * TODO: abstract delete method, so that it will delete any related data in other tables also. For example, delete the course notes corresponding to student with id 'x'.
 *
 * @author sean-harnett
 */

@Service
public class StudentService extends GeneralService {

    private final LRUCache<UUID, Student> studentCache = new LRUCache<>(SizeConstants.DEFAULT_CACHE_LENGTH);
    private final String delimiter = "-";
    private final int STUDENT_FIELD_COUNT = 3;

    @Autowired
    public StudentService(StringFormatter stringFormatter, SqlQueryExecutor executor) {

        super(stringFormatter, executor, "-");

        this.queryInformation = new SqlQueryInformation<>();

        this.readProperties();
    }

    /**
     * Method to instantiate a student object.
     * @param firstName Students first name
     * @param lastName Students last name
     * @return a new Student object populated with first name, last name, and an auto-generated ID field
     */
    public Student createStudent(String firstName, String lastName) {

        return new Student(newID(), firstName, lastName);

    }

    /**
     * Insert one student into the database.
     * @param student the object, containing at least mandatory fields to populate a database record of type Student.
     * @throws SQLException
     */
    public void insertStudentIntoDatabase(Student student) throws SQLException {

        String nonformattedQuery = "INSERT INTO student (-,-,-) VALUES (?,?,?)";

        UUID id = student.getId();
        String firstName = student.getFirstName();
        String lastName = student.getLastName();

        String[] formatAttributes = {"id", "first_name", "last_name"};

        this.queryInformation = this.constructStatement(nonformattedQuery, formatAttributes);

        Object[] queryValues = {id, firstName, lastName};

        int[] columnIndices = {1, 2, 3};

        queryInformation.setRecordAttributes(queryValues);
        queryInformation.setAttributeSqlColumnIndices(columnIndices);

        this.queryInformation = queryInformation;

        this.executeUpdate();

        this.studentCache.put(id, student); // add the student to cache.

    }

    /**
     * Method that will select a single student from the database based off of ID.
     * @param targetStudentId UUID type, of the target student.
     * @return Student object
     * @throws SQLException
     */
    public Student selectStudentByIdFromDatabase(UUID targetStudentId) throws SQLException {
        Student foundStudent = null;
        if (this.studentCache.checkCache(targetStudentId)) {
            foundStudent = this.studentCache.get(targetStudentId);
        } else {
            //Create the query:
            String nonformattedQuery = "SELECT - FROM student WHERE -";
            String[] formatAttributes = {"*", "id=?"};
            this.queryInformation = this.constructStatement(nonformattedQuery, formatAttributes);

            Object[] queryValues = {targetStudentId};
            int[] columnIndices = {1};

            this.queryInformation.setRecordAttributes(queryValues);
            this.queryInformation.setAttributeSqlColumnIndices(columnIndices);

            //Execute the query:

            this.executeQuery(); // throws SQLException

            ResultSet rs = this.getResultSet();

            //Create the student object from the results

            if (rs.getFetchSize() > 0) {

                UUID id = rs.getObject(1, UUID.class);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);

                foundStudent = new Student(id, firstName, lastName);

                // Add the student to cache.
                this.studentCache.put(id, foundStudent);

            }

            this.closeAllDatabaseObjects(); // throws SQLException
        }

        return foundStudent;

    }

    /**
     * Method to delete a Student from the connected database.
     *
     * @param targetStudentId the id to find, and delete it's record.
     * @throws SQLException
     */
    public boolean deleteStudentByIdFromDataBase(UUID targetStudentId) throws SQLException {

        String nonformattedQuery = "DELETE FROM student WHERE -";

        String[] formatAttributes = {"id=?"};

        this.queryInformation = this.constructStatement(nonformattedQuery, formatAttributes);

        Object[] queryValues = {targetStudentId};

        int[] columnIndices = {1};

        this.queryInformation.setRecordAttributes(queryValues);

        this.queryInformation.setAttributeSqlColumnIndices(columnIndices);

        int rowsAffected = this.executeUpdate();

        this.resetQueryAttributes(); // reset the information object for the next query

        return rowsAffected > 0; // boolean, whether any rows were affected

    }

    /**
     * Method that returns a list of all the students in the database.
     * @return List of type Student.
     * @throws SQLException
     */
    public List<Student> retrieveAllStudents() throws SQLException {

        String query = "SELECT * FROM student"; // no need to format

        this.queryInformation.setUnFormattedSqlStatement(query);

        this.queryInformation.setFormattedSqlStatement(query);
        this.resetQueryAttributes();

        this.executeQuery();

        ResultSet rs = this.getResultSet();
        List<Student> students = new ArrayList<Student>();

        while (rs.next()) {
            students.add(new Student(rs.getObject(1, UUID.class), rs.getString(2), rs.getString(3)));

        }
        this.closeAllDatabaseObjects();
        return students;

    }

    /**
     * Method used to update a single student record by Id within a database.
     * @param student Object containing the values to update.
     * @return integer- rows affected, returns -1 when error occurs.
     * @throws SQLException
     */
    public int updateStudentById(Student student) throws SQLException {


        String nonformattedQuery = "UPDATE student SET - WHERE id=?";
        int rowsAffected = -1; // -1 for error handling.
        this.resetQueryAttributes();
        this.queryInformation.setUnFormattedSqlStatement(nonformattedQuery);

        String[] formatAttributes = new String[1];
        UUID targetId = student.getId();
        String firstName = student.getFirstName();
        String lastName = student.getLastName();
        Object[] queryValues = null;
        int[] columnIndices = null;

        if (student.getFirstName() != null && student.getLastName() != null) { // Three possibilities for update:

            formatAttributes[0] = "first_name=?,last_name=?";
            queryValues = new Object[3];
            columnIndices = new int[3];
            queryValues[0] = firstName;
            queryValues[1] = lastName;
            queryValues[2] = targetId;
            columnIndices[0] = 1;
            columnIndices[1] = 2;
            columnIndices[2] = 3;

        } else if (firstName == null || firstName.length() < 1) {

            formatAttributes[0] = "last_name=?";
            queryValues = new Object[2];
            columnIndices = new int[2];
            queryValues[0] = lastName;
            queryValues[1] = targetId;
            columnIndices[0] = 1;
            columnIndices[1] = 2;
        } else if (lastName == null || lastName.length() < 1) {

            formatAttributes[0] = "first_name=?";
            queryValues = new Object[2];
            columnIndices = new int[2];
            queryValues[0] = firstName;
            queryValues[1] = targetId;
            columnIndices[0] = 1;
            columnIndices[1] = 2;
        }

        this.queryInformation = this.constructStatement(nonformattedQuery, formatAttributes);
        if(queryValues == null || columnIndices == null) { // is error return -1.
            return rowsAffected;
        }
            this.queryInformation.setRecordAttributes(queryValues);
            this.queryInformation.setAttributeSqlColumnIndices(columnIndices);
            rowsAffected = this.executeUpdate();

        return rowsAffected;

    }

    /**
     * Helper method to return a new ID
     * @return UUID to be used, generally, as a primary-key.
     */
    private UUID newID() {
        return UUID.randomUUID();
    }


}
