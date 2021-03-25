package com.wikischool.wikischool.main.Services.StudentService;

import com.wikischool.wikischool.main.Services.ServiceAbstraction.TableAttributes;

public class StudentTableAttributes implements TableAttributes {

    private final int columnCount = 3;
    private final String[] columns = {"id","first_name","last_name"};
    private final String tableName = "student";

    public StudentTableAttributes() {
    }


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
