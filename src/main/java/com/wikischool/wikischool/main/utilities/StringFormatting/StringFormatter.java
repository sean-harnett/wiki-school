package com.wikischool.wikischool.main.utilities.StringFormatting;


import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Inserts variable number of String attributes into a source String through corresponding indexes.
 *
 * @author sean-harnett
 */
@Component
public class StringFormatter implements StatementFormatter {

    private final ArrayList<Formatter_Node> formattingAttributes = new ArrayList<>();
    private String source;
    private boolean clearNodes;

    /**
     * Empty Constructor.
     */
    public StringFormatter() {
    }

    /**
     * Add new attributes to a string.
     * **This method also calculates the new location of each attribute as it inserts them into the string.**
     * Used in this project to change SQL queries, and allow for dynamic addition of table names, and variable number of columns.
     * Not safe for client provided Strings. This method is only safe when the values are determined server side.
     *
     * @return A new String with attributes added.
     */
    @Override
    public String constructNewString() {

        int attributesLength = this.formattingAttributes.size();

        StringBuilder builder = new StringBuilder(source);

        Formatter_Node currentAttributeNode;

        int addedLength = 0;
        String currentAttribute;

        for (int ix = 0; ix < attributesLength; ix++) {
            currentAttributeNode = this.formattingAttributes.get(ix);

            currentAttribute =  currentAttributeNode.getAttribute();

            builder.insert((currentAttributeNode.getInsertionIndex() + addedLength), currentAttribute );

            addedLength += (currentAttribute.length() -1); // subtract one for each delimiter that was originally in the string, because all the characters have shifted left by one.
        }

        this.formattingAttributes.clear();


        return builder.toString();

    }

    /**
     * Helper function to add a new node to the list, without outside classes needing to know about formatterNodes.
     * @param attribute The string to insert
     * @param insertionIndex The integer, corresponding to where to insert attribute.
     */
    @Override
    public void addNewAttribute(String attribute, int insertionIndex) {
        this.formattingAttributes.add(new Formatter_Node(insertionIndex, attribute));
    }

    /**
     * Helper function to add a new node to the list, without outside classes needing to know about formatterNodes.
     * @param attribute The string to insert
     * @param insertionIndex The integer, corresponding to where to insert attribute.
     */
    public void insertNewFormatterNode(String attribute, int insertionIndex) {
        this.formattingAttributes.add(new Formatter_Node(insertionIndex, attribute));
    }

    @Override
    public String getSource() {
        return source;
    }

    /**
     * Set the String that will have attributes inserted into.
     * @param source String to insert attributes into.
     */
    @Override
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Private Class to hold an attribute, and the location within StringFormatter.source to insert it.
     * @author sean-harnett
     */
    private class Formatter_Node {

        private int insertionIndex;
        private String attribute;

        public Formatter_Node(int insertionIndex, String attribute) {
            this.insertionIndex = insertionIndex;
            this.attribute = attribute;
        }

        public int getInsertionIndex() { return insertionIndex;  }

        public void setInsertionIndex(int insertionIndex) {
            this.insertionIndex = insertionIndex;
        }

        public String getAttribute() {
            return attribute;
        }

        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }
    }

}
