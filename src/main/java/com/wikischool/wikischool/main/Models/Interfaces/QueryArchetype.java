package com.wikischool.wikischool.main.Models.Interfaces;

import com.wikischool.wikischool.main.Queries.SqlQueryInformation;

public interface QueryArchetype<K> {
    public void executeUpdateStatement(SqlQueryInformation<K> queryInformation);
    public void executeQueryStatement(SqlQueryInformation<K> queryInformation);

}
