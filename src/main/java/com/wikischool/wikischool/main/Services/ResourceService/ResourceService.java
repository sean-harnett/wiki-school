package com.wikischool.wikischool.main.Services.ResourceService;


import com.wikischool.wikischool.main.Models.Types.WikiResource;
import com.wikischool.wikischool.main.ConnectionObjects.Queries.SqlQueryExecutor;
import com.wikischool.wikischool.main.ConnectionObjects.Queries.SqlQueryInformation;
import com.wikischool.wikischool.main.Services.ServiceAbstraction.GeneralService;
import com.wikischool.wikischool.main.utilities.StringFormatting.StringFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static java.util.Objects.isNull;

/**
 * @author sean-harnett
 */

@Service
public class ResourceService extends GeneralService {
    @Autowired
    public ResourceService(StringFormatter stringFormatter, SqlQueryExecutor executor) {

        super(stringFormatter, executor, "-");

        this.queryInformation = new SqlQueryInformation<UUID>();

        this.readProperties();

    }


    public boolean insertResourceLink(URL resourceLink) throws SQLException, IllegalArgumentException {

        checkNullArgument(resourceLink, "URL parameter cannot be null");

        String query = "INSERT INTO resources link VALUES (?)";

        this.setExecutionParameters(query, new Object[]{resourceLink}, new int[]{1});

        this.queryInformation.setFormattedSqlStatement(query);

        int rowsAffected;

        try {
            rowsAffected = this.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }

        return rowsAffected > 0;

    }


    public WikiResource selectTopicsById(UUID id) throws SQLException, IllegalArgumentException {

        checkNullArgument(id,"id parameter cannot be null");

        WikiResource foundResource = null;
        String query = "SELECT link FROM resources WHERE id=?";

        this.setExecutionParameters(query, new Object[]{id}, new int[]{1});

        this.queryInformation.setFormattedSqlStatement(query);

        try {

            this.executeQuery();

        } catch (SQLException e) {
            throw e;
        }
        ResultSet resultSet = this.getResultSet();

        if (resultSet.getFetchSize() > 0) {
            foundResource = new WikiResource(id, resultSet.getURL(1));
        }

        return foundResource;
    }


    public boolean deleteResourceById(UUID id) throws SQLException, IllegalArgumentException {

        checkNullArgument(id, "Id parameter cannot be null");

        String query = "DELETE * FROM topics WHERE id=?";

        this.resetQueryAttributes();
        this.setExecutionParameters(query, new Object[]{id}, new int[]{1});

        this.queryInformation.setFormattedSqlStatement(query);

        int affectedRows = 0;

        try {
            affectedRows = this.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }

        return affectedRows > 0;
    }


    public boolean updateResourceById(WikiResource resource) throws SQLException, IllegalArgumentException, IllegalStateException {

        checkNullArgument(resource, "WikiResource parameter cannot be null");

        if (isNull(resource.getId())) {
            throw new IllegalStateException("resource id cannot be null");
        }

        if (isNull(resource.getResourceLink())) {
            throw new IllegalStateException("method updateResourceById called on WikiResource Object with no update fields");
        }

        String query = "UPDATE resources SET link=? WHERE id=?";
        Object[] attributes = {resource.getId(), resource.getResourceLink()};
        int[] columnIndices = {2,1};

        this.setExecutionParameters(query,attributes,columnIndices);

        this.queryInformation.setFormattedSqlStatement(query);

        int rowsAffected = 0;

        try{
            rowsAffected = executeUpdate();
        }catch(SQLException e){
            throw e;
        }

        return rowsAffected > 0;
    }

}
