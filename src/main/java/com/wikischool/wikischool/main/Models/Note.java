package com.wikischool.wikischool.main.Models;

import java.time.LocalDate;
import java.util.List;

/**
 * Class to store notes that correspond to courses in the application.
 * Also stores a list of URLs to online/external resources relevant to the note.
 * Contains only Constructor(1) getters and setters.
 * @author sean-harnett
 */
public class Note {
    // may need UUID for storing in table
    private String title;
    private String textBody;
    private LocalDate timeLastUpdated; //simple date storage.
    private List<String> internetResources; // Will change to URL


    public Note(String title, String textBody, LocalDate timeLastUpdated, List<String> internetResources) {
        this.title = title;
        this.textBody = textBody;
        this.timeLastUpdated = timeLastUpdated;
        this.internetResources = internetResources;
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

    public LocalDate getTimeLastUpdated() { return timeLastUpdated; }

    public void setTimeLastUpdated(LocalDate timeLastUpdated) {
        this.timeLastUpdated = timeLastUpdated;
    }

    public List<String> getInternetResources() {
        return internetResources;
    }

    public void setInternetResources(List<String> internetResources) {
        this.internetResources = internetResources;
    }
}
