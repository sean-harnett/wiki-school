package com.wikischool.wikischool.main.Services;

import com.wikischool.wikischool.main.ConnectionObjects.ConnectionAbstraction.DatabaseConnection;
import com.wikischool.wikischool.main.Queries.QueryFormatter;
import com.wikischool.wikischool.main.Queries.SqlQueryExecutor;
import com.wikischool.wikischool.main.Queries.SqlQueryInformation;
import com.wikischool.wikischool.main.Services.ServiceAbstraction.TableAttributes;
import com.wikischool.wikischool.main.utilities.StringFormatting.StatementFormatter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Class used to handle a database queries made by services.
 * @see TableAttributes
 * @see SqlQueryExecutor
 * @see QueryFormatter
 * @see SqlQueryInformation
 * @author sean-harnett
 *
 */
public abstract class GeneralService {

    private final SqlQueryExecutor queryExecutor;

    private final TableAttributes tableAttributes;

    private final QueryFormatter queryFormatter;

    private SqlQueryInformation<UUID> queryInformation;


    public GeneralService(TableAttributes tableAttributes, StatementFormatter formatterType) {
        this.queryExecutor = new SqlQueryExecutor();
        this.tableAttributes = tableAttributes;
        this.queryFormatter = new QueryFormatter(formatterType);
    }

    public GeneralService(TableAttributes tableAttributes, SqlQueryExecutor executor, QueryFormatter queryFormatter) {
        this.tableAttributes = tableAttributes;
        this.queryExecutor = executor;
        this.queryFormatter = queryFormatter;
    }

    public SqlQueryInformation<UUID> constructStatement(String statement, String[] attributes, String delimiter) {
        this.queryInformation = this.queryFormatter.constructStatement(attributes, statement, delimiter);
        return this.queryInformation;
    }

    public ResultSet getResultSet(){
        return this.queryExecutor.getResultSet();
    }

    public void executeQuery() throws SQLException {
        this.queryExecutor.executeQueryStatement(this.queryInformation);
    }

    public int executeUpdate() throws SQLException {
        return this.queryExecutor.executeUpdateStatement(this.queryInformation);
    }

    public void setExecutorDatabaseConnectionType(DatabaseConnection databaseConnectionType) {
        this.queryExecutor.setDatabaseConnection(databaseConnectionType);
    }

    public SqlQueryExecutor getQueryExecutor(){
        return this.queryExecutor;
    }

}