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

public class StudentService implements Standard_Service_Operations<Student, StudentAttributeIndex> { // No Getters or Setters

    private final LRUCache StudentCache = new LRUCache(SizeConstants.DEFAULT_CACHE_LENGTH);

    // hard memory, that will switch to JDBC or similar:
    private final HashMap_FNV<UUID, Student> testMemory = new HashMap_FNV<UUID, Student>(SizeConstants.DEFAULT_MAP_LENGTH); // Create hashMap interface.

    //Constructor(s):
    public StudentService() {
    }

    //Main Methods:

    public List<Course> getStudentCoursesById(UUID studentId) {
        if (!this.testMemory.containsKey(studentId)) {
            return null;
        }
        Student targetStudent = (Student) this.testMemory.get(studentId);
        List<Course> studentCourses = targetStudent.getCourses();
        return studentCourses;
    }


    public boolean deleteById(UUID id) {
        if (!this.testMemory.containsKey(id)) {
            return false;
        }
        this.testMemory.remove(id);
        this.deleteFromCache(id);
        return true;
    }

    public boolean updateById(UUID id, Student updatedAttributes) {
        // Implement when connected to database.

        return true;
    }

    //Interface Implementations:

    @Override
    public boolean InsertIntoDataBase(Student entity) { //Implement after jdbc driver written
        return false;
    }

    @Override
    public Student create(Map<StudentAttributeIndex, Object> entityAttributes) {
        Student createdStudent;
        UUID id = (UUID) entityAttributes.get(StudentAttributeIndex.ID);
        String firstName = (String) entityAttributes.get(StudentAttributeIndex.FIRST_NAME);
        String lastName = (String) entityAttributes.get(StudentAttributeIndex.LAST_NAME);
        createdStudent = new Student(id, firstName, lastName);
        return createdStudent;
    }

    @Override
    public boolean createAndInsertEntity(Map<StudentAttributeIndex, Object> entityAttributes) {
        return false;
    }


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

    //Cache helper: -> are these completely pointless..?
    private void deleteFromCache(UUID studentId) {
        this.StudentCache.cacheDelete(studentId);
    }

    private Student retrieveFromCache(UUID studentId) {
        return (Student) this.StudentCache.get(studentId);
    }

    private void insertIntoCache(UUID studentId, Student studentToInsert) {
        this.StudentCache.put(studentId, studentToInsert);
    }
}
