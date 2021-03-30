package com.wikischool.wikischool.main.Queries;

import com.wikischool.wikischool.main.utilities.StringFormatting.StatementFormatter;

import java.util.UUID;

public class QueryFormatter {

    private String delimiter;
    private String baseStatement;
    private StatementFormatter queryFormatter;
    private SqlQueryInformation<UUID> queryInformation;

    public QueryFormatter() {
    }

    public QueryFormatter(StatementFormatter formatter) {
        this.queryFormatter = formatter;
    }

    /**
     * Construct a formatted sql statement, ready to be prepared for execution.
     * This creates a queryInformation object, with a populated formatted.
     * Called by more specific archetypal query-type methods.
     */
    private void ConstructFormattedStatement(int[] insertIndices, String[] attributes, int length) {

  //      SqlQueryInformation<UUID> information = new SqlQueryInformation<>();


        for (int ix = 0; ix < length; ix++) {

            this.queryFormatter.addNewAttribute(attributes[ix], insertIndices[ix]);
        }
        String strippedStatement = this.baseStatement.replaceAll(this.delimiter, "");

        this.queryFormatter.setSource(strippedStatement);

        this.queryInformation.setFormattedSqlStatement(this.queryFormatter.constructNewString());

        //this.queryInformation = information;

    }

    /**
     * Method called to construct an query that is ready to be prepared.
     * attributes.length must equal the amount of locations in the baseSqlStatement where the delimiter is used.
     *
     * @param attributes       The attributes to insert
     * @param baseSqlStatement The foundational statement from which the statement is constructed. ie. SELECT FROM WHERE
     * @param delimiter        the character(as a String) to split on, so that nothing will be inserted in unintended locations.
     */
    public void constructStatement(String[] attributes, String baseSqlStatement, String delimiter, SqlQueryInformation<UUID> queryInformation) {

        this.delimiter = delimiter;
        this.baseStatement = null;
        this.baseStatement = baseSqlStatement;
        this.queryInformation = queryInformation;


        int length = attributes.length;

        String[] locationToInsertAfter = baseSqlStatement.split(delimiter);

        int[] insertIndices = findInsertIndices(locationToInsertAfter, length);

        ConstructFormattedStatement(insertIndices, attributes, length);

        //return this.queryInformation;

    }

    private int[] findInsertIndices(String[] targets, int length) {
        int[] indices = new int[length];
        int previousIndex = 0;
        for (int ix = 0; ix < length; ix++) {

            indices[ix] = this.baseStatement.indexOf(targets[ix], previousIndex) + targets[ix].length();
            previousIndex = indices[ix];

        }

        return indices;
    }

    /**
     * Set the current base statement to use to format a query. The statement should have delimiter characters.
     * Delimiters will be used to insert attributes into.
     * This statement contains no SQL placeholders, table names, or columns.
     * For example: "UPDATE % SET % WHERE" where '%' is an example delimiter
     *
     * @param baseSqlStatement String
     */
    public void setBaseStatement(String baseSqlStatement) {
        this.baseStatement = baseSqlStatement;
    }


}
