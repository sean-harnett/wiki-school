package com.wikischool.wikischool.main.Models.People;

import com.wikischool.wikischool.main.Models.Types.Course;
import com.wikischool.wikischool.main.utilities.SizeConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Student  {
    private UUID id;
    private String firstName;
    private String lastName;
    //private SizeConstants sizeConstants;
    //What type of list..? -> arrayList most likely
    List<Course> courses; //store whole course object, or just a set of UUIDs...?


    //Constructors:
    public Student(UUID id, String firstName, String lastName) { // add course list initial capacity capacity...?
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.courses = new ArrayList<Course>(SizeConstants.DEFAULT_LIST_LENGTH); // will need to be instantiated when I decide what list to make it
    }
    public Student(UUID id){

    }

    //Student Course Management:

    public void addCourse(Course newCourse){
        this.courses.add(newCourse);
    }
    public void removeCourse(Course newCourse){
        this.courses.remove(newCourse);
    }
    public boolean isStudentTakingCourse(Course checkCourse){
        return this.courses.contains(checkCourse);
    }

    //Getters + Setters:
    public List<Course> getCourses(){
        return this.courses;
    }
    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
