package com.wikischool.wikischool.main.utilities;

import java.util.UUID;

/**
 * interface to specify the LRU cache base functionality.
 * @author sean-harnett
 */
public interface LRUCacheInterface {

    public Object get(UUID key);
    public void put(UUID key, Object value); //Contains the full name of the student or instructor
    public void cacheDelete(UUID key);

}
