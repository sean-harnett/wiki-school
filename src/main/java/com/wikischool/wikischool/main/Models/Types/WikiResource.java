package com.wikischool.wikischool.main.Models.Types;

import java.net.URL;
import java.util.UUID;

public class WikiResource {
    private UUID id;
    private URL resourceLink;

    public WikiResource(URL resourceLink) {
        this.resourceLink = resourceLink;
    }

    public WikiResource(UUID id, URL resourceLink) {
        this.id = id;
        this.resourceLink = resourceLink;
    }

    public WikiResource(){}


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public URL getResourceLink() {
        return resourceLink;
    }

    public void setResourceLink(URL resourceLink) {
        this.resourceLink = resourceLink;
    }
}
