package com.wikischool.wikischool.main.utilities.StringFormatting;

/**
 * Interface Specifying the proper implementation of a Formatter.
 * For an example:
 * @see StringFormatter
 * @author sean-harnett
 */
public interface StatementFormatter {


     String constructNewString();

     void addNewAttribute(String attribute, int insertionIndex);

     void setSource(String newSource);

     String getSource();


}
