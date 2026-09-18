package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Experiment {

    private static final int[] INPUT_SIZES = {
            100, 1000, 5000, 10000, 50000
    };

    private static final String[] INPUT_TYPES = {
            "Random",
            "Sorted",
            "Reverse-sorted",
            "Duplicate-heavy"
    };

    private static final Random RANDOM =
            new Random(42);

    public static void runExperiments() {

        File resultsDirectory =
                new File("results");

        if (!resultsDirectory.exists()) {
            resultsDirectory.mkdirs();
        }

        File csvFile =
                new File(
                        resultsDirectory,
                        "results.csv"
                );

        try (PrintWriter writer =
                     new PrintWriter(
                             new FileWriter(csvFile)
                     )) {

            writer.println(
                    "algorithm,inputType,n,timeNs,recursionDepth,comparisons,swaps"
            );

            for (String inputType : INPUT_TYPES) {

                for (int n : INPUT_SIZES) {

                    System.out.println(
                            "Running: "
                                    + inputType
                                    + ", n="
                                    + n
                    );

                    int[] data =
                            generateArray(
                                    n,
                                    inputType
                            );

                    runMergeSort(
                            data.clone(),
                            inputType,
                            n,
                            writer
                    );

                    runQuickSort(
                            data.clone(),
                            inputType,
                            n,
                            writer
                    );

                    runDeterministicSelect(
                            data.clone(),
                            inputType,
                            n,
                            writer
                    );

                    Point[] points =
                            generatePoints(
                                    n,
                                    inputType
                            );

                    runClosestPair(
                            points,
                            inputType,
                            n,
                            writer
                    );
                }
            }

            System.out.println();
            System.out.println(
                    "Experiments completed successfully."
            );

            System.out.println(
                    "Results saved to: "
                            + csvFile.getPath()
            );

        } catch (IOException e) {

            System.out.println(
                    "Error writing CSV: "
                            + e.getMessage()
            );
        }
    }

    private static void runMergeSort(
            int[] array,
            String inputType,
            int n,
            PrintWriter writer) {

        MergeSorter sorter =
                new MergeSorter();

        long start =
                System.nanoTime();

        sorter.sort(array);

        long end =
                System.nanoTime();

        long time =
                end - start;

        writer.println(
                "MergeSort,"
                        + inputType + ","
                        + n + ","
                        + time + ","
                        + sorter.getMaxRecursionDepth() + ","
                        + sorter.getComparisons() + ","
                        + 0
        );
    }

    private static void runQuickSort(
            int[] array,
            String inputType,
            int n,
            PrintWriter writer) {

        QuickSorter sorter =
                new QuickSorter();

        long start =
                System.nanoTime();

        sorter.sort(array);

        long end =
                System.nanoTime();

        long time =
                end - start;

        writer.println(
                "QuickSort,"
                        + inputType + ","
                        + n + ","
                        + time + ","
                        + sorter.getMaxRecursionDepth() + ","
                        + sorter.getComparisons() + ","
                        + sorter.getSwaps()
        );
    }

    private static void runDeterministicSelect(
            int[] array,
            String inputType,
            int n,
            PrintWriter writer) {

        DeterministicSelector selector =
                new DeterministicSelector();

        int k = n / 2;

        long start =
                System.nanoTime();

        selector.select(array, k);

        long end =
                System.nanoTime();

        long time =
                end - start;

        writer.println(
                "DeterministicSelect,"
                        + inputType + ","
                        + n + ","
                        + time + ","
                        + selector.getMaxRecursionDepth() + ","
                        + selector.getComparisons() + ","
                        + selector.getSwaps()
        );
    }

    private static void runClosestPair(
            Point[] points,
            String inputType,
            int n,
            PrintWriter writer) {

        ClosestPairSolver solver =
                new ClosestPairSolver();

        long start =
                System.nanoTime();

        solver.findClosestPair(points);

        long end =
                System.nanoTime();

        long time =
                end - start;

        writer.println(
                "ClosestPair,"
                        + inputType + ","
                        + n + ","
                        + time + ","
                        + solver.getMaxRecursionDepth() + ","
                        + solver.getComparisons() + ","
                        + 0
        );
    }

    private static int[] generateArray(
            int n,
            String type) {

        int[] array =
                new int[n];

        switch (type) {

            case "Random":

                for (int i = 0; i < n; i++) {

                    array[i] =
                            RANDOM.nextInt(1_000_000);
                }

                break;

            case "Sorted":

                for (int i = 0; i < n; i++) {

                    array[i] = i;
                }

                break;

            case "Reverse-sorted":

                for (int i = 0; i < n; i++) {

                    array[i] = n - i;
                }

                break;

            case "Duplicate-heavy":

                for (int i = 0; i < n; i++) {

                    array[i] =
                            RANDOM.nextInt(10);
                }

                break;

            default:

                throw new IllegalArgumentException(
                        "Unknown input type: "
                                + type
                );
        }

        return array;
    }

    private static Point[] generatePoints(
            int n,
            String type) {

        Point[] points =
                new Point[n];

        switch (type) {

            case "Random":

                for (int i = 0; i < n; i++) {

                    double x =
                            RANDOM.nextDouble()
                                    * 1_000_000;

                    double y =
                            RANDOM.nextDouble()
                                    * 1_000_000;

                    points[i] =
                            new Point(x, y);
                }

                break;

            case "Sorted":

                for (int i = 0; i < n; i++) {

                    points[i] =
                            new Point(i, i);
                }

                break;

            case "Reverse-sorted":

                for (int i = 0; i < n; i++) {

                    points[i] =
                            new Point(
                                    n - i,
                                    n - i
                            );
                }

                break;

            case "Duplicate-heavy":

                for (int i = 0; i < n; i++) {

                    double x =
                            RANDOM.nextInt(10);

                    double y =
                            RANDOM.nextInt(10);

                    points[i] =
                            new Point(x, y);
                }

                break;

            default:

                throw new IllegalArgumentException(
                        "Unknown input type: "
                                + type
                );
        }

        return points;
    }

    public static void main(String[] args) {

        runExperiments();
    }
}
