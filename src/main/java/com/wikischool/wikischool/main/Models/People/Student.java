package com.wikischool.wikischool.main.Models.People;

import com.wikischool.wikischool.main.Models.Types.Course;

import java.util.List;
import java.util.UUID;

/**
 * Student class used to represent student entries in the database.
 *
 * @author sean-harnett
 */
public class Student  {

    private UUID id;
    private String firstName;
    private String lastName;
    List<Course> courses;



    /**
     * Simple constructor to create a base student object.
     * Initialises an empty ArrayList of courses using a default Constant length.
     * @param id Identification UUID
     * @param firstName Students first name
     * @param lastName Students last name
     */
    public Student(UUID id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.courses = null;
    }
    public Student(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Empty constructor
     */
    public Student(){}

    //Student Course Management:

    /**
     * Add a course object to the student
     * @param newCourse Course to add to the course list
     */
    public void addCourse(Course newCourse){
        this.courses.add(newCourse);
    }

    /**
     * Remove a course from the Course list
     * @param targetCourse course to remove
     */
    public void removeCourse(Course targetCourse){
        this.courses.remove(targetCourse);
    }

    /**
     * Check whether a student is taking course
     * @param checkCourse course to check
     * @return boolean - whether the course is in the list or not
     */
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
