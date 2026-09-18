package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DeterministicSelectorTest {

    @Test
    void testBasicSelection() {
        int[] array = {7, 2, 9, 4, 1, 5};

        DeterministicSelector selector = new DeterministicSelector();

        assertEquals(1, selector.select(array.clone(), 0));
        assertEquals(4, selector.select(array.clone(), 2));
        assertEquals(9, selector.select(array.clone(), 5));
    }

    @Test
    void testMinimum() {
        int[] array = {8, 3, 6, 1, 9, 2};

        DeterministicSelector selector = new DeterministicSelector();

        assertEquals(1, selector.select(array.clone(), 0));
    }

    @Test
    void testMaximum() {
        int[] array = {8, 3, 6, 1, 9, 2};

        DeterministicSelector selector = new DeterministicSelector();

        assertEquals(9, selector.select(array.clone(), array.length - 1));
    }

    @Test
    void testDuplicateHeavyArray() {
        int[] array = {5, 1, 5, 3, 5, 2, 1, 5};

        DeterministicSelector selector = new DeterministicSelector();

        int[] expected = array.clone();
        Arrays.sort(expected);

        for (int k = 0; k < array.length; k++) {
            assertEquals(
                    expected[k],
                    selector.select(array.clone(), k)
            );
        }
    }

    @Test
    void test100RandomCases() {
        Random random = new Random(42);

        DeterministicSelector selector = new DeterministicSelector();

        for (int test = 0; test < 100; test++) {
            int n = 1 + random.nextInt(100);

            int[] array = new int[n];

            for (int i = 0; i < n; i++) {
                array[i] = random.nextInt(1000);
            }

            int k = random.nextInt(n);

            int[] expected = array.clone();
            Arrays.sort(expected);

            int actual = selector.select(array.clone(), k);

            assertEquals(expected[k], actual);
        }
    }

    @Test
    void testInvalidK() {
        int[] array = {1, 2, 3};

        DeterministicSelector selector = new DeterministicSelector();

        assertThrows(
                IllegalArgumentException.class,
                () -> selector.select(array.clone(), -1)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> selector.select(array.clone(), 3)
        );
    }

    @Test
    void testEmptyArray() {
        DeterministicSelector selector = new DeterministicSelector();

        assertThrows(
                IllegalArgumentException.class,
                () -> selector.select(new int[]{}, 0)
        );
    }
}


