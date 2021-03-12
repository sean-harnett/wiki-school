package com.wikischool.wikischool.main.Services;

import com.wikischool.wikischool.main.Models.Interfaces.Standard_Service_Operations;
import com.wikischool.wikischool.main.Models.People.Student;
import com.wikischool.wikischool.main.Models.Types.Course;
import com.wikischool.wikischool.main.utilities.HashMap_FNV;
import com.wikischool.wikischool.main.utilities.LRUCache;
import com.wikischool.wikischool.main.utilities.SizeConstants;
import com.wikischool.wikischool.main.utilities.StudentAttributeIndex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Class to work with student objects and their functionality
 * @author sean-harnett
 */
public class StudentService implements Standard_Service_Operations<Student, StudentAttributeIndex> {

    private final LRUCache StudentCache = new LRUCache(SizeConstants.DEFAULT_CACHE_LENGTH);

    // hard memory, that will switch to JDBC or similar:
    private final HashMap_FNV<UUID, Student> testMemory = new HashMap_FNV<UUID, Student>(SizeConstants.DEFAULT_MAP_LENGTH);

    //Constructor(s):

    /**
     * Empty Constructor.
     */
    public StudentService() {
    }

    //Main Methods:

    /**
     * Method to obtain a list of courses the student attends.
     * @param studentId UUID to identify a specific student
     * @return List of courses the student contains
     */
    public List<Course> getStudentCoursesById(UUID studentId) {
        if (!this.testMemory.containsKey(studentId)) {
            return null;
        }
        Student targetStudent = (Student) this.testMemory.get(studentId);
        List<Course> studentCourses = targetStudent.getCourses();
        return studentCourses;
    }

    /**
     * Find a specific student through their ID, and delete them.
     * @param id UUID to identify a specific student
     * @return boolean whether the student was deleted.
     */
    public boolean deleteById(UUID id) {
        if (!this.testMemory.containsKey(id)) {
            return false;
        }
        this.testMemory.remove(id);
        this.StudentCache.cacheDelete(id);
        return true;
    }

    /**
     * Find a student and update them, with new qualities.
     * For example: change their name.
     * The implementation and type of parameters are subject to change.
     * @param id UUID to identify a specific student
     * @param updatedAttributes This is a student object to compare and change the attributes that are different
     * @return boolean - if attributes were changed
     */
    public boolean updateById(UUID id, Student updatedAttributes) {
        // Implement when connected to database.

        return true;
    }

    //Interface Implementations:

    /**
     * Insert a student into a database.
     * @param entity Student to insert
     * @return boolean - whether the student was inserted.
     */
    @Override
    public boolean InsertIntoDataBase(Student entity) { //Implement after jdbc driver written
        return false;
    }

    /**
     * Create a new student based off of attribute mappings.
     * @param entityAttributes Map using [StudentAttributeIndex enum, Object] [key,value] pairs.
     * @return Student
     */

    @Override
    public Student create(Map<StudentAttributeIndex, Object> entityAttributes) {
        Student createdStudent;
        UUID id = (UUID) entityAttributes.get(StudentAttributeIndex.ID);
        String firstName = (String) entityAttributes.get(StudentAttributeIndex.FIRST_NAME);
        String lastName = (String) entityAttributes.get(StudentAttributeIndex.LAST_NAME);
        createdStudent = new Student(id, firstName, lastName);
        return createdStudent;
    }

    /**
     * Create and insert a student into a database.
     * @param entityAttributes Map using [StudentAttributeIndex enum, Object] [key,value] pairs.
     * @return boolean - whether the operations were successful.
     */
    @Override
    public boolean createAndInsertEntity(Map<StudentAttributeIndex, Object> entityAttributes) {
        return false;
    }

    /**
     * Method that returns all students.
     * Its specific implementation will change.
     * @return - List of Students
     */
    @Override
    public List<Student> retrieveAll() { // reimplement with jdbc connection

        if (this.testMemory.isMapEmpty()) { //will want to throw error here
            return null;
        }

        List<Student> students = new ArrayList<Student>(SizeConstants.DEFAULT_LIST_LENGTH);

        for (UUID key : this.testMemory.keySet()) {
            students.add((Student) this.testMemory.get(key));
        }
        return students;
    }

    /**
     * Helper function to obtain an object of student type from the cache.
     * This method will no longer be needed when the LRU cache changes to use generics.
     * @param studentId Identifier to find specific student in cache.
     * @return Student object
     */
    private Student retrieveFromCache(UUID studentId) {
        return (Student) this.StudentCache.get(studentId);
    }


}
