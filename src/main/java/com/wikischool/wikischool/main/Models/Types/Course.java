package com.wikischool.wikischool.main.Models.Types;

import java.util.UUID;

/**
 * Object to store course attributes.
 * Contains only getters + setter and Constructor(2)
 * @author sean-harnett
 */
public class Course {
    private String name;
    private UUID id;

    /**
     * Main Course Constructor
     * @param name Course name
     * @param id UUID - Course Identifier
     */
    public Course(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Empty course constructor
     */
    public Course(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
