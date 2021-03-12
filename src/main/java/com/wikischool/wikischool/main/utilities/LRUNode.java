package com.wikischool.wikischool.main.utilities;

import java.util.UUID;

/**
 * Node used in the LRU cache implementation.
 * The only methods are getters and setters for class members.
 * @author sean-harnett
 */
public class LRUNode {


    public LRUNode previous;
    public LRUNode next;
    private UUID key;
    private Object value;

    public LRUNode(UUID key, Object value) {
        this.key = key;
        this.value = value;
    }

    public LRUNode() {
        previous = null;
        next = null;
        key = null;
        value = null;
    }

    public UUID getKey() {
        return key;
    }

    public void setKey(UUID key) {
        this.key = key;
    }

    public LRUNode getPrevious() {
        return previous;
    }

    public void setPrevious(LRUNode previous) {
        this.previous = previous;
    }

    public LRUNode getNext() {
        return next;
    }

    public void setNext(LRUNode next) {
        this.next = next;
    }

    public Object getValue() { return value; }

    public void setValue(Object value) {
        this.value = value;
    }

}
