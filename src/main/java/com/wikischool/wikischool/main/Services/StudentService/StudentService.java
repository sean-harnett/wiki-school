package com.wikischool.wikischool.main.Services.StudentService;

import com.wikischool.wikischool.main.Models.People.Student;
import com.wikischool.wikischool.main.Queries.SqlQueryExecutor;
import com.wikischool.wikischool.main.Queries.SqlQueryInformation;
import com.wikischool.wikischool.main.Services.GeneralService;
import com.wikischool.wikischool.main.utilities.Constants.SizeConstants;
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
 * <p>
 * TODO: abstract SQL syntax.
 *
 * @author sean-harnett
 */

@Service
public class StudentService extends GeneralService {

    private final LRUCache<UUID, Student> studentCache = new LRUCache<>(SizeConstants.DEFAULT_CACHE_LENGTH);

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
    public Student createStudent(String firstName, String lastName, UUID id) {
        if (id == null)
            return null;
        Student student = new Student(id, firstName, lastName);
        this.studentCache.putIntoCache(id, student);
        return student;
    }

    /**
     * Insert one student into the database.
     *
     * @param student the object, containing at least mandatory fields to populate a database record of type Student.
     * @throws SQLException
     */
    public void insertStudentIntoDatabase(Student student) throws SQLException {

        String nonformattedQuery = "INSERT INTO student (-,-,-) VALUES (?,?,?)";

        UUID id = student.getId();
        String firstName = student.getFirstName();
        String lastName = student.getLastName();

        String[] formatAttributes = {"id", "first_name", "last_name"};

        this.queryInformation.setUnFormattedSqlStatement(nonformattedQuery);

        this.constructStatement(nonformattedQuery, formatAttributes);

        Object[] queryValues = {id, firstName, lastName};

        int[] columnIndices = {1, 2, 3};

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

        this.studentCache.putIntoCache(id, student);

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

        if (this.studentCache.checkIfCacheContainsKey(targetStudentId)) {
            foundStudent = this.studentCache.getFromCache(targetStudentId);
        } else {
            //Create the query:
            String nonformattedQuery = "SELECT - FROM student WHERE -";
            String[] formatAttributes = {"*", "id=?"};
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

                UUID id = rs.getObject(1, UUID.class);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);

                foundStudent = new Student(id, firstName, lastName);

                // Add the student to cache.
                this.studentCache.putIntoCache(id, foundStudent);

            }
            try {
                this.closeAllDatabaseObjects(); // throws SQLException
            } catch (SQLException e) {
                throw e;
            }
            this.resetQueryAttributes(); // reset the queryInformation object
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
        if (targetStudentId == null) {
            return false;
        }

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

            if (studentCache.checkIfCacheContainsKey(targetStudentId)) {
                studentCache.removeFromCache(targetStudentId);
            }


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

        String query = "SELECT * FROM student"; // no need to format

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
     * @return integer- rows affected, returns -1 when error occurs.
     * @throws SQLException
     */
    public boolean updateStudentById(Student student) throws SQLException {


        String nonformattedQuery = "UPDATE student SET - WHERE id=?";
        int rowsAffected = 0;
        this.resetQueryAttributes();
        this.queryInformation.setUnFormattedSqlStatement(nonformattedQuery);

        String[] formatAttributes = new String[1];
        UUID targetId = student.getId();
        String firstName = student.getFirstName();
        String lastName = student.getLastName();

        Object[] queryValues = null;
        int[] columnIndices = null;


        int size = 1;
        Object[] potentialValues = new Object[3];
        int[] potentialIndices = new int[3];
        potentialValues[2] = targetId;

        StringBuilder queryBuilder = new StringBuilder();

        if (firstName != null) {
            queryBuilder.append("first_name=?");
            potentialValues[0] = firstName;
            potentialIndices[0] = 1;
            size++;
        }

        if (lastName != null) {
            if (size > 0) {
                queryBuilder.append(",last_name=?");
                potentialIndices[1] = 2;
                potentialIndices[2] = 3;
            } else {
                queryBuilder.append("last_name=?");
                potentialIndices[0] = 1;
                potentialIndices[1] = 2;
            }

            potentialValues[1] = lastName;
            size++;
        }

        queryValues = new Object[size];
        columnIndices = new int[size];

        for (int ix = 0; ix < potentialValues.length; ix++) {

            if (potentialValues[ix] != null) {
                queryValues[ix] = potentialValues[ix];
                columnIndices[ix] = potentialIndices[ix];
            }
            if (potentialIndices[ix] > 0) {
                columnIndices[ix] = potentialIndices[ix];
            }
        }
        formatAttributes[0] = queryBuilder.toString();
        this.constructStatement(nonformattedQuery, formatAttributes);

        if (queryValues == null || columnIndices == null) { // is error return -1.
            return rowsAffected > 0;
        }
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
        this.resetQueryAttributes();
        if (rowsAffected > 0)
            this.studentCache.putIntoCache(targetId, student);
        return rowsAffected > 0;

    }
}
