package com.wikischool.wikischool.main.utilities.StringFormatting;

/**
 * Interface Specifying the proper implementation of a Formatter.
 * For an example:
 * @see StringFormatter
 * @author sean-harnett
 */
public interface StatementFormatter {


    public String constructNewString();

    public void addNewAttribute(String attribute, int insertionIndex);

    public void setSource(String newSource);

    public String getSource();


}
