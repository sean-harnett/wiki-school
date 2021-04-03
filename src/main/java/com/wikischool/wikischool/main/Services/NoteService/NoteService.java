package com.wikischool.wikischool.main.Services.NoteService;

import com.wikischool.wikischool.main.Models.Types.Note;
import com.wikischool.wikischool.main.Queries.SqlQueryExecutor;
import com.wikischool.wikischool.main.Queries.SqlQueryInformation;
import com.wikischool.wikischool.main.Services.GeneralService;
import com.wikischool.wikischool.main.utilities.Constants.SizeConstants;
import com.wikischool.wikischool.main.utilities.DataStructures.LRUCache.LRUCache;
import com.wikischool.wikischool.main.utilities.StringFormatting.StatementFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service class to work with Note objects.
 * TODO: Write the rest of the methods, will be similar functionality to StudentService.
 * TODO: abstract SQL syntax.
 *
 * @author sean-harnett
 * @see com.wikischool.wikischool.main.Services.StudentService.StudentService
 */
public class NoteService extends GeneralService {

    private final LRUCache<UUID, Note> noteCache = new LRUCache<>(SizeConstants.DEFAULT_CACHE_LENGTH);

    @Autowired
    public NoteService(@Qualifier("StringFormatter") StatementFormatter formatterType, SqlQueryExecutor executor, String delimiter) {

        super(formatterType, executor, delimiter);

        this.queryInformation = new SqlQueryInformation<>();

        this.readProperties();
    }

    /**
     * Method to instantiate a new Note Object
     *
     * @param id       UUID object to use as the notes primary key.
     * @param title    String, that serves as the title of the note.
     * @param textBody String serving as the main paragraphs making up the note.
     */
    public Note createNote(UUID id, String title, String textBody) {
        if (id == null || title == null || textBody == null) {
            return null;
        }
        if (title.length() == 0 || textBody.length() == 0) {
            return null;
        }
        Note note = new Note(title, textBody, id);
        note.setTimeLastUpdated(LocalDateTime.now());

        this.noteCache.putIntoCache(id, note); // add the new Note to cache
        return note;

    }

    /**
     * Insert a note object into the database.
     *
     * @param note containing at least the required Id primary key value to insert a record into the database.
     * @throws SQLException
     */
    public boolean insertNoteIntoDatabase(Note note) throws SQLException {
        if (note == null)
            return false;

        String nonformattedQuery = "INSERT INTO note (-,-,-) VALUES (?,?,?)";


        UUID id = note.getId();
        String title = note.getTitle();
        String textBody = note.getTextBody();

        if (id == null)
            return false;


        String[] formatAttributes = {"id", "title", "textBody"};

        this.constructStatement(nonformattedQuery, formatAttributes);


        Object[] queryValues = {id, title, textBody};
        int[] columnIndices = {1, 2, 3};

        queryInformation.setRecordAttributes(queryValues);
        queryInformation.setAttributeSqlColumnIndices(columnIndices);

        int rowsAffected = this.executeUpdate();

        noteCache.putIntoCache(id, note);

        return rowsAffected > 0;

    }

    /**
     * Method that selects a single note record from the database from a given ID.
     *
     * @param targetNoteId the ID of UUID type, used as primary key.
     * @return Note object, containing the columns corresponding to the given targetNoteId
     * @throws SQLException Thrown due to database connection problem, or error in preparing statement.
     */
    public Note selectNoteByIdFromDatabase(UUID targetNoteId) throws SQLException {
        if (targetNoteId == null)
            return null;

        Note foundNote = null;

        if (this.noteCache.checkIfCacheContainsKey(targetNoteId)) { // if the note was recently put into the cache
            foundNote = this.noteCache.getFromCache(targetNoteId);
        } else { //Query the database.
            String nonformattedQuery = "SELECT - FROM note WHERE id=?";
            String[] formatAttributes = {"title,text_body"};

            this.constructStatement(nonformattedQuery, formatAttributes);

            Object[] queryValues = {targetNoteId};
            int[] columnIndices = {1};

            this.queryInformation.setRecordAttributes(queryValues);
            this.queryInformation.setAttributeSqlColumnIndices(columnIndices);

            //Execute Query:
            try {
                this.executeQuery();
            } catch (SQLException e) {
                throw e;
            }

            ResultSet rs = this.getResultSet();

            //Check if a note with corresponding id existed:
            if (rs.getFetchSize() > 0) {
                //Put together a note object from the results:
                String title = rs.getString(2);
                String textBody = rs.getString(3);

                foundNote = new Note(title, textBody, targetNoteId);
                this.noteCache.putIntoCache(targetNoteId, foundNote);
            }
            this.resetQueryAttributes(); // reset the queryInformation object
            this.closeAllDatabaseObjects();
        }
        return foundNote;
    }

    public boolean deleteNoteByIdFromDatabase(UUID targetNoteId) throws SQLException {
        if (targetNoteId == null)
            return false;

        int rowsAffected;
        String query = "DELETE from note WHERE id=?";

        this.queryInformation.setUnFormattedSqlStatement(query);
        this.queryInformation.setFormattedSqlStatement(query);

        Object[] queryValues = {targetNoteId};
        int[] columnIndices = {1};

        this.queryInformation.setAttributeSqlColumnIndices(columnIndices);
        this.queryInformation.setRecordAttributes(queryValues);

        try {
            rowsAffected = this.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
        if (this.noteCache.checkIfCacheContainsKey(targetNoteId)) {
            this.noteCache.removeFromCache(targetNoteId); // remove the entry from the cache completely.
        }

        try {
            this.closeAllDatabaseObjects();
        } catch (SQLException e) {
            throw e;
        }
        this.resetQueryAttributes(); // set query array fields to null
        return rowsAffected > 0;
    }

    /**
     * Returns a List of Note objects corresponding to all notes in the database.
     *
     * @return List of Note types.
     * @throws SQLException
     */
    public List<Note> retrieveAllNotes() throws SQLException {
        String query = "SELECT * FROM note";

        this.queryInformation.setFormattedSqlStatement(query); // no need to format
        this.queryInformation.setUnFormattedSqlStatement(query);

        try {
            this.executeQuery();
        } catch (SQLException e) {
            throw e;
        }
        ResultSet rs = this.getResultSet();
        List<Note> notes = new ArrayList<>();
        if (rs.getFetchSize() <= 0) {
            return null; // no entries returned.
        }

        while (rs.next()) {
            UUID id = rs.getObject(1, UUID.class);
            String title = rs.getString(2);
            String textBody = rs.getString(3);
            notes.add(new Note(title, textBody, id));
        }
        try {
            this.closeAllDatabaseObjects();
        } catch (SQLException e) {
            throw e;
        }
        return notes;

    }

}
