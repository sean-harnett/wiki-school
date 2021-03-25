package com.wikischool.wikischool.main.Services.NoteService;

import com.wikischool.wikischool.main.Services.GeneralService;
import com.wikischool.wikischool.main.Services.StudentService.StudentTableAttributes;

import com.wikischool.wikischool.main.utilities.StringFormatting.StringFormatter;


import java.time.LocalDate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * Service class to work with Note objects.
 * TODO: Write the rest of the methods, will be similar functionality to StudentService.
 * @see com.wikischool.wikischool.main.Services.StudentService.StudentService
 * @author sean-harnett
 */
public class NoteService extends GeneralService {

   public NoteService(){
       super( new StudentTableAttributes(), new StringFormatter() );
   }
}
