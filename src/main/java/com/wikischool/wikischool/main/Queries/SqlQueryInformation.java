package com.wikischool.wikischool.main.Queries;

/**
 * Class to store values for an SQL query.
 * K is the element type to use as a primary key (example: UUID).
 *
 * @author sean-harnett
 */
public class SqlQueryInformation<K> {

    private K primaryKey;

    private String sqlStatement; // should be ready for table formatting

    private String formattedSqlStatement; // Used to prepare the statement.

    private String statementType;

    private Object[] recordAttributes;

    private String[] formatValues; // stores values used to format the sql statement.

    private int[] attributeSqlColumnIndices; //Store the sql table column indices


    public SqlQueryInformation() {}

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

    public String getSqlStatement() {
        return sqlStatement;
    }

    public void setSqlStatement(String sqlStatement) {
        this.sqlStatement = sqlStatement;
    }

    public K getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(K primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Object[] getRecordAttributes() {
        return recordAttributes;
    }

    public void setRecordAttributes(Object[] attributes) {
        this.recordAttributes = attributes;
    }

    public String getStatementType() {
        return this.statementType;
    }

    public void setStatementType(String type) {
        this.statementType = type;
    }



    public int[] getAttributeSqlColumnIndices() {
        return attributeSqlColumnIndices;
    }

    public void setAttributeSqlColumnIndices(int[] attributeIndices) {
        this.attributeSqlColumnIndices = attributeIndices;
    }

    public String getFormattedSqlStatement() {
        return formattedSqlStatement;
    }

    public void setFormattedSqlStatement(String formattedSqlStatement) {
        this.formattedSqlStatement = formattedSqlStatement;
    }

    public void setFormatValues(String[] formatValues){
        this.formatValues = formatValues;
    }


}
