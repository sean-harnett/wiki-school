package com.wikischool.wikischool.main.utilities.DataStructures.LRUCache;


import com.wikischool.wikischool.main.utilities.DataStructures.FNVHashMap.FNVMap;
import com.wikischool.wikischool.main.utilities.DataStructures.FNVHashMap.HashMapFNV;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a Least Recently Used (LRU) cache.
 * The LRU cache determines what entries can be deleted based on how relevant they are.
 * Relevance is determined by how recently an entry was last used. Leaving the less relevant to be replaced.
 * Relevance is implicit in this implementation based on an entries position in a linked list.
 *
 * @author sean-harnett
 */
public class LRUCache<K, V> implements Cache<K, V> {

    public static final int defaultCapacity = 16;
    private final FNVMap<K, LruNode<K, V>> lruMap;
    private final LruNode<K, V> cacheHead = new LruNode();
    private final LruNode<K, V> cacheTail = new LruNode();
    private final int capacity;
    private int size;

    /**
     * Empty constructor. Initializes the cache to a default capacity equal to 16.
     */
    public LRUCache() {
        this(defaultCapacity);
    }

    /**
     * Constructor to initialize the cache to a specific capacity.
     *
     * @param givenCapacity capacity to assign the cache too.
     */
    public LRUCache(int givenCapacity) {

        this.capacity = givenCapacity;
        this.size = 0;
        this.cacheHead.next = this.cacheTail;
        this.cacheHead.previous = this.cacheTail;

        this.cacheTail.next = this.cacheHead;
        this.cacheTail.previous = this.cacheHead;

        this.lruMap = new HashMapFNV<K, LruNode<K, V>>(givenCapacity);

    }

    /**
     * Method to add an element into the cache.
     *
     * @param key   The key to use for this element in the cache.
     * @param value The object to store in the cache.
     * @throws IllegalArgumentException thrown when the key provided is null.
     * @throws IllegalStateException    Thrown when an item cannot be removed from the cache to make room for the new item.
     */
    @Override
    public void putIntoCache(K key, V value) throws IllegalArgumentException, IllegalStateException {
        if (key == null)
            throw new IllegalArgumentException("Key cannot be null");

        LruNode<K, V> cacheNode;

        if (this.lruMap.containsKey(key)) { // update the value
            cacheNode = this.lruMap.get(key);
            cacheNode.value = value;
            this.swapNodeToFront(cacheNode);
        } else { // it is a new value to add:
            cacheNode = new LruNode<K, V>(key, value, null, null);
            if (this.size == this.capacity) {
                try {
                    this.removeFromCache((K) this.cacheTail.previous.key); // remove the least recently used.
                } catch (IllegalStateException e) {
                    throw e;
                }
            }
            this.size++; // only increment element count when one is not removed.
            this.lruMap.put(key, cacheNode);
            addToFrontOfCache(cacheNode); // add new node to the front of list.
        }
    }

    /**
     * Method to obtain the value stored in the cache.
     *
     * @param key key corresponding to an element in the cache.
     * @return V value of the element stored.
     * @throws IllegalArgumentException thrown when key is null.
     * @throws IllegalStateException    thrown when the key is not in the cache.
     */
    @Override
    public V getFromCache(K key) throws IllegalArgumentException, IllegalStateException {
        if (key == null)
            throw new IllegalArgumentException("Key cannot be null");

        if (!this.lruMap.containsKey(key))
            throw new IllegalStateException("Cache does not contain provided key: " + key);

        LruNode<K, V> cacheNode = this.lruMap.get(key);

        this.swapNodeToFront(cacheNode);

        return cacheNode.value;
    }

    /**
     * Method that deletes an item from the cache.
     *
     * @param key The key corresponding to the element to delete.
     * @return boolean whether the element was deleted from the cache or not.
     */
    @Override
    public boolean removeFromCache(K key) throws IllegalArgumentException, IllegalStateException {

        if (key == null)
            throw new IllegalArgumentException("Key cannot be null");
        if (this.size <= 0)
            throw new IllegalStateException("Cache is empty, cannot remove a key");

        if (!this.checkIfCacheContainsKey(key)) {
            return false;
        }
        LruNode<K, V> cacheNode = this.lruMap.get(key);

        this.removeFromCacheList(cacheNode); //detach it from list.
        this.lruMap.remove(key); // delete from hashMap.


        this.size--;

        return true;
    }


    @Override
    public boolean checkIfCacheContainsKey(K key) {
        return this.lruMap.containsKey(key);
    }

    /**
     * Method that will remove a node from the list, and then insert it at the beginning therefore increasing it's priority.
     *
     * @param cacheNode node to swap.
     */
    private void swapNodeToFront(LruNode cacheNode) {
        this.addToFrontOfCache(this.removeFromCacheList(cacheNode)); // removes, then sets to front of list.
    }

    /**
     * Method removing a node from the node list.
     *
     * @param cacheNode node to remove.
     * @return the removed node.
     */
    private LruNode<K, V> removeFromCacheList(LruNode cacheNode) {

        cacheNode.previous.next = cacheNode.next;
        cacheNode.next.previous = cacheNode.previous;
        cacheNode.next = null;
        cacheNode.previous = null;

        return cacheNode;
    }

    /**
     * Method adding a node to the front of the list.
     *
     * @param cacheNode node to add.
     */
    private void addToFrontOfCache(LruNode cacheNode) {

        cacheNode.next = this.cacheHead.next;
        this.cacheHead.next.previous = cacheNode;
        this.cacheHead.next = cacheNode;
        cacheNode.previous = this.cacheHead;

    }

    public LruNode<K, V> getCacheHead() {
        return this.cacheHead;
    }

    public List<V> getListOfCacheValues() {

        List<V> values = new ArrayList<V>(this.size);
        LruNode<K, V> list = this.cacheHead.next;

        while (list.value != null) {
            values.add(list.value);
            list = list.next;
        }

        return values;
    }

    private class LruNode<Key, Value> {

        protected Key key;
        protected Value value;
        protected LruNode next;
        protected LruNode previous;

        public LruNode(Key key, Value value, LruNode next, LruNode previous) {
            this.key = key;
            this.value = value;
        }

        public LruNode() {
        }
    }

}
