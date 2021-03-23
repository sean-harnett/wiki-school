package com.wikischool.wikischool.main.utilities.DataStructures.FNVHashMap;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Hash Map implementation. Keys are hashed using Fowler-NOLL-Vo hash function
 * Collisions of hashed keys are resolved with separate chaining.
 *
 * @author sean-harnett
 *
 */

public class HashMap_FNV<K, V> { // 32 bit integer hash

    /*Both FNV_32 constants are standard FNV_32 values*/
    // The base hash value to start with:
    private final int FNV_32_OFFSET = 0x811c9dc5; // Initial FNV 32 bit hash offset
    // Prime Number provides better hash distribution:
    private final int FNV_32_PRIME = 0x01000193; //Standard FNV_Prime number for 32 bit hash


    private final int capacity;
    private final int MAP_EMPTY = 0;
    private int element_count;

    private HashSet<K> keySet; //Contains map keys
    private Bucket<K, V> map[];

    //
    /**
     * Root node periodically set to a map index.
     * References the list of values stored at that index, but it itself contains no value.
     */
    private Bucket<K, V> entry_root;

    /**
     * Main constructor.
     * @param given_capacity int - size to initialize the map for
     */
    public HashMap_FNV(int given_capacity) {
        this.element_count = 0;
        this.capacity = given_capacity;
        this.map = new Bucket[given_capacity];
        this.entry_root = null;
        Arrays.fill(map, new Bucket<K, V>(null, null, null, null)); // initialize root buckets
        this.keySet = new HashSet<K>(given_capacity);
    }

    /**
     *
     *
     *  Find a suitable location to add a new entry, or update an old one.
     *
     * @param key Identifier for the map entry
     * @param value Object stored in the map
     */
    public void put(K key, V value) {
        if (isMapFull()) {
            return;
        }
        Bucket<K, V> target = getTarget(key);
        if (target == null) {

            target = new Bucket<>(key, value, null, this.entry_root);
            entry_root.next = target;
            element_count++;
            keySet.add(key);
            return;
        }
        while (target.key != key) {
            if (target.next == null) {
                target.next = new Bucket<K, V>(key, value, null, target);
                element_count++;
                keySet.add(key);
                return;
            }
            target = target.next;
        }
        target.value = value; // Or update value
    }

    /**
     *
     *  Find an entry in the map, and return the value otherwise if no entry exists return null.
     * @param key Identifier for the map entry
     * @return Value object contained int the map for the corresponding key
     */

    public V get(K key) {
        if (isMapEmpty()) {
            return null;
        }
        Bucket<K, V> target = getTarget(key);
        while (target != null && target.key != key) {
            target = target.next;
        }
        if (target == null) {
            return null;
        }
        return target.value;
    }

    /**
     * Remove an entry entirely from the map (and from the keySet), if the entry does not exist do nothing.
     * @param key Identifier for the map entry
     */

    public void remove(K key) {
        if (isMapEmpty()) {
            return;
        }
        Bucket<K, V> target = getTarget(key);

        if (target != null) {
            while (target.key != key) {
                if (target.next == null) {
                    return;
                }
                target = target.next;
            }
            if (target.next != null) { //if node surrounded
                target.next.prev = target.prev;
            }
            this.keySet.remove(key);
            target.prev.next = target.next;
            target = null; // explicit delete
        }
    }

    /**
     * checks if the map if full
     * @return boolean
     */
    public boolean isMapFull() {
        if (this.element_count == capacity) {
            return true;
        }
        return false;
    }

    /**
     *  checks if the map is empty
     * @return boolean
     */
    public boolean isMapEmpty() {
        if (this.element_count == MAP_EMPTY) {
            return true;
        }
        return false;
    }

    /**
     * Checks if there is a mapping corresponding to a key.
     * @param key key to check for
     * @return boolean - whether a mapping exists for the given key
     */
    public boolean containsKey(K key) { // check if mapping for key, and then throw-away object
        return keySet.contains(key);
    }

    /**
     *  returns the current set of keys stored in the map
     * @return this.keySet
     */
    public Set<K> keySet() {
        return this.keySet;
    }

    /**
     * Find the index of the map where the entry will be, and set the root node to be the bucket at that index.
     * The root does not hold values, but points to the head of a list of values.
     * @param key
     * @return Bucket type, corresponding to the head of the list of buckets where values are stored.
     */
    private Bucket getTarget(K key) {
        int hash_key = hash(key.hashCode());
        this.entry_root = map[hash_key];
        return this.entry_root.next;
    }

    /**
     * Hash Operations performed on a byte array to return a key corresponding to an index in the hash map.
     *  Hash Procedure:
     *      Initially the key is set to a constant prime number. This is a standard number used for 32-bit FNV hash.
     *      Then, for the number of bytes in byte array k, the current key is XOR'ed against the current byte.
     *      k is set to this value, and then multiplied by another prime number standard in 32-bit FNV hashes.
     *      This continues for the length of k[];
     * @param k byte array used against a new int key.
     * @return int key -> index in the map to now store the new entry.
     */

    private int hashFNV32(byte[] k) {

        int key = FNV_32_OFFSET;
        final int length = k.length;

        for (int ix = 0; ix < length; ix++) {
            key ^= k[ix];
            key *= FNV_32_PRIME;
        }
        if (key < 0) { // if hash is negative : invert
            key = ~key;
        }
        return (key % capacity);
    }

    /**
     * Method called to hash an integer, returning a new index. this is the main method called to find a hash key.
     * The method will turn k into a byte array, and hash using FNV algorithm.
     * @param k integer value used to find hash
     * @return key, an integer that corresponds to an index in the map[]
     */

    private int hash(int k) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(k);

        byte[] to_hash = buffer.array();

        int key = hashFNV32(to_hash);

        return key; //The new hash table key
    }

    /**
     * Bucket Class is where map entries are stored.
     * @param <K> // un-hashed key of the same type as outer class Key
     * @param <V> // generic value also the same type as outer class Value
     * @author sean-harnett
     */
    class Bucket<K, V> {
        public K key;
        public V value;
        public Bucket<K, V> next;
        public Bucket prev;

        public Bucket(K key, V value, Bucket next, Bucket prev) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.prev = prev;

        }
    }
}


