package com.wikischool.wikischool.main.utilities.DataStructures;


/**
 * Implementation of a Least Recently Used (LRU) cache.
 * The LRU cache determines what entries can be deleted based on how relevant they are.
 * Relevance is determined by how recently an entry was last used. Leaving the less relevant to be replaced.
 * Relevance is implicit in this implementation based on an entries position in a linked list.
 *
 *
 * @author sean-harnett
 */

// TO DO: Implement generic FNV map: (This class does not work right now.)
public class LRUCache<K, V> implements LRUCacheInterface<K,V> {

    // head and tail are references to the list, they themselves have no values
    private final LRUNode head = new LRUNode();
    private final LRUNode tail = new LRUNode();
    private HashMap_FNV<K,LRUNode<K,V>> hash_map;
    private int capacity;
    private int elements;

    /**
     * Constructor to initialize to a specific capacity
     * @param given_capacity int
     */
    public LRUCache(int given_capacity) {
        hash_map = new HashMap_FNV<>(given_capacity);
        capacity = given_capacity;
        elements = 0;
        head.next = tail;
        tail.previous = head;
    }

    /**
     * add the node to the front of the list, increasing it's relevance
     * @param target_node node to move in hte list.
     */

    private void add_to_front(LRUNode target_node) { // add node to the front of the list
        LRUNode temp;
        temp = head.next;

        target_node.next = temp;
        temp.previous = target_node;

        head.next = target_node;
        target_node.previous = head;
    }

    /**
     * Remove a node from the list, not necessarily the cache.
     * @param target_node The node to remove
     */
    private void remove(LRUNode target_node) { //remove node from list
        target_node.previous.next = target_node.next;
        target_node.next.previous = target_node.previous;
    }

    /**
     * Get an entry from the LRU cache.
     * @param key Identifier of the entry
     * @return Object corresponding to key
     */

    @Override
    public V get(K key) { //Get from the cache

        boolean check = hash_map.containsKey(key);

        if (!check) {

            return null; //key does not exist
        }

        LRUNode<K,V> target_node = hash_map.get(key);

        // Reorder list to increment most recently used:
        remove(target_node);
        add_to_front(target_node);

        return target_node.getValue();
    }

    /**
     * Add an entity to the cache.
     * If the cache is at capacity, replace the least relevant.
     * @param key Identifier for the object
     * @param value Object to store in the cache
     */

    @Override
    public void put(K key, V value) {
        LRUNode<K,V> update_node = (LRUNode)hash_map.get(key);

        if (update_node != null) { //Update the node
            remove(update_node);
            add_to_front(update_node);
            update_node.setValue(value);
            System.out.println("NODE UPDATED");
            return;
        }

        if (elements == capacity) { //Set the new tail and remove Node from hash
            LRUNode<K,V> temp;
            temp = tail.previous;
            remove(temp);

            hash_map.remove(temp.getKey()); // remove the Node by key
            elements--;

        }
        //Add to front of list, and add to hash:
        LRUNode new_node = new LRUNode<K,V>(key, value);
        add_to_front(new_node);
        hash_map.put(key, new_node);
        elements++;

    }

    /**
     * Permanently delete an entry from the cache
     * @param key Identifier with a corresponding Object stored in the cache
     */
    public void cacheDelete(K key){ //Delete entry from cache
        LRUNode<K, V> target_node = hash_map.get(key);
        if(target_node == null){
            return;
        }
        hash_map.remove(key);
        remove(target_node);
    }

    /**
     * Check if there is a mapping for a key in the cache
     * @param key Identifier with a corresponding Object stored in the cache
     * @return boolean whether the key exists in the hashmap or not.
     */
    public boolean checkCache(K key ){ //Check if something in the cache
       return (this.hash_map.containsKey(key));
    }

    /**
     * Method  to test the cache's contents, without re-ordering the list priority.
     * ** Do not use for anything other than testing **
     * @param key Identifier with a corresponding Object stored in the cache
     * @return V - value stored in cache
     */
    public V testGetCacheContents(K key){

        return this.hash_map.get(key).getValue();

    }
    public LRUNode<K,V> testGetCacheList(){
        return this.head.next;
    }
    public boolean isNodeTail(LRUNode<K,V> node){
        if(tail.equals(node)){
            return true;
        }
        return false;
    }

}
