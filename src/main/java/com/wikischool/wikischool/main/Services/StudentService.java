package com.wikischool.wikischool.main.Services;

import com.wikischool.wikischool.main.Models.Interfaces.Standard_Service_Operations;
import com.wikischool.wikischool.main.Models.People.Student;
import com.wikischool.wikischool.main.Models.Types.Course;
import com.wikischool.wikischool.main.utilities.HashMap_FNV;
import com.wikischool.wikischool.main.utilities.LRUCache;
import com.wikischool.wikischool.main.utilities.SizeConstants;

import java.util.*;

public class StudentService implements Standard_Service_Operations<Student> { // No Getters or Setters

    private final LRUCache StudentCache = new LRUCache(SizeConstants.DEFAULT_CACHE_LENGTH);

    // hard memory, that will switch to JDBC or similar:
    private final HashMap_FNV testMemory = new HashMap_FNV(SizeConstants.DEFAULT_MAP_LENGTH); // Create hashMap interface.

    //Constructor:
    public StudentService(){}

    //Main Methods:

    public Student createStudentBaseTraits(UUID id, String firstName, String lastName) {
        Student newStudent = new Student(id, firstName, lastName);
        return newStudent;
    }
    public List<Course> getStudentCoursesById(UUID id){
        if(!this.testMemory.containsKey(id)){
            return null;
        }
        Student targetStudent = (Student)this.testMemory.get(id);
        List<Course> studentCourses = targetStudent.getCourses();
         return studentCourses;
    }

    @Override
    public boolean deleteById(UUID id) {
        if(!this.testMemory.containsKey(id)){
            return false;
        }
        this.testMemory.remove(id);
        this.deleteFromCache(id);
        return true;
    }

    @Override
    public boolean updateById(UUID id, Student updatedAttributes) { //This will be very different with actual connection to database
        if(!this.testMemory.containsKey(id)){
            return false;
        }
        if(id != updatedAttributes.getId()){
            return false; //throw error probably better..?
        }
        Student targetStudent = (Student)this.testMemory.get(id);
        targetStudent.setFirstName(updatedAttributes.getFirstName());
        targetStudent.setLastName(updatedAttributes.getFirstName());

        return true;
    } // This is bad implementation

    @Override
    public List<Student> retrieveAll() {

        if(this.testMemory.isMapEmpty()){ //will want to throw error here
            return null;
        }

        List<Student> students = new ArrayList<Student>(SizeConstants.DEFAULT_LIST_LENGTH);

        for(UUID key : this.testMemory.keySet()){
            students.add((Student)this.testMemory.get(key));
        }
        return students;
    }

    //Cache helper: -> are these completely pointless..?
    private void deleteFromCache(UUID studentId){
        this.StudentCache.cacheDelete(studentId);
    }
    private Student retrieveFromCache(UUID studentId){
        return (Student)this.StudentCache.get(studentId);
    }

    private void insertIntoCache(UUID studentId, Student studentToInsert){
        this.StudentCache.put(studentId, studentToInsert);
    }
}
