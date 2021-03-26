package com.wikischool.wikischool.main.utilities.DataStructures.LRUCache;

/**
 * interface to specify the LRU cache base functionality.
 * @author sean-harnett
 */
public interface LRUCacheInterface<K, V> {

     V get(K key);
     void put(K key, V value); //Contains the full name of the student or instructor
     void cacheDelete(K key);

}
