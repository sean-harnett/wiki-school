package com.wikischool.wikischool.main.Services;

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
 * TODO: setup the QueryFormatter to be autowired.
 * @author sean-harnett
 *
 */
public abstract class GeneralService {


    private SqlQueryExecutor queryExecutor;



    private final QueryFormatter queryFormatter;

    protected SqlQueryInformation<UUID> queryInformation;
    protected String delimiter;

    public GeneralService( StatementFormatter formatterType, SqlQueryExecutor executor, String delimiter) {
        this.delimiter = delimiter;
        this.queryFormatter = new QueryFormatter(formatterType);
        this.queryExecutor = executor;
    }

    public GeneralService( QueryFormatter queryFormatter, SqlQueryExecutor executor, String delimiter) {
        this.delimiter = delimiter;
        this.queryFormatter = queryFormatter;
        this.queryExecutor = executor;
    }
    protected void readProperties(){
        this.queryExecutor.readProperties();
    }

    protected SqlQueryInformation<UUID> constructStatement(String statement, String[] attributes) {
        return this.queryFormatter.constructStatement(attributes, statement, this.delimiter);
    }

    public ResultSet getResultSet(){
        return this.queryExecutor.getResultSet();
    }

    protected void executeQuery() throws SQLException {
        this.queryExecutor.executeQueryStatement(this.queryInformation);
    }

    protected int executeUpdate() throws SQLException {
        return this.queryExecutor.executeUpdateStatement(this.queryInformation);
    }

    public void closeAllDatabaseObjects()throws SQLException{
        //this.queryInformation = null;
        this.queryExecutor.closeAll();
    }

    protected void resetQueryObject(){
        this.queryInformation = null;
    }
    protected void resetQueryAttributes(){
        this.queryInformation.setRecordAttributes(null);
        this.queryInformation.setAttributeSqlColumnIndices(null);
    }
    protected SqlQueryExecutor getQueryExecutor(){
        return this.queryExecutor;
    }

}