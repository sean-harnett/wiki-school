package com.wikischool.wikischool.main.Models.Types;

public class Note { //Will store notes about courses

    private final String title;
    private final String category;
    private final String textBody;

    public Note(String title, String category, String noteBody) {
        this.title = title;
        this.category = category;
        this.textBody = noteBody;
    }
}
