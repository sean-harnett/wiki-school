package com.wikischool.wikischool.main.Queries;

/**
 * Class to store values for an SQL query.
 * K is the element type to use as a primary key -> UUID
 * @author sean-harnett
 */
public class SqlQueryInformation<K> {

    private K primaryKey; // of table record being targeted.
    private String tableName;
    private String[] recordAttributes;
    private String sqlStatement;


    public SqlQueryInformation(){}

    public SqlQueryInformation(K primaryKey, String tableName, String[] attributes) {
        this.primaryKey = primaryKey;
        this.tableName = tableName;
        this.recordAttributes = attributes;
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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String[] getRecordAttributes() {
        return recordAttributes;
    }

    public void setRecordAttributes(String[] attributes) {
        this.recordAttributes = attributes;
    }
}
