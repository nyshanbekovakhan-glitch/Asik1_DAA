package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MergeSorterTest {

    @Test
    void testRandomArray() {
        int[] array = {5, 2, 8, 1, 3};
        int[] expected = array.clone();

        Arrays.sort(expected);

        MergeSorter sorter = new MergeSorter();
        sorter.sort(array);

        assertArrayEquals(expected, array);
    }

    @Test
    void testSortedArray() {
        int[] array = {1, 2, 3, 4, 5};

        MergeSorter sorter = new MergeSorter();
        sorter.sort(array);

        assertArrayEquals(
                new int[]{1, 2, 3, 4, 5},
                array
        );
    }

    @Test
    void testReverseSortedArray() {
        int[] array = {5, 4, 3, 2, 1};

        MergeSorter sorter = new MergeSorter();
        sorter.sort(array);

        assertArrayEquals(
                new int[]{1, 2, 3, 4, 5},
                array
        );
    }

    @Test
    void testDuplicateHeavyArray() {
        int[] array = {3, 3, 1, 2, 3, 1, 2};

        MergeSorter sorter = new MergeSorter();
        sorter.sort(array);

        assertArrayEquals(
                new int[]{1, 1, 2, 2, 3, 3, 3},
                array
        );
    }

    @Test
    void testEmptyArray() {
        int[] array = {};

        MergeSorter sorter = new MergeSorter();
        sorter.sort(array);

        assertArrayEquals(
                new int[]{},
                array
        );
    }

    @Test
    void testSingleElement() {
        int[] array = {42};

        MergeSorter sorter = new MergeSorter();
        sorter.sort(array);

        assertArrayEquals(
                new int[]{42},
                array
        );
    }
}