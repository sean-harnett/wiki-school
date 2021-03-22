package com.wikischool.wikischool.main.utilities.DataStructures;

import java.util.UUID;

/**
 * interface to specify the LRU cache base functionality.
 * @author sean-harnett
 */
public interface LRUCacheInterface<K, V> {

    public V get(K key);
    public void put(K key, V value); //Contains the full name of the student or instructor
    public void cacheDelete(K key);

}
