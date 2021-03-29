package com.wikischool.wikischool.UtilitiesTest;

import com.wikischool.wikischool.main.utilities.Constants.SizeConstants;
import com.wikischool.wikischool.main.utilities.DataStructures.LRUCache.LRUNode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LRUCache {

    /**
     * Test if LRUCache 'put' method implementation removes the least recently used element
     */
    @Test
    public void TestLRUCache() {
        com.wikischool.wikischool.main.utilities.DataStructures.LRUCache.LRUCache<Integer, Integer> cache = new com.wikischool.wikischool.main.utilities.DataStructures.LRUCache.LRUCache<>(SizeConstants.DEFAULT_CACHE_LENGTH);
        int testArrayLength = 10;
        int ix;
        int[] arr = new int[testArrayLength];
        for (ix = 0; ix < testArrayLength; ix++) { //Initialize test values
            arr[ix] = ix;
        }
        System.out.println("<< Start fill cache to capacity>>");
        LRUNode<Integer, Integer> list_reference;
        for (ix = 0; ix < SizeConstants.DEFAULT_CACHE_LENGTH; ix++) { // Fill the cache
            cache.put(arr[ix], arr[ix]);
            list_reference = cache.testGetCacheList();
            System.out.println("----------------");
            while (!cache.isNodeTail(list_reference)) {
                System.out.print("List Node value:");
                System.out.println(list_reference.getValue());
                System.out.println(" ");
                list_reference = list_reference.next;
            }
            System.out.println("----------------");
        }
        System.out.println("<< End fill cache to capacity>>");
        System.out.println();
        //Adding another element should remove the entry value '0' from the Cache.
        System.out.println("<< Start push cache beyond capacity>>");
        cache.put(arr[ix], arr[ix]);
        list_reference = cache.testGetCacheList();
        System.out.println("----------------");
        System.out.println();
        while (!cache.isNodeTail(list_reference)) {
            System.out.print("List Node value:");
            System.out.println(list_reference.getValue());
            System.out.println(" ");
            list_reference = list_reference.next;
        }
        System.out.println("----------------");
        System.out.println("<< End push cache beyond capacity>>");

    }
}
