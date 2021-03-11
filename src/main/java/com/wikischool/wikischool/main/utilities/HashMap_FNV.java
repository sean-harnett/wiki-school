package com.wikischool.wikischool.main.utilities;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

// Maybe write a find node method instead of repeating myself..?
public class HashMap_FNV<Key, Value> { // 32 bit integer hash

    private final int capacity;
    private final int MAP_EMPTY = 0;
    private final int FNV_32_INIT = 0x811c9dc5;
    private final int FNV_32_PRIME = 0x01000193;
    private Bucket<Key, Value> map[];
    private int element_count;
    private HashSet<Key> keySet; //Contains the keys -> perhaps implement my own..?
    private Bucket<Key, Value> initial_target_parent;

    public HashMap_FNV(int given_capacity) {
        this.element_count = 0;
        this.capacity = given_capacity;
        this.map = new Bucket[given_capacity];
        this.initial_target_parent = null;
        Arrays.fill(map, new Bucket<Key, Value>(null, null, null, null)); //list of heads to buckets
        this.keySet = new HashSet<Key>(given_capacity);
    }

    public void put(Key key, Value value) {
        if (isMapFull()) {
            return;
        }
        Bucket<Key, Value> target = getTarget(key);
        if (target == null) {

            target = new Bucket<>(key, value, null, this.initial_target_parent);
            initial_target_parent.next = target;
            element_count++;
            keySet.add(key);
            return;
        }
        while (target.key != key) {
            if (target.next == null) {
                target.next = new Bucket<Key, Value>(key, value, null, target);
                element_count++;
                keySet.add(key);
                return;
            }
            target = target.next;
        }
        target.value = value; // Or update value
    }

    public Value get(Key key) {
        if (isMapEmpty()) {
            return null;
        }
        Bucket<Key, Value> target = getTarget(key);
        while (target != null && target.key != key) {
            target = target.next;
        }
        if (target == null) {
            return null;
        }
        return target.value;
    }

    public void remove(Key key) {
        if (isMapEmpty()) {
            return;
        }
        Bucket<Key, Value> target = getTarget(key);

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

    public boolean isMapFull() {
        if (this.element_count == capacity) {
            return true;
        }
        return false;
    }

    public boolean isMapEmpty() {
        if (this.element_count == MAP_EMPTY) {
            return true;
        }
        return false;
    }

    public boolean containsKey(UUID key) { // check if mapping for key, and then throway object
        return keySet.contains(key);
    }

    public Set<Key> keySet() {
        return this.keySet;
    }

    private Bucket getTarget(Key key) {
        int hash_key = hash(key.hashCode());
        this.initial_target_parent = map[hash_key];
        return this.initial_target_parent.next;
    }

    private int hashFNV32(byte[] k) {

        int key = FNV_32_INIT;
        final int length = k.length;

        for (int ix = 0; ix < length; ix++) {
            key ^= k[ix];
            key *= FNV_32_PRIME;
        }
        return key;
    }

    private int hash(int k) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(k);

        byte[] to_hash = buffer.array();

        int key = hashFNV32(to_hash);
        if (key < 0) { // if hash is negative : invert
            key = ~key;
        }
        return (key % capacity); //The new hash table key
    }

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


