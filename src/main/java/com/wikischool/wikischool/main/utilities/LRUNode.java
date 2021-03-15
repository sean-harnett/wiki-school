package com.wikischool.wikischool.main.utilities;

import java.util.UUID;

/**
 * Node used in the LRU cache implementation.
 * The only methods are getters and setters for class members.
 * @author sean-harnett
 */
public class LRUNode<K,V> {


    public LRUNode previous;
    public LRUNode next;
    private K key;
    private V value;

    public LRUNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public LRUNode() {
        previous = null;
        next = null;
        key = null;
        value = null;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
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

    public V getValue() { return value; }

    public void setValue(V value) {
        this.value = value;
    }

}
