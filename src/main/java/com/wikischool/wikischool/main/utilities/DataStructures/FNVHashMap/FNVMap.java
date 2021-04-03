package com.wikischool.wikischool.main.utilities.DataStructures.FNVHashMap;

import java.util.Set;

public interface FNVMap<K,V> {

    /**
     * Put method, adds a new item to the map, or updates existing.
     * @param key
     * @param value
     */
    void put(K key, V value);

    /**
     * Get method, returns the value corresponding to key k.
     * @param key
     * @return the object stored at k, of type V.
     */
    V get(K key);

    boolean remove(K key);

    boolean containsKey(K key);

    Set<K> getKeySet();

    /**
     * Size method, returns the current number of elements in the map.
     * @return integer.
     */
    int size();

    /**
     * Map item interface
     * @param <K>
     * @param <V>
     */
    interface MapItem<K,V>{

    }


}
