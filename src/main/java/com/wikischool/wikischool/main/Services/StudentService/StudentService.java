package com.wikischool.wikischool.main.Services.StudentService;

import com.wikischool.wikischool.main.Models.People.Student;
import com.wikischool.wikischool.main.ConnectionObjects.Queries.SqlQueryExecutor;
import com.wikischool.wikischool.main.ConnectionObjects.Queries.SqlQueryInformation;
import com.wikischool.wikischool.main.Services.ServiceAbstraction.GeneralService;
import com.wikischool.wikischool.main.utilities.Constants.SizeConstants;
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
 * <p>
 * TODO: abstract SQL syntax.
 *
 * @author sean-harnett
 */

@Service
public class StudentService extends GeneralService {



    @Autowired
    public StudentService(StringFormatter stringFormatter, SqlQueryExecutor executor) {

        super(stringFormatter, executor, "-");

        this.queryInformation = new SqlQueryInformation<>();

        this.readProperties();
    }

    /**
     * Method to instantiate a student object.
     *
     * @param firstName Students first name
     * @param lastName  Students last name
     * @return a new Student object populated with first name, last name, and an auto-generated ID field
     */
    public Student createStudent(String firstName, String lastName) {

        Student student = new Student(firstName, lastName);
        return student;
    }

    /**
     * Insert one student into the database.
     *
     * @param student the object, containing at least mandatory fields to populate a database record of type Student.
     * @throws SQLException
     */
    public void insertStudentIntoDatabase(Student student) throws SQLException {

        String nonformattedQuery = "INSERT INTO student (-,-) VALUES (?,?)";

        String firstName = student.getFirstName();
        String lastName = student.getLastName();

        String[] formatAttributes = {"first_name", "last_name"};

        this.queryInformation.setUnFormattedSqlStatement(nonformattedQuery);

        this.constructStatement(nonformattedQuery, formatAttributes);

        Object[] queryValues = {firstName, lastName};

        int[] columnIndices = {1, 2};

        queryInformation.setRecordAttributes(queryValues);
        queryInformation.setAttributeSqlColumnIndices(columnIndices);


        try {
            this.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }

        try {
            this.closeAllDatabaseObjects();
        } catch (SQLException e) {
            throw e;
        }

        this.resetQueryAttributes();

    }

    /**
     * Method that will select a single student from the database based off of ID.
     *
     * @param targetStudentId UUID type, of the target student.
     * @return Student object
     * @throws SQLException
     */
    public Student selectStudentByIdFromDatabase(UUID targetStudentId) throws SQLException {
        Student foundStudent = null;

        //Create the query:
        String nonformattedQuery = "SELECT - FROM student WHERE -";
        String[] formatAttributes = {"first_name,last_name", "id=?"};

        this.constructStatement(nonformattedQuery, formatAttributes);

        Object[] queryValues = {targetStudentId};
        int[] columnIndices = {1};

        this.queryInformation.setRecordAttributes(queryValues);
        this.queryInformation.setAttributeSqlColumnIndices(columnIndices);

        //Execute the query:

        try {
            this.executeQuery();
        } catch (SQLException e) {
            throw e;
        }
        ResultSet rs = this.getResultSet();


        // If the result set retrieved anything, set the columns to corresponding student class members:
        if (rs.getFetchSize() > 0) {

            String firstName = rs.getString(1);
            String lastName = rs.getString(2);

            foundStudent = new Student(targetStudentId, firstName, lastName);

        }
        try {
            this.closeAllDatabaseObjects(); // throws SQLException
        } catch (SQLException e) {
            throw e;
        }
        this.resetQueryAttributes(); // reset the queryInformation object

        return foundStudent;

    }

    /**
     * Method to delete a Student from the connected database.
     *
     * @param targetStudentId the id to find, and delete it's record.
     * @throws SQLException
     */
    public boolean deleteStudentByIdFromDataBase(UUID targetStudentId) throws SQLException, IllegalArgumentException {

       checkNullArgument(targetStudentId,"'targetStudentId' cannot be null in method: 'deleteStudentByIdFromDataBase");


        String query = "DELETE FROM student WHERE id=?";

        this.queryInformation.setUnFormattedSqlStatement(query);
        this.queryInformation.setFormattedSqlStatement(query);

        int rowsAffected = 0;

        Object[] queryValues = {targetStudentId};

        int[] columnIndices = {1};

        this.queryInformation.setRecordAttributes(queryValues);

        this.queryInformation.setAttributeSqlColumnIndices(columnIndices);
        try {

            rowsAffected = this.executeUpdate();

        } catch (SQLException e) {
            throw e;
        }

        try {
            this.closeAllDatabaseObjects();
        } catch (SQLException e) {
            throw e;
        }
        this.resetQueryAttributes(); // reset the information object for the next query
        return rowsAffected > 0; // boolean, whether any rows were affected

    }

    /**
     * Method that returns a list of all the students in the database.
     *
     * @return List of type Student.
     * @throws SQLException
     */
    public List<Student> retrieveAllStudents() throws SQLException {

        String query = "SELECT id,first_name,last_name FROM student"; // no need to format

        this.queryInformation.setUnFormattedSqlStatement(query);

        this.queryInformation.setFormattedSqlStatement(query);

        try {
            this.executeQuery();
        } catch (SQLException e) {
            throw e;
        }

        ResultSet rs = this.getResultSet();
        List<Student> students = new ArrayList<>();

        while (rs.next()) {
            students.add(new Student(rs.getObject(1, UUID.class), rs.getString(2), rs.getString(3)));
        }

        try {
            this.closeAllDatabaseObjects();
        } catch (SQLException e) {
            throw e;
        }
        this.resetQueryAttributes();

        return students;

    }

    /**
     * Method used to update a single student record by Id within a database.
     *
     * @param student Object containing the values to update.
     * @return boolean, whether the student record was updated or not.
     * @throws SQLException
     * @throws IllegalStateException thrown when student param is null, or student.getId() returns null.
     */
    public boolean updateStudentById(Student student) throws SQLException, IllegalStateException {

        if (student == null) {
            throw new IllegalStateException("Error in method 'updateStudentById: 'student' parameter cannot be null.");
        }
        if (student.getId() == null) {
            throw new IllegalStateException("Error in method 'updateStudentById: 'student.getId()' cannot return null.");
        }

        int rowsAffected;

        String nonformattedQuery = "UPDATE student SET - WHERE id=?";
        this.resetQueryAttributes();
        this.queryInformation.setUnFormattedSqlStatement(nonformattedQuery);

        String[] formatAttributes = new String[1];
        UUID targetId = student.getId();

        Object[] fields = {student.getFirstName(), student.getLastName()};

        String[] potentialQueryFields = {"first_name=?", "last_name=?"};
        Object[] potentialValues = new Object[3];
        int[] potentialIndices = new int[3];

        StringBuilder queryBuilder = new StringBuilder();

        int size = this.createQueryIndexAttributes(fields, targetId, potentialIndices, potentialValues, queryBuilder, potentialQueryFields);

        if (size == -1) {
            throw new IllegalStateException("Error: no fields were provided to method: updateStudentById");
        }

        formatAttributes[0] = queryBuilder.toString();
        this.constructStatement(nonformattedQuery, formatAttributes);

        this.queryInformation.setRecordAttributes(potentialValues);
        this.queryInformation.setAttributeSqlColumnIndices(potentialIndices);

        try {
            rowsAffected = this.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
        try {
            this.closeAllDatabaseObjects();
        } catch (SQLException e) {
            throw e;
        }

        this.resetQueryAttributes();

        return rowsAffected > 0;

    }
}
