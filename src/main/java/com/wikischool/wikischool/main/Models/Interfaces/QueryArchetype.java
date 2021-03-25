package com.wikischool.wikischool.main.Models.Interfaces;

import com.wikischool.wikischool.main.Queries.SqlQueryInformation;

import java.sql.SQLException;

public interface QueryArchetype<K> {

    public int executeUpdateStatement(SqlQueryInformation<K> queryInformation) throws SQLException;

    public void executeQueryStatement(SqlQueryInformation<K> queryInformation) throws SQLException;

}
