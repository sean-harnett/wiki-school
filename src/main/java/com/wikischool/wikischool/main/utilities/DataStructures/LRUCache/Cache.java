package com.wikischool.wikischool.main.utilities.DataStructures.LRUCache;

/**
 * interface to specify the LRU cache base functionality.
 * @author sean-harnett
 */
public interface Cache<K, V> {

     void putIntoCache(K key, V value);

     V getFromCache(K key);

     boolean removeFromCache(K key);

     boolean checkIfCacheContainsKey(K key);


}
