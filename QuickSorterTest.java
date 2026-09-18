package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class QuickSorterTest {

    @Test
    void testRandomArray() {
        int[] array = {5, 2, 8, 1, 3};
        int[] expected = array.clone();

        Arrays.sort(expected);

        QuickSorter sorter = new QuickSorter();
        sorter.sort(array);

        assertArrayEquals(expected, array);
    }

    @Test
    void testSortedArray() {
        int[] array = {1, 2, 3, 4, 5};

        QuickSorter sorter = new QuickSorter();
        sorter.sort(array);

        assertArrayEquals(
                new int[]{1, 2, 3, 4, 5},
                array
        );
    }

    @Test
    void testReverseSortedArray() {
        int[] array = {5, 4, 3, 2, 1};

        QuickSorter sorter = new QuickSorter();
        sorter.sort(array);

        assertArrayEquals(
                new int[]{1, 2, 3, 4, 5},
                array
        );
    }

    @Test
    void testDuplicateHeavyArray() {
        int[] array = {3, 3, 1, 2, 3, 1, 2};

        QuickSorter sorter = new QuickSorter();
        sorter.sort(array);

        assertArrayEquals(
                new int[]{1, 1, 2, 2, 3, 3, 3},
                array
        );
    }

    @Test
    void testEmptyArray() {
        int[] array = {};

        QuickSorter sorter = new QuickSorter();
        sorter.sort(array);

        assertArrayEquals(
                new int[]{},
                array
        );
    }

    @Test
    void testSingleElement() {
        int[] array = {42};

        QuickSorter sorter = new QuickSorter();
        sorter.sort(array);

        assertArrayEquals(
                new int[]{42},
                array
        );
    }
}


