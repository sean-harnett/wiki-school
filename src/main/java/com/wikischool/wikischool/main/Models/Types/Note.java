package com.wikischool.wikischool.main.Models.Types;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Class to store notes that correspond to courses in the application.
 * Also stores a list of URLs to online/external resources relevant to the note.
 * Contains only Constructor(1) getters and setters.
 *
 * @author sean-harnett
 */
public class Note {
    private UUID id;
    private String title;
    private String textBody;
    private LocalDateTime timeLastUpdated; //simple date storage.
    private List<String> internetResources; // Will change to URL


    public Note(String title, String textBody, LocalDateTime timeLastUpdated, List<String> internetResources) {

        this.title = title;
        this.textBody = textBody;
        this.timeLastUpdated = timeLastUpdated;
        this.internetResources = internetResources;

    }

    public Note(String title, String textBody, UUID id) {
        this.id = id;
        this.title = title;
        this.textBody = textBody;
    }
    public Note(String textBody, UUID id){
        this.title = null;
        this.textBody = textBody;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public LocalDateTime getTimeLastUpdated() {
        return timeLastUpdated;
    }

    public void setTimeLastUpdated(LocalDateTime timeLastUpdated) {
        this.timeLastUpdated = timeLastUpdated;
    }

    public List<String> getInternetResources() {
        return internetResources;
    }

    public void setInternetResources(List<String> internetResources) {
        this.internetResources = internetResources;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
