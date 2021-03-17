package com.wikischool.wikischool.main.utilities;


/**
 * Formats strings.
 *
 * @author sean-harnett
 */
public class StringFormatter {


    /**
     * Empty Constructor.
     */
    public StringFormatter() {
    }

    /**
     * Add new attributes to a string.
     *  **This method also calculates the new location of each attribute as it inserts them into the string.**
     * Used in this project to change SQL queries, and allow for dynamic addition of table names, and variable number of Update columns.
     * Not safe for client provided Strings. This method is only safe when the values are determined server side.
     * @see FormatterNode
     * @param source     String to create the new string to insert placeholders, and format with new attributes.
     * @param attributes The array of 'Formatter Nodes' -> these contain an index, and a String to insert said index.
     * @return A new String with attributes added.
     */
    public String constructNewString(String source, Object[] attributes) {

        FormatterNode[] formatterNodeAttributes = (FormatterNode[])attributes;
        StringBuilder builder = new StringBuilder(source);

        int attributesLength = attributes.length;
        FormatterNode currentAttribute;
        int addedLength = 0;
        for (int ix = 0; ix < attributesLength; ix++) {
            currentAttribute = formatterNodeAttributes[ix];
            builder.insert((currentAttribute.getInsertionIndex() + addedLength), currentAttribute.getAttribute());
            addedLength = currentAttribute.getAttribute().length();
        }
        return builder.toString();

    }

}
