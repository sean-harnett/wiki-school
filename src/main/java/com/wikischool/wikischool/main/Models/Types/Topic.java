package com.wikischool.wikischool.main.Models.Types;

import java.util.List;
import java.util.UUID;

public class Topic {

    private UUID id;
    private String title;
    private List<WikiResource> resources;

    public Topic() {
    }

    public Topic(UUID id, String title, List<WikiResource> resources) {
        this.id = id;
        this.title = title;
        this.resources = resources;
    }

    public Topic(String title){
        this.title = title;
    }

    public Topic(UUID id, String title) {
        this.id = id;
        this.title = title;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<WikiResource> getResources() {
        return resources;
    }

    public void setResources(List<WikiResource> resources) {
        this.resources = resources;
    }

}
