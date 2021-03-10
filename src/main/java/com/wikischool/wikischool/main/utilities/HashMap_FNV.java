package com.wikischool.wikischool.main.utilities;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

// Maybe write a find node method instead of repeating myself..?
public class HashMap_FNV { // 32 bit integer hash

    private final int SIZE;
    private final int MAP_EMPTY = 0;
    private final int FNV_32_INIT = 0x811c9dc5;
    private final int FNV_32_PRIME = 0x01000193;
    private final Bucket[] map;
    private int element_count;
    private HashSet<UUID> keySet; //Contains the keys -> perhaps implement my own..?
    private Bucket initial_target_parent;

    public HashMap_FNV(int given_capacity) {
        this.element_count = 0;
        this.SIZE = given_capacity;
        this.map = new Bucket[SIZE];
        this.initial_target_parent = null;
        Arrays.fill(map, new Bucket(null, null, null, null)); //list of heads to buckets
        this.keySet = new HashSet<UUID>(given_capacity);
    }

    public void put(UUID key, Object value) {
        if (isMapFull()) {
            return;
        } // map is full
        Bucket target = getTarget(key);
        //above two lines can be extracted
        if (target == null) {
            target = new Bucket(key, value, null, this.initial_target_parent);
            this.initial_target_parent.next = target;
            this.element_count++; //increment elements
            this.keySet.add(key);
            return;
        }

        while (target.key != key) {
            if (target.next == null) {
                target.next = new Bucket(key, value, null, target);
                this.element_count++; //increment elements
                this.keySet.add(key);
                return;
            }
            target = target.next;
        }
        target.value = value; //Update
    }

    public Object get(UUID key) {
        if (isMapEmpty()) {
            return null;
        }
        Bucket target = getTarget(key);
        if (target == null) {
            return null; // maybe something else? -> or return custom boolean obj
        }
        while (target != null && target.key != key) {
            target = target.next;
        }
        if (target == null) {
            return null;
        }
        return target.value; // then cast to something
    }

    public void remove(UUID key) {
        if (isMapEmpty()) {
            return;
        }
        Bucket target = getTarget(key);

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
        if (this.element_count == SIZE) {
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

    public Set<UUID> keySet() {

        return this.keySet;

    }

    private Bucket getTarget(UUID key) {
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
        if (key < 0) {
            key = ~key;
        }
        return (key % SIZE); //The new hash table key
    }

    class Bucket {
        public UUID key;
        public Object value;
        public Bucket next;
        public Bucket prev;

        public Bucket(UUID key, Object value, Bucket next, Bucket prev) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.prev = prev;

        }
    }
}


