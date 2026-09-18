package org.example;

import java.util.Arrays;

public class DeterministicSelector {

    private long comparisons;
    private long swaps;
    private int maxRecursionDepth;

    public int select(int[] array, int k) {
        comparisons = 0;
        swaps = 0;
        maxRecursionDepth = 0;

        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }

        if (k < 0 || k >= array.length) {
            throw new IllegalArgumentException("Invalid k");
        }

        return selectRecursive(array, 0, array.length - 1, k, 1);
    }

    private int selectRecursive(int[] array,
                                int left,
                                int right,
                                int k,
                                int depth) {

        maxRecursionDepth =
                Math.max(maxRecursionDepth, depth);

        if (left == right) {
            return array[left];
        }

        int pivotValue =
                medianOfMedians(array, left, right);

        int[] equalRange =
                partitionThreeWay(
                        array,
                        left,
                        right,
                        pivotValue
                );

        int equalLeft = equalRange[0];
        int equalRight = equalRange[1];

        if (k >= equalLeft && k <= equalRight) {
            return array[k];
        }

        if (k < equalLeft) {
            return selectRecursive(
                    array,
                    left,
                    equalLeft - 1,
                    k,
                    depth + 1
            );
        }

        return selectRecursive(
                array,
                equalRight + 1,
                right,
                k,
                depth + 1
        );
    }

    private int medianOfMedians(int[] array,
                                int left,
                                int right) {

        int size = right - left + 1;

        if (size <= 5) {
            insertionSort(array, left, right);

            return array[
                    left + size / 2
                    ];
        }

        int medianCount = 0;

        for (int start = left;
             start <= right;
             start += 5) {

            int groupRight =
                    Math.min(start + 4, right);

            insertionSort(
                    array,
                    start,
                    groupRight
            );

            int medianIndex =
                    start
                            + (groupRight - start) / 2;

            swap(
                    array,
                    left + medianCount,
                    medianIndex
            );

            medianCount++;
        }

        int medianOfMediansIndex =
                left + medianCount / 2;

        return selectRecursive(
                array,
                left,
                left + medianCount - 1,
                medianOfMediansIndex,
                1
        );
    }

    private int[] partitionThreeWay(
            int[] array,
            int left,
            int right,
            int pivotValue) {

        int low = left;
        int current = left;
        int high = right;

        while (current <= high) {

            comparisons++;

            if (array[current] < pivotValue) {

                swap(array, low, current);

                low++;
                current++;

            } else {

                comparisons++;

                if (array[current] > pivotValue) {

                    swap(
                            array,
                            current,
                            high
                    );

                    high--;

                } else {

                    current++;
                }
            }
        }

        return new int[]{
                low,
                high
        };
    }

    private void insertionSort(
            int[] array,
            int left,
            int right) {

        for (int i = left + 1;
             i <= right;
             i++) {

            int key = array[i];

            int j = i - 1;

            while (j >= left) {

                comparisons++;

                if (array[j] <= key) {
                    break;
                }

                array[j + 1] = array[j];

                j--;
            }

            array[j + 1] = key;
        }
    }

    private void swap(
            int[] array,
            int i,
            int j) {

        if (i == j) {
            return;
        }

        int temp = array[i];

        array[i] = array[j];
        array[j] = temp;

        swaps++;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getSwaps() {
        return swaps;
    }

    public int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }

    public static void main(String[] args) {

        DeterministicSelector selector =
                new DeterministicSelector();

        int[] array = {
                12, 3, 5, 7, 4,
                19, 26, 8, 15, 2
        };

        int k = 4;

        System.out.println(
                "Array: "
                        + Arrays.toString(array)
        );

        int result =
                selector.select(array, k);

        System.out.println(
                "k = " + k
        );

        System.out.println(
                "Selected element: "
                        + result
        );

        System.out.println(
                "Comparisons: "
                        + selector.getComparisons()
        );

        System.out.println(
                "Swaps: "
                        + selector.getSwaps()
        );

        System.out.println(
                "Max recursion depth: "
                        + selector.getMaxRecursionDepth()
        );
    }
}
