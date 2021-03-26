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

import java.sql.SQLException;
import java.util.UUID;

/**
 * Class to work with student objects and their functionality
 * TODO: implement the rest of the student methods
 * TODO: abstract SQL syntax.
 * @author sean-harnett
 */

@Service
public class StudentService extends GeneralService {

    private final LRUCache<UUID, Student> studentCache = new LRUCache<>(SizeConstants.DEFAULT_CACHE_LENGTH);
    private final String delimiter = "-";

    @Autowired
    public StudentService(StringFormatter stringFormatter, SqlQueryExecutor executor) {

        super(new StudentTableAttributes(), stringFormatter, executor);
        this.readProperties();
    }

    /**
     * What methods are required:
     * 1) Create new Student object.
     * 2) insert student into database using GeneralService.
     *
     * @see GeneralService
     * 3) select one or more student,
     * 4) delete students
     * 5) add courses to student's course list
     */

    public Student createStudent(String firstName, String lastName) {

        return new Student(newID(), firstName, lastName);

    }

    public void insertStudentIntoDatabase(Student student) throws SQLException {

        String nonformattedQuery = "INSERT INTO student (-,-,-) VALUES (?,?,?)";

        UUID id = student.getId();
        String firstName = student.getFirstName();
        String lastName = student.getLastName();

        String[] formatAttributes = {"id","first_name","last_name"};

        SqlQueryInformation<UUID> queryInformation = this.constructStatement(nonformattedQuery, formatAttributes, this.delimiter);

        Object[] queryValues = {id, firstName,lastName};

        int[] columnIndices = {1, 2, 3};

        queryInformation.setRecordAttributes(queryValues);
        queryInformation.setAttributeSqlColumnIndices(columnIndices);

        this.queryInformation = queryInformation;

        this.executeUpdate();

    }


    private UUID newID() {
        return UUID.randomUUID();
    }


}
