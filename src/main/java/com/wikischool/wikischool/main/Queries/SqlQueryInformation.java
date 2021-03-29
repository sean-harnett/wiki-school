package com.wikischool.wikischool.main.Queries;

/**
 * Class to store values for an SQL query.
 * K is the element type to use as a primary key (example: UUID).
 *
 * @author sean-harnett
 */
public class SqlQueryInformation<K> {

    private K primaryKey;

    private String unFormattedSqlStatement; // should be ready for table formatting

    private String formattedSqlStatement; // Used to prepare the statement.


    private Object[] recordAttributes;


    private int[] attributeSqlColumnIndices; //Store the sql table column indices


    public SqlQueryInformation() {
        this.recordAttributes = null;
        this.attributeSqlColumnIndices = null;
    }

    public SqlQueryInformation(K primaryKey, String tableName, String[] attributes) {
        this.primaryKey = primaryKey;
        this.recordAttributes = attributes;
    }

    /**
     * Checks whether the query has more attributes other than a primary key.
     *
     * @return boolean
     */
    public boolean hasRecordAttributes() {
        return recordAttributes != null;
    }

    public String getUnFormattedSqlStatement() { return this.unFormattedSqlStatement; }

    public void setUnFormattedSqlStatement(String sqlStatement) {
        this.unFormattedSqlStatement = sqlStatement;
    }

    public K getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(K primaryKey) { this.primaryKey = primaryKey; }

    public Object[] getRecordAttributes() {
        return recordAttributes;
    }

    public void setRecordAttributes(Object[] attributes) {
        this.recordAttributes = attributes;
    }

    public int[] getAttributeSqlColumnIndices() {
        return attributeSqlColumnIndices;
    }

    /**
     * Set the statements column indices, these are where in the prepared statement to replace placeholder '?' characters- parallel to this.recordAttributes.
     * @param attributeIndices integers
     */
    public void setAttributeSqlColumnIndices(int[] attributeIndices) {
        this.attributeSqlColumnIndices = attributeIndices;
    }

    public String getFormattedSqlStatement() {
        return formattedSqlStatement;
    }

    public void setFormattedSqlStatement(String formattedSqlStatement) {
        this.formattedSqlStatement = formattedSqlStatement;
    }

}
