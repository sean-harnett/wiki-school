package com.wikischool.wikischool.UtilitiesTest;

import com.wikischool.wikischool.main.utilities.Constants.SizeConstants;
import com.wikischool.wikischool.main.utilities.DataStructures.LRUCache.LRUCache;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LRUCacheTest {

    private final LRUCache<Integer, Integer> cache = new LRUCache<>(SizeConstants.DEFAULT_CACHE_LENGTH);

    /**
     * Test if LRUCache 'put' method implementation removes the least recently used element
     */
    @Test
    public void TestLruCachePut() {
        int[] testValues = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] testKeys = {2, 3, 4, 5, 6, 7, 8, 9, 1};

        for (int ix = 0; ix < testKeys.length; ix++) {
            this.cache.putIntoCache(testKeys[ix], testValues[ix]);
            LRUCache.LruNode listHead = this.cache.getCacheHead();

            System.out.println("-------------------");
            while (listHead.getNext().getValue() != null) {
                listHead = listHead.getNext();
                System.out.println("Node value: " + listHead.getValue());

            }
            System.out.println("-------------------");

        }
    }

    @Test
    public void TestLruCacheGet() {

        int[] testValues = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] testKeys = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        for (int ix = 0; ix < testKeys.length; ix++) {
            this.cache.putIntoCache(testKeys[ix], testValues[ix]);
        }
        System.out.println("\n-------------------");
        for (int ix = 0; ix < testKeys.length; ix++) {
            System.out.print("Getting Key: " + testKeys[ix]);
            try {
                this.cache.getFromCache(testKeys[ix]);
            }catch (IllegalStateException e){

                System.out.println("\n"+e.getMessage());

            }
            LRUCache.LruNode listHead = this.cache.getCacheHead();
            System.out.println("\n-------------------");
            while (listHead.getNext().getValue() != null) {
                listHead = listHead.getNext();
                System.out.println("Node value: " + listHead.getValue());
            }
            System.out.println("-------------------");
        }
    }

    @Test
    public void TestLruCacheRemove() {

        int[] testValues = {1, 2, 3, 4};
        //5, 6, 7, 8, 9};
        int[] testKeys = {1, 2, 3, 4};
        //5, 6, 7, 8, 9, 1};

        for (int ix = 0; ix < testKeys.length; ix++) {
            this.cache.putIntoCache(testKeys[ix], testValues[ix]);
        }
        System.out.println("-------------------");
        for (int ix = 0; ix < testKeys.length; ix++) {
            System.out.println("Removing key: " + testKeys[ix]);
            this.cache.removeFromCache(testKeys[ix]);

            LRUCache.LruNode listHead = this.cache.getCacheHead();
            System.out.println("-------------------");
            while (listHead.getNext().getValue() != null) {
                listHead = listHead.getNext();
                System.out.println("Node value: " + listHead.getValue());

            }
            System.out.println("-------------------");
        }

    }
}
