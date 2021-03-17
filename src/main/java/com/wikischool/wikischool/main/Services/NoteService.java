package com.wikischool.wikischool.main.Services;

import com.wikischool.wikischool.main.Models.Interfaces.Standard_Service_Operations;
import com.wikischool.wikischool.main.Models.Types.Note;
import com.wikischool.wikischool.main.utilities.NoteAttributeIndex;


import java.time.LocalDate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * Service class to work with Note objects.
 * @author sean-harnett
 */
public class NoteService implements Standard_Service_Operations<Note, NoteAttributeIndex> {

    /**
     * Insert the Note into the database
     * @param entity Note to insert into the databse
     * @return boolean - whether was insertion successfull
     */
    @Override
    public boolean InsertIntoDataBase(Note entity) {
        return false;
    }

    /**
     * Create a new note from a map of Enum, Object key value pairs.
     * @param entityAttributes map of Note attributes using Enum of NoteAttributeIndex for keys
     * @return Note object - newly created
     */
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

    /**
     * Create a new Note object, and insert it into the database.
     * @param entityAttributes map of Note attributes using Enum of NoteAttributeIndex for keys
     * @return boolean whether the operations were successful
     */

    @Override
    public boolean createAndInsertEntity(Map<NoteAttributeIndex, Object> entityAttributes) {
        return false;
    }

    /**
     * Find a Note whose ID matches the parameter, and delete it.
     * @param id UUID used to find the corresponding Note object
     * @return boolean - whether the object was found, and deleted or not.
     */
    @Override
    public boolean deleteById(UUID id) {
        return false;
    }

    /**
     * Method to retrieve a list of all notes.
     * @return List of Notes
     */
    @Override
    public List<Note> retrieveAll() {
        return null;
    }
}
