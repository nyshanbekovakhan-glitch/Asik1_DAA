package org.example;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class ClosestPairSolver {

    private long comparisons;
    private int maxRecursionDepth;

    public Point[] findClosestPair(Point[] points) {

        comparisons = 0;
        maxRecursionDepth = 0;

        if (points == null || points.length < 2) {
            throw new IllegalArgumentException(
                    "At least two points are required"
            );
        }

        Point[] pointsByX = points.clone();
        Point[] pointsByY = points.clone();

        Arrays.sort(
                pointsByX,
                Comparator.comparingDouble(Point::getX)
        );

        Arrays.sort(
                pointsByY,
                Comparator.comparingDouble(Point::getY)
        );

        return closestPair(
                pointsByX,
                pointsByY,
                0,
                pointsByX.length - 1,
                1
        );
    }

    private Point[] closestPair(
            Point[] pointsByX,
            Point[] pointsByY,
            int left,
            int right,
            int depth) {

        maxRecursionDepth = Math.max(
                maxRecursionDepth,
                depth
        );

        int n = right - left + 1;

        if (n <= 3) {
            return bruteForce(
                    pointsByX,
                    left,
                    right
            );
        }

        int middle =
                left + (right - left) / 2;

        double middleX =
                pointsByX[middle].getX();

        Point[] leftPointsX =
                Arrays.copyOfRange(
                        pointsByX,
                        left,
                        middle + 1
                );

        Point[] rightPointsX =
                Arrays.copyOfRange(
                        pointsByX,
                        middle + 1,
                        right + 1
                );

        Point[] leftPointsY =
                new Point[leftPointsX.length];

        Point[] rightPointsY =
                new Point[rightPointsX.length];

        /*
         * Store all points from the left half
         * in a HashSet.
         *
         * This allows O(1) average lookup
         * when dividing pointsByY.
         */
        Set<Point> leftSet =
                new HashSet<>(
                        Arrays.asList(leftPointsX)
                );

        int leftCount = 0;
        int rightCount = 0;

        /*
         * Divide pointsByY into leftY and rightY.
         * The Y-order is preserved.
         */
        for (Point point : pointsByY) {

            comparisons++;

            if (leftSet.contains(point)) {

                leftPointsY[leftCount++] =
                        point;

            } else {

                rightPointsY[rightCount++] =
                        point;
            }
        }

        Point[] leftY =
                Arrays.copyOf(
                        leftPointsY,
                        leftCount
                );

        Point[] rightY =
                Arrays.copyOf(
                        rightPointsY,
                        rightCount
                );

        /*
         * Solve the left half.
         */
        Point[] leftPair =
                closestPair(
                        leftPointsX,
                        leftY,
                        0,
                        leftPointsX.length - 1,
                        depth + 1
                );

        /*
         * Solve the right half.
         */
        Point[] rightPair =
                closestPair(
                        rightPointsX,
                        rightY,
                        0,
                        rightPointsX.length - 1,
                        depth + 1
                );

        double leftDistance =
                leftPair[0].distanceTo(
                        leftPair[1]
                );

        double rightDistance =
                rightPair[0].distanceTo(
                        rightPair[1]
                );

        double delta;

        Point[] bestPair;

        comparisons++;

        if (leftDistance <= rightDistance) {

            delta = leftDistance;
            bestPair = leftPair;

        } else {

            delta = rightDistance;
            bestPair = rightPair;
        }

        /*
         * Build the strip.
         *
         * pointsByY is already sorted by Y,
         * so the strip also remains sorted by Y.
         */
        Point[] strip =
                new Point[n];

        int stripSize = 0;

        for (Point point : pointsByY) {

            comparisons++;

            if (Math.abs(
                    point.getX() - middleX
            ) < delta) {

                strip[stripSize++] =
                        point;
            }
        }

        /*
         * Check points inside the strip.
         */
        for (int i = 0;
             i < stripSize;
             i++) {

            for (int j = i + 1;
                 j < stripSize;
                 j++) {

                comparisons++;

                /*
                 * Because strip is sorted by Y,
                 * no later point can be useful
                 * if the Y difference is >= delta.
                 */
                if (strip[j].getY()
                        - strip[i].getY()
                        >= delta) {

                    break;
                }

                double distance =
                        strip[i].distanceTo(
                                strip[j]
                        );

                if (distance < delta) {

                    delta = distance;

                    bestPair =
                            new Point[]{
                                    strip[i],
                                    strip[j]
                            };
                }
            }
        }

        return bestPair;
    }

    /*
     * Brute force is used only for
     * very small subarrays (n <= 3).
     */
    private Point[] bruteForce(
            Point[] points,
            int left,
            int right) {

        double minDistance =
                Double.POSITIVE_INFINITY;

        Point[] bestPair =
                new Point[2];

        for (int i = left;
             i <= right;
             i++) {

            for (int j = i + 1;
                 j <= right;
                 j++) {

                comparisons++;

                double distance =
                        points[i].distanceTo(
                                points[j]
                        );

                if (distance < minDistance) {

                    minDistance = distance;

                    bestPair[0] =
                            points[i];

                    bestPair[1] =
                            points[j];
                }
            }
        }

        return bestPair;
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }

    public static void main(String[] args) {

        ClosestPairSolver solver =
                new ClosestPairSolver();

        Point[] points = {
                new Point(2, 3),
                new Point(12, 30),
                new Point(40, 50),
                new Point(5, 1),
                new Point(12, 10),
                new Point(3, 4)
        };

        Point[] result =
                solver.findClosestPair(points);

        System.out.println(
                "Closest pair: "
                        + result[0]
                        + " and "
                        + result[1]
        );

        System.out.println(
                "Distance: "
                        + result[0]
                        .distanceTo(result[1])
        );

        System.out.println(
                "Comparisons: "
                        + solver.getComparisons()
        );

        System.out.println(
                "Max recursion depth: "
                        + solver.getMaxRecursionDepth()
        );
    }
}
