package com.wikischool.wikischool.main.Services.NoteService;

import com.wikischool.wikischool.main.Services.ServiceAbstraction.TableAttributes;

public class NoteTableAttributes implements TableAttributes {


    private final int columnCount = 3;
    private final String[] columns = {"id","text_body"};
    private final String tableName = "note";


    public NoteTableAttributes() { }

    @Override
    public int getColumnCount() {
        return this.columnCount;
    }

    @Override
    public String[] getColumns() {
        return columns;
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }



}
