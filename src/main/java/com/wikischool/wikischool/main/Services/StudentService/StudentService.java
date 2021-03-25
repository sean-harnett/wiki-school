package com.wikischool.wikischool.main.Services.StudentService;

import com.wikischool.wikischool.main.Models.Interfaces.Standard_Service_Operations;
import com.wikischool.wikischool.main.Models.People.Student;
import com.wikischool.wikischool.main.Models.Types.Course;
import com.wikischool.wikischool.main.Queries.SqlQueryExecutor;
import com.wikischool.wikischool.main.Services.GeneralService;
import com.wikischool.wikischool.main.utilities.DataStructures.FNVHashMap.HashMap_FNV;
import com.wikischool.wikischool.main.utilities.DataStructures.LRUCache.LRUCache;
import com.wikischool.wikischool.main.utilities.Constants.SizeConstants;
import com.wikischool.wikischool.main.utilities.EnumIndices.StudentAttributeIndex;
import com.wikischool.wikischool.main.utilities.StringFormatting.StringFormatter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Class to work with student objects and their functionality
 * TODO: implement the rest of the student methods
 * @author sean-harnett
 */

@Service
public class StudentService extends GeneralService {

    private final LRUCache<UUID, Student> studentCache = new LRUCache<>(SizeConstants.DEFAULT_CACHE_LENGTH);

    public StudentService(){
        super( new StudentTableAttributes(), new StringFormatter());
    }

    /**
     * What methods are required:
     *  1) Create new Student object.
     *  2) insert student into database using GeneralService.
     *          @see GeneralService
     *  3) select one or more student,
     *  4) delete students
     *  5) add courses to student's course list
     *
     */







}
