package org.example;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ClosestPairSolverTest {

    @Test
    void testSimpleCase() {
        Point[] points = {
                new Point(0, 0),
                new Point(3, 4),
                new Point(1, 1),
                new Point(10, 10)
        };

        ClosestPairSolver solver = new ClosestPairSolver();

        Point[] pair = solver.findClosestPair(points);

        assertEquals(Math.sqrt(2), pair[0].distanceTo(pair[1]), 1e-9);
    }

    @Test
    void testDuplicatePoints() {
        Point[] points = {
                new Point(0, 0),
                new Point(5, 5),
                new Point(0, 0),
                new Point(10, 10)
        };

        ClosestPairSolver solver = new ClosestPairSolver();

        Point[] pair = solver.findClosestPair(points);

        assertEquals(0.0, pair[0].distanceTo(pair[1]), 1e-9);
    }

    @Test
    void testTwoPoints() {
        Point[] points = {
                new Point(0, 0),
                new Point(3, 4)
        };

        ClosestPairSolver solver = new ClosestPairSolver();

        Point[] pair = solver.findClosestPair(points);

        assertEquals(5.0, pair[0].distanceTo(pair[1]), 1e-9);
    }

    @Test
    void testRandomSmallCases() {
        Random random = new Random(42);

        ClosestPairSolver solver = new ClosestPairSolver();

        for (int test = 0; test < 20; test++) {
            int n = 2 + random.nextInt(20);

            Point[] points = new Point[n];

            for (int i = 0; i < n; i++) {
                double x = random.nextDouble() * 100;
                double y = random.nextDouble() * 100;
                points[i] = new Point(x, y);
            }

            Point[] pair = solver.findClosestPair(points);

            double actual = pair[0].distanceTo(pair[1]);
            double expected = bruteForceDistance(points);

            assertEquals(expected, actual, 1e-9);
        }
    }

    @Test
    void testInvalidInput() {
        ClosestPairSolver solver = new ClosestPairSolver();

        assertThrows(
                IllegalArgumentException.class,
                () -> solver.findClosestPair(null)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> solver.findClosestPair(new Point[]{})
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> solver.findClosestPair(new Point[]{
                        new Point(1, 1)
                })
        );
    }

    private double bruteForceDistance(Point[] points) {
        double minDistance = Double.POSITIVE_INFINITY;

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double distance = points[i].distanceTo(points[j]);

                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }

        return minDistance;
    }
}

