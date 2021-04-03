package com.wikischool.wikischool.main.utilities.DataStructures.FNVHashMap;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

/**
 * Hash Map implementation. Keys are hashed using Fowler-NOLL-Vo hash function
 * Collisions of hashed keys are resolved with separate chaining.
 *
 * @author sean-harnett
 */

public class HashMapFNV<K, V> implements FNVMap<K, V> { // 32 bit integer hash

    /* Both FNV_32 constants are standard FNV_32 values */

    // The base hash value to start with:
    private final int FNV_32_OFFSET = 0x811c9dc5; // Initial FNV 32 bit hash offset
    // Prime Number provides better hash distribution:
    private final int FNV_32_PRIME = 0x01000193; //Standard FNV_Prime number for 32 bit hash
    //Default initial length of array, if none provided to constructor.
    private final int defaultCapacity = 10;
    //Total elements the map can hold, before it needs to be resized.
    private final int capacity;
    private final Set<K> keySet;
    //Current number of elements in the HashMap.
    private int size;

    private FnvMapItem<K, V>[] map = null;

    public HashMapFNV(int givenCapacity) {
        this.size = 0;
        this.capacity = givenCapacity;
        this.createNewMap(givenCapacity);
        this.keySet = new HashSet<K>(givenCapacity);
    }


    public HashMapFNV() {
        this.size = 0;
        this.capacity = this.defaultCapacity;
        this.createNewMap(this.defaultCapacity);
        this.keySet = new HashSet<K>(defaultCapacity);
    }

    /**
     * Method called to put an item into the map.
     *
     * @param key   key to hash.
     * @param value - of type V, element to store at the index determined by hash.
     * @throws IllegalArgumentException thrown when the given key is null.
     * @throws IllegalStateException    thrown when the map is full.
     */
    @Override
    public void put(K key, V value) throws IllegalArgumentException, IllegalStateException {

        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int itemIndex = getKeyIndex(key); // get the index for this key.

        FnvMapItem<K, V> item = map[itemIndex];

        if (item == null) { // map does not contain an entry for this index, so a new one may be added:
            map[itemIndex] = new FnvMapItem<K, V>(key, value, null, null);
            this.keySet.add(key);
        } else {
            if (item.key == key) {
                item.value = value;
            }

            if (this.keySet.contains(key)) { // look for the key, and update.
                try {
                    FnvMapItem<K, V> itemToUpdate = this.seekItem(item, key); // find existing item.
                    itemToUpdate.value = value; // update.
                } catch (IllegalStateException e) {
                    throw e;
                }

            } else { // create a new item, and insert at the head of the list.

                if (this.mapIsFull()) // if the capacity is reached.
                    throw new IllegalStateException("Unable to put element: 'Map capacity reached' ");

                map[itemIndex] = new FnvMapItem<K, V>(key, value, item, null);
                item.previous = map[itemIndex];
                this.keySet.add(key);
            }
        }
        this.size++;
    }

    /**
     * Method used to get an item from the map.
     *
     * @param key key whose value we want to find
     * @return V - the value matching the given key.
     * @throws IllegalArgumentException thrown when the given key is null.
     * @throws IllegalStateException    thrown when the keySet does not contain a matching key, or if the map is empty.
     */
    @Override
    public V get(K key) throws IllegalArgumentException, IllegalStateException {

        if (key == null)
            throw new IllegalArgumentException("Key cannot be null");

        if (!this.keySet.contains(key))
            throw new IllegalStateException("Key does not exist in map");

        if (this.mapIsEmpty())
            throw new IllegalStateException("Map cannot be empty");

        int itemIndex = getKeyIndex(key);

        FnvMapItem<K, V> item = this.map[itemIndex];

        if (item.key.equals(key)) {
            return item.value;
        }
        item = this.map[itemIndex];
        //other wise traverse the list to find the item:
        try {
            FnvMapItem<K, V> foundItem = this.seekItem(item, key);
            return foundItem.value;
        } catch (IllegalStateException e) {
            throw e;
        }
    }

    /**
     * Method to delete an entry from the map by key.
     *
     * @param key key, whose node we want to remove.
     * @return boolean, of whether or not we have deleted an entry.
     * @throws IllegalArgumentException thrown when a null value is passed for the key.
     */
    @Override
    public boolean remove(K key) throws IllegalArgumentException {

        if (key == null)
            throw new IllegalArgumentException("Key cannot be null");

        if (!this.keySet.contains(key))
            return false;

        int itemIndex = getKeyIndex(key);

        FnvMapItem<K, V> item = this.map[itemIndex];
        if (item.key == key) { // remove item from list.
            this.map[itemIndex] = item.next;
            if (item.next != null) {
                item.next.previous = this.map[itemIndex];
            }
        } else {
            try { // find the actual node with the matching key

                FnvMapItem<K, V> foundItem = seekItem(item, key);

                // remove foundItem from the list
                if (foundItem.next != null) {
                    foundItem.previous.next = foundItem.next;
                    foundItem.next.previous = foundItem.previous;
                } else {
                    foundItem.previous.next = null;
                }
                //remove the foundItem's own connections:
                foundItem.next = null;
                foundItem.previous = null;

            } catch (IllegalStateException e) {
                return false;
            }
        }
        this.keySet.remove(key);
        this.size--;

        return true;
    }

    /**
     * Private method seeking an node with a matching key in a list of items.
     *
     * @param item item to start from.
     * @param key  key, whose item we want to find
     * @return the found node
     * @throws IllegalStateException if the list does not contain the key
     */
    private FnvMapItem<K, V> seekItem(FnvMapItem<K, V> item, K key) throws IllegalStateException {
        FnvMapItem<K, V> current = item.next;
        while (current != null && current.key != key) {
            System.out.println(current.key);
            current = current.next;
        }
        if (current == null) {
            throw new IllegalStateException("Method 'seekItem' failed: called on unreachable or deleted key");
        }

        return current;
    }

    /**
     * Method to check whether the map contains a specific key.
     *
     * @param key key to look for
     * @return boolean - true = map does contain the key, false = map does not contain the key.
     */
    @Override
    public boolean containsKey(K key) {
        return this.keySet.contains(key);
    }

    /**
     * Method to obtain the set of key currently in the map.
     *
     * @return Set object, of the keys.
     */
    @Override
    public final Set<K> getKeySet() {
        return this.keySet;
    }

    /**
     * @return the current amount of elements stored in the map.
     */
    @Override
    public int size() {
        return this.size;
    }

    @SuppressWarnings("unchecked")
    private void createNewMap(int newCapacity) {
        this.map = (FnvMapItem<K, V>[]) new FnvMapItem[newCapacity];
    }

    /**
     * method returning whether the map has reached it's current capacity.
     *
     * @return boolean - true = map is full, false = map is not full.
     */
    public boolean mapIsFull() {
        return this.size == this.capacity;
    }

    /**
     * method returning whether the map is empty.
     *
     * @return boolean - true = map is empty, false = map is not empty.
     */
    public boolean mapIsEmpty() {
        return this.size <= 0;
    }

    /**
     * Method that returns a new integer to store an item in the map.
     *
     * @param key of type K.
     * @return int corresponding to an element in this.map array.
     */
    private int getKeyIndex(K key) {
        return (hash(key) % this.capacity);
    }

    /**
     * Helper method hash, converts K key into a byte array, then returns the value of the FNV hash using the byte array.
     *
     * @param key K type object
     * @return integer corresponding to the map capacity.
     */
    private int hash(K key) {
        int hashCode = key.hashCode();

        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(hashCode);

        byte[] to_hash = buffer.array();

        return hashKey(to_hash) % this.capacity; //The new hash table key
    }


    /**
     * Hash Operations performed on a byte array to return a key corresponding to an index in the hash map.
     * Hash Procedure:
     * Initially the key is set to a constant prime number. This is a standard number used for 32-bit FNV hash.
     * Then, for the number of bytes in byte array k, the current key is XOR'ed against the current byte.
     * k is set to this value, and then multiplied by another prime number standard in 32-bit FNV hashes.
     * This continues for the length of k[];
     *
     * @param k byte array used against a new int key.
     * @return int key -> index in the map to now store the new entry.
     */
    private int hashKey(byte[] k) {

        int key = FNV_32_OFFSET;
        final int length = k.length;

        for (int ix = 0; ix < length; ix++) {
            key ^= k[ix];
            key *= FNV_32_PRIME;
        }
        if (key < 0) { // if hash is negative : invert
            key = ~key;
        }
        return key;
    }

    class FnvMapItem<Key, Value> implements MapItem<Key, Value> {

        protected Key key;
        protected Value value;
        FnvMapItem<Key, Value> next;
        FnvMapItem<Key, Value> previous;


        public FnvMapItem(Key key, Value value, FnvMapItem next, FnvMapItem previous) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }
}


