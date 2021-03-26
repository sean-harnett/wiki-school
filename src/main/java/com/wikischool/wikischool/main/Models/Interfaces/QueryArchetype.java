package com.wikischool.wikischool.main.Models.Interfaces;

import com.wikischool.wikischool.main.Queries.SqlQueryInformation;

import java.sql.SQLException;

public interface QueryArchetype<K> {

     int executeUpdateStatement(SqlQueryInformation<K> queryInformation) throws SQLException;

     void executeQueryStatement(SqlQueryInformation<K> queryInformation) throws SQLException;

}
