package com.wikischool.wikischool.UtilitiesTest;

import com.wikischool.wikischool.main.utilities.DataStructures.FNVHashMap.FNVMap;
import com.wikischool.wikischool.main.utilities.DataStructures.FNVHashMap.HashMapFNV;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HashMapTest {
    private final FNVMap<Integer, Integer> hashMap = new HashMapFNV<>();


    @Test
    public void putTest() {
        int[] testInts = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] testIntKeys = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int ix = 0; ix < testIntKeys.length; ix++) {
            this.hashMap.put(testIntKeys[ix], testInts[ix]);
        }
        Set<Integer> key = this.hashMap.getKeySet();
        int[] keys = new int[testIntKeys.length];
        int i = 0;
        for (int ix : key) {
            keys[i] = ix;
            i++;
        }
        assertThat(keys).isEqualTo(testIntKeys);

    }

    @Test
    public void getTest() {

        int[] testInts = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] testResults = new int[10];
        int[] testIntKeys = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        for (int ix = 0; ix < testIntKeys.length; ix++) {
            this.hashMap.put(testIntKeys[ix], testInts[ix]);
        }

        for (int ix = 0; ix < testIntKeys.length; ix++) {
            testResults[ix] = this.hashMap.get(testIntKeys[ix]);
        }


        assertThat(testResults).isEqualTo(testInts);
    }

    @Test
    public void removeTest() {

        int[] testInts = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] testResults = new int[10];
        int[] testIntKeys = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        for (int ix = 0; ix < testIntKeys.length; ix++) {
            this.hashMap.put(testIntKeys[ix], testInts[ix]);
        }

        for (int ix = 0; ix < testIntKeys.length; ix++) {
            this.hashMap.remove(testIntKeys[ix]);
        }


        assertThat(this.hashMap.size()).isEqualTo(0); // map is empty
        assertThat(this.hashMap.getKeySet().size()).isEqualTo(0); // keySet is empty

    }


}
