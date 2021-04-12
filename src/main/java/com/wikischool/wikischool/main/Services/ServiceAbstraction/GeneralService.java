package com.wikischool.wikischool.main.Services.ServiceAbstraction;

import com.wikischool.wikischool.main.ConnectionObjects.Queries.QueryFormatter;
import com.wikischool.wikischool.main.ConnectionObjects.Queries.SqlQueryExecutor;
import com.wikischool.wikischool.main.ConnectionObjects.Queries.SqlQueryInformation;
import com.wikischool.wikischool.main.utilities.StringFormatting.StatementFormatter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.isNull;

/**
 * Class used to handle a database queries made by services.
 *
 * @author sean-harnett
 * @see SqlQueryExecutor
 * @see QueryFormatter
 * @see SqlQueryInformation
 */
public abstract class GeneralService {


    private final QueryFormatter queryFormatter;
    private final SqlQueryExecutor queryExecutor;
    protected SqlQueryInformation<UUID> queryInformation;
    protected String delimiter;

    public GeneralService(StatementFormatter formatterType, SqlQueryExecutor executor, String delimiter) {
        this.delimiter = delimiter;
        this.queryFormatter = new QueryFormatter(formatterType);
        this.queryExecutor = executor;
    }

    public GeneralService(QueryFormatter queryFormatter, SqlQueryExecutor executor, String delimiter) {
        this.delimiter = delimiter;
        this.queryFormatter = queryFormatter;
        this.queryExecutor = executor;
    }

    protected void readProperties() {
        this.queryExecutor.readProperties();
    }

    protected void constructStatement(String statement, String[] attributes) {
        this.queryFormatter.constructStatement(attributes, statement, this.delimiter, this.queryInformation);
    }

    public ResultSet getResultSet() {
        return this.queryExecutor.getResultSet();
    }

    protected void executeQuery() throws SQLException {
        this.queryExecutor.executeQueryStatement(this.queryInformation);
    }

    protected int executeUpdate() throws SQLException {
        return this.queryExecutor.executeUpdateStatement(this.queryInformation);
    }

    public void closeAllDatabaseObjects() throws SQLException {
        //this.queryInformation = null;
        this.queryExecutor.closeAll();
    }

    protected void resetQueryObject() {
        this.queryInformation = null;
    }

    /**
     * Sets the queryInformation Object's recordAttributes, and columnIndices arrays to null.
     *
     * @see SqlQueryInformation
     */
    protected void resetQueryAttributes() {
        this.queryInformation.setRecordAttributes(null);
        this.queryInformation.setAttributeSqlColumnIndices(null);
    }

    protected SqlQueryExecutor getQueryExecutor() {
        return this.queryExecutor;
    }

    /**
     * Method createQueryIndexAttributes is used to find what fields need to be updated, and the location of their placeholder in an update query.
     * Also sets these values to corresponding arrays as parameters: potentialIndices, and potentialValues.
     *
     * @param potentialFields  fields to check for values of.
     * @param primaryKey       the primary key for the record.
     * @param potentialIndices where to store the corresponding column indices when found.
     * @param potentialValues  where to store the new column values to update with when found.
     * @param queryBuilder     the string builder used to concatenate the proper amount of fields of the form "id=?".
     * @param fieldStrings     the string values of fields to add to queryBuilder, if they exist in the potentialFields. Of the form "title=?".
     */
    protected int createQueryIndexAttributes(Object[] potentialFields, Object primaryKey, int[] potentialIndices, Object[] potentialValues, StringBuilder queryBuilder, String[] fieldStrings) {

        int length = potentialFields.length;

        int[] stringIndices = new int[fieldStrings.length];
        Arrays.fill(stringIndices, 0);
        int iy = 0; // index to assign the values to.
        int placeholderIndex = 1; // location of the placeholder in the query.
        boolean populated = false;

        for (int ix = 0; ix < length; ix++) {

            if (!Objects.isNull(potentialFields[ix])) {

                potentialValues[iy] = potentialFields[ix];
                potentialIndices[iy] = placeholderIndex;
                stringIndices[ix] = 1;

                iy++;
                placeholderIndex++;

                populated = true; // at least one field was found.

            }
        }

        potentialValues[iy] = primaryKey; // guarantee the primaryKey is at the last index
        potentialIndices[iy] = placeholderIndex;

        if (populated) {
            int iz = 0;
            while (stringIndices[iz] == 0) { // skipp the consecutive elements that won't need to be appended
                ++iz;
            }

            queryBuilder.append(fieldStrings[iz]);

            iz++;

            for (; iz < stringIndices.length; iz++) {
                if (stringIndices[iz] != 0) {
                    queryBuilder.append(("," + " " + fieldStrings[iz]));
                }
            }
            return iy; //number of fields found.
        }
        return -1; // no fields were found.
    }

    protected void setExecutionParameters(String query, Object[] attributes, int[] indices) {
        this.resetQueryAttributes();
        this.queryInformation.setUnFormattedSqlStatement(query);
        this.queryInformation.setRecordAttributes(attributes);
        this.queryInformation.setAttributeSqlColumnIndices(indices);
    }

    protected void checkNullArgument(Object obj, String msg) throws IllegalArgumentException {
        if (isNull(obj)) {
            throw new IllegalArgumentException(msg);
        }
    }
}