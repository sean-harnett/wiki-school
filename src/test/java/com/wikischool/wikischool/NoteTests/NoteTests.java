package com.wikischool.wikischool.NoteTests;

import com.wikischool.wikischool.WikiTestConstants;
import com.wikischool.wikischool.main.Models.Types.Note;
import com.wikischool.wikischool.main.Services.NoteService.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.UUID;

//TODO: write the rest of NoteService tests.
@SpringBootTest
public class NoteTests {


    @Test
    public void noteUpdateTest(@Autowired NoteService service) {
        Note note = new Note("TEST", "textBody", UUID.fromString(WikiTestConstants.TEST_NOTE_ID));

        try {
            service.updateNoteById(note);
        } catch (SQLException e) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println();

        note = null;

        note = new Note(null, "textBody", UUID.fromString(WikiTestConstants.TEST_NOTE_ID));

        try {
            service.updateNoteById(note);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
