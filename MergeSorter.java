package org.example;
import java.util.Arrays;

public class MergeSorter {

    private static final int INSERTION_SORT_CUTOFF = 16;

    private long comparisons;
    private int maxRecursionDepth;

    public void sort(int[] array) {
        comparisons = 0;
        maxRecursionDepth = 0;

        if (array == null || array.length <= 1) {
            return;
        }

        int[] buffer = new int[array.length];

        mergeSort(array, buffer, 0, array.length - 1, 1);
    }

    private void mergeSort(int[] array, int[] buffer,
                           int left, int right, int depth) {

        maxRecursionDepth = Math.max(maxRecursionDepth, depth);

        if (left >= right) {
            return;
        }

        int size = right - left + 1;

        // Small-input cutoff: use Insertion Sort
        if (size <= INSERTION_SORT_CUTOFF) {
            insertionSort(array, left, right);
            return;
        }

        int middle = left + (right - left) / 2;

        mergeSort(array, buffer, left, middle, depth + 1);
        mergeSort(array, buffer, middle + 1, right, depth + 1);

        // Already sorted: no need to merge
        comparisons++;
        if (array[middle] <= array[middle + 1]) {
            return;
        }

        merge(array, buffer, left, middle, right);
    }

    private void merge(int[] array, int[] buffer,
                       int left, int middle, int right) {

        int i = left;
        int j = middle + 1;
        int k = left;

        while (i <= middle && j <= right) {

            comparisons++;

            if (array[i] <= array[j]) {
                buffer[k++] = array[i++];
            } else {
                buffer[k++] = array[j++];
            }
        }

        while (i <= middle) {
            buffer[k++] = array[i++];
        }

        while (j <= right) {
            buffer[k++] = array[j++];
        }

        for (int index = left; index <= right; index++) {
            array[index] = buffer[index];
        }
    }

    private void insertionSort(int[] array, int left, int right) {

        for (int i = left + 1; i <= right; i++) {

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

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }

    public static void main(String[] args) {

        MergeSorter sorter = new MergeSorter();

        int[] array = {38, 27, 43, 3, 9, 82, 10};

        System.out.println("Before: " + Arrays.toString(array));

        sorter.sort(array);

        System.out.println("After:  " + Arrays.toString(array));
        System.out.println("Comparisons: " + sorter.getComparisons());
        System.out.println("Max recursion depth: "
                + sorter.getMaxRecursionDepth());
    }
}