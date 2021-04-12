package com.wikischool.wikischool.main.Services.TopicService;

import com.wikischool.wikischool.main.Models.Types.Topic;
import com.wikischool.wikischool.main.ConnectionObjects.Queries.SqlQueryExecutor;
import com.wikischool.wikischool.main.ConnectionObjects.Queries.SqlQueryInformation;
import com.wikischool.wikischool.main.Services.ServiceAbstraction.GeneralService;
import com.wikischool.wikischool.main.utilities.StringFormatting.StringFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static java.util.Objects.isNull;

/**
 * @author sean-harnett
 */
@Service
public class TopicService extends GeneralService {

    @Autowired
    public TopicService(StringFormatter stringFormatter, SqlQueryExecutor executor) {

        super(stringFormatter, executor, "-");

        this.queryInformation = new SqlQueryInformation<>();

        this.readProperties();
    }

    public boolean insert(Topic topic) throws SQLException, IllegalArgumentException {

        checkNullArgument(topic, "Topic parameter cannot be null");

        String nonformattedQuery = "INSERT INTO topics (-) VALUES (?)";

        String title = topic.getTitle();


        String[] formatAttributes = {"title"};

        this.constructStatement(nonformattedQuery, formatAttributes);


        Object[] queryValues = {title};
        int[] columnIndices = {1};

        queryInformation.setRecordAttributes(queryValues);
        queryInformation.setAttributeSqlColumnIndices(columnIndices);

        int rowsAffected;

        try {
            rowsAffected = this.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }

        return rowsAffected > 0;

    }

    public Topic selectTopicsById(UUID id) throws SQLException, IllegalArgumentException {

        checkNullArgument(id, "Id parameter cannot be null");

        Topic foundTopic = null;

        this.resetQueryAttributes();
        this.queryInformation.setFormattedSqlStatement("SELECT title FROM topics WHERE id=?");

        try {

            this.executeQuery();

        } catch (SQLException e) {
            throw e;
        }
        ResultSet resultSet = this.getResultSet();

        if (resultSet.getFetchSize() > 0) {
            foundTopic = new Topic(id, resultSet.getString(1));
        }

        return foundTopic;
    }

    public boolean deleteTopicById(UUID id) throws SQLException, IllegalArgumentException {

        checkNullArgument(id,"id parameter cannot be null");

        String query = "DELETE * FROM topics WHERE id=?";
        this.resetQueryAttributes();
        this.queryInformation.setUnFormattedSqlStatement(query);

        int affectedRows = 0;

        try {
            affectedRows = this.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }

        return affectedRows > 0;
    }

    public boolean updateTopicById(Topic topic)throws SQLException, IllegalArgumentException, IllegalStateException{

        this.checkNullArgument(topic, "topic parameter cannot be null");

        if(isNull(topic.getId())){
            throw new IllegalStateException("topic id cannot be null");
        }
        if(isNull(topic.getTitle())){
            throw new IllegalStateException("Method updateTopicById called with no fields to update");
        }
        int rowsAffected = 0;

        String query ="UPDATE topics SET title=? WHERE id=?";

        this.resetQueryAttributes();
        this.queryInformation.setUnFormattedSqlStatement(query);
        this.queryInformation.setFormattedSqlStatement(query);

        Object[] attributes = {topic.getTitle(), topic.getId()};
        int[] columnIndices = {1,2};
        this.queryInformation.setAttributeSqlColumnIndices(columnIndices);
        this.queryInformation.setRecordAttributes(attributes);

        try{
            rowsAffected = this.executeUpdate();

        }catch(SQLException e){
            throw e;
        }
    return rowsAffected > 0;

    }
}
