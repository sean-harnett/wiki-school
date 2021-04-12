package com.wikischool.wikischool.main.ConnectionObjects.Queries;

import java.sql.SQLException;

public interface QueryArchetype<K> {

     int executeUpdateStatement(SqlQueryInformation<K> queryInformation) throws SQLException;

     void executeQueryStatement(SqlQueryInformation<K> queryInformation) throws SQLException;

}
