package com.wikischool.wikischool.main.Models;

import com.wikischool.wikischool.main.Models.Interfaces.Standard_Service_Operations;
import com.wikischool.wikischool.main.utilities.NoteAttributeIndex;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class NoteService implements Standard_Service_Operations<Note, NoteAttributeIndex> {


    @Override
    public boolean InsertIntoDataBase(Note entity) {
        return false;
    }

    @Override
    public Note create(Map<NoteAttributeIndex, Object> entityAttributes) {
        if(entityAttributes.size() == 0){
            return null;
        }
        Note newNote;
        String title = (String)entityAttributes.get(NoteAttributeIndex.TITLE);
        String noteBody = (String)entityAttributes.get(NoteAttributeIndex.TEXT_BODY);
        List<String> internetResources = (List<String>)entityAttributes.get(NoteAttributeIndex.RESOURCES);
        LocalDate dateNoteCreated = LocalDate.now();
        newNote = new Note(title, noteBody, dateNoteCreated, internetResources);
        return newNote;
    }

    @Override
    public boolean createAndInsertEntity(Map<NoteAttributeIndex, Object> entityAttributes) {
        return false;
    }

    @Override
    public boolean deleteById(UUID id) {
        return false;
    }

    @Override
    public List<Note> retrieveAll() {
        return null;
    }
}
