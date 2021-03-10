package com.wikischool.wikischool.main.utilities;

import java.util.UUID;

public class LRUCache implements LRUCacheInterface { //can change to a linked hashmap, would remove most of this code.

    // double pointers (keep empty):
    private final LRUNode head = new LRUNode();
    private final LRUNode tail = new LRUNode();
    private HashMap_FNV hash_map;
    private int capacity;
    private int elements;


    public LRUCache(int given_capacity) {
        hash_map = new HashMap_FNV(given_capacity);
        capacity = given_capacity;
        elements = 0;
        head.next = tail;
        tail.previous = head;
    }

    private void add_to_front(LRUNode target_node) { // add node to the front of the list
        LRUNode temp;
        temp = head.next;

        target_node.next = temp;
        temp.previous = target_node;

        head.next = target_node;
        target_node.previous = head;
    }

    private void remove(LRUNode target_node) { //remove node from list
        target_node.previous.next = target_node.next;
        target_node.next.previous = target_node.previous;
    }

    @Override
    public Object get(UUID key) { //Get from the cache
        System.out.println("GET FROM CACHE");
        boolean check = hash_map.containsKey(key);

        if (!check) {
            return null; //key does not exist
        }
        LRUNode target_node = (LRUNode)hash_map.get(key);
        // Reorder list to increment most recently used:
        remove(target_node);
        add_to_front(target_node);

        return target_node.getValue(); //Cast to target type
    }

    @Override
    public void put(UUID key, Object value) {
        LRUNode update_node = (LRUNode)hash_map.get(key);
        System.out.println("PUT INTO CACHE");
        if (update_node != null) { //Update the node
            remove(update_node);
            add_to_front(update_node);
            update_node.setValue(value);
            return;
        }

        if (elements == capacity) { //Set the new tail and remove Node from hash
            LRUNode temp;
            temp = tail.previous;
            remove(temp);
            hash_map.remove(temp.getKey()); // remove the Node by UUID key
            elements--;
        }
        //Add to front of list, and add to hash:
        LRUNode new_node = new LRUNode(key, value);
        add_to_front(new_node);
        hash_map.put(key, new_node);
        elements++;
    }

    public void cacheDelete(UUID key){ //Delete entry from cache
        LRUNode target_node = (LRUNode)hash_map.get(key);
        if(target_node == null){
            return;
        }
        hash_map.remove(key);
        remove(target_node);
    }

    public boolean checkCache(UUID id ){ //Check if something in the cache
       return (this.hash_map.containsKey(id));
    }
    public HashMap_FNV getMap(){return this.hash_map;}

}
