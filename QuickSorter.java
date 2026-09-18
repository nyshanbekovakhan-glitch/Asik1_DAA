package org.example;

import java.util.Arrays;
import java.util.Random;

public class QuickSorter {

    private final Random random = new Random();

    private long comparisons;
    private long swaps;
    private int maxRecursionDepth;

    public void sort(int[] array) {
        comparisons = 0;
        swaps = 0;
        maxRecursionDepth = 0;

        if (array == null || array.length <= 1) {
            return;
        }

        quickSort(array, 0, array.length - 1, 1);
    }

    private void quickSort(int[] array, int low, int high, int depth) {

        while (low < high) {

            maxRecursionDepth = Math.max(maxRecursionDepth, depth);

            int pivotIndex = low + random.nextInt(high - low + 1);

            swap(array, pivotIndex, high);

            int partitionIndex = partition(array, low, high);

            int leftSize = partitionIndex - low;
            int rightSize = high - partitionIndex;

            if (leftSize < rightSize) {

                if (low < partitionIndex - 1) {
                    quickSort(array, low, partitionIndex - 1, depth + 1);
                }

                low = partitionIndex + 1;

            } else {

                if (partitionIndex + 1 < high) {
                    quickSort(array, partitionIndex + 1, high, depth + 1);
                }

                high = partitionIndex - 1;
            }
        }
    }

    private int partition(int[] array, int low, int high) {

        int pivot = array[high];
        int i = low;

        for (int j = low; j < high; j++) {

            comparisons++;

            if (array[j] <= pivot) {
                swap(array, i, j);
                i++;
            }
        }

        swap(array, i, high);

        return i;
    }

    private void swap(int[] array, int i, int j) {

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

        QuickSorter sorter = new QuickSorter();

        int[] array = {38, 27, 43, 3, 9, 82, 10};

        System.out.println("Before: " + Arrays.toString(array));

        sorter.sort(array);

        System.out.println("After:  " + Arrays.toString(array));
        System.out.println("Comparisons: " + sorter.getComparisons());
        System.out.println("Swaps: " + sorter.getSwaps());
        System.out.println("Max recursion depth: "
                + sorter.getMaxRecursionDepth());
    }
}
