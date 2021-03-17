package com.wikischool.wikischool.main.utilities;
/**
 * Helper class to store attributes and insertion indices:
 *
 * @author sean-harnett
 */

public class FormatterNode {


        private int insertionIndex;
        private String attribute;


        public FormatterNode() {
        }

        public FormatterNode(int insertionIndex, String attribute) {
            this.insertionIndex = insertionIndex;
            this.attribute = attribute;
        }

        public int getInsertionIndex() {
            return insertionIndex;
        }

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
