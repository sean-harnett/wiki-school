package com.wikischool.wikischool.StudentTests;

import com.wikischool.wikischool.WikiTestConstants;
import com.wikischool.wikischool.main.Models.People.Student;
import com.wikischool.wikischool.main.Services.StudentService.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class StudentTests {

    /**
     * Test Method used to test the StudentService Ability to insert one student into the database.
     * @param studentService
     */
    @Test
    public void TestStudentInsert(@Autowired StudentService studentService) {

        Student student = studentService.createStudent("Test", "Name", UUID.fromString(WikiTestConstants.TEST_STUDENT_ID));
        try {
            studentService.insertStudentIntoDatabase(student);
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            studentService.closeAllDatabaseObjects();
        }catch(SQLException e){
            System.out.println(e);
        }

    }

    /**
     * Test if the Student service can delete a student using an ID.
     * @param studentService
     */
    @Test
    public void TestStudentDeleteById(@Autowired StudentService studentService) {

        UUID id = UUID.fromString(WikiTestConstants.TEST_STUDENT_ID);
        boolean didDelete = false;
        try {
            didDelete = studentService.deleteStudentByIdFromDataBase(id);
        } catch (SQLException e) {
            System.out.println(e);
        }
        assertThat(didDelete).isEqualTo(false);
        try {
            studentService.closeAllDatabaseObjects();
        }catch(SQLException e){
            System.out.println(e);
        }

    }

    /**
     * Test Student Service methods used to select a student by an ID from the database.
     * @param studentService
     */
    @Test
    public void TestStudentSelectById(@Autowired StudentService studentService) {
        UUID id = UUID.fromString(WikiTestConstants.TEST_STUDENT_ID);
        Student foundStudent = null;
        try {
            foundStudent = studentService.selectStudentByIdFromDatabase(id);
        } catch (SQLException e) {
            System.out.println(e);
        }
        if (foundStudent != null) {
            assertThat(foundStudent.getId()).isEqualTo(WikiTestConstants.TEST_STUDENT_ID);
        }
        try {
            studentService.closeAllDatabaseObjects();
        }catch(SQLException e){
            System.out.println(e);
        }

    }

    /**
     * Method testing if the student service can return a complete list of all students present in the database.
     * @param studentService
     */

    @Test
    public void TestSelectAllStudents(@Autowired StudentService studentService) {

        List<Student> students = null;

        try {
            students = studentService.retrieveAllStudents();
        } catch (SQLException e) {
            System.out.println(e);
        }
        for (Student student : students) {

            System.out.println("\t { ");
            System.out.println("\t \tid: " + student.getId());
            System.out.println("\t \t" + "First Name: " + student.getFirstName());
            System.out.println("\t \t" + "Last Name: " + student.getLastName());
            System.out.println("\t }");

        }
    }

    /**
     * Tests if the student service will update a single student record.
     * @param studentService
     */
    @Test
    public void TestUpdateStudent(@Autowired StudentService studentService){
        UUID id = UUID.fromString(WikiTestConstants.TEST_STUDENT_ID);
        Student studentFieldsToUpdate = new Student(id,"New", "Name");
        try{
            boolean rowsAffected = studentService.updateStudentById(studentFieldsToUpdate);
            System.out.println(rowsAffected);
        }catch(SQLException e){
            System.out.println(e);
        }
        try {
            studentService.closeAllDatabaseObjects();
        }catch(SQLException e){
            System.out.println(e);
        }

    }

}
