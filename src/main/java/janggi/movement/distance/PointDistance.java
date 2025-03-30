package janggi.movement.distance;

import janggi.point.Point;

public class PointDistance {
    private static final double EPSILON = 1e-9;

    public static double oneCardinal() {
        return 1;
    }

    public static double oneDiagonal() {
        return Math.sqrt(2);
    }

    public static double oneDiagonalAndOneCardinal() {
        return Math.sqrt(5);
    }

    public static double oneDiagonalAndTwoCardinal() {
        return Math.sqrt(13);
    }

    private final double distance;

    public PointDistance(double distance) {
        this.distance = distance;
    }

    public static PointDistance calculate(Point startPoint, Point targetPoint) {
        double rowDistanceSquare = Math.pow(startPoint.row() - targetPoint.row(), 2);
        double columnDistanceSquare = Math.pow(startPoint.column() - targetPoint.column(), 2);
        return new PointDistance(Math.sqrt(rowDistanceSquare + columnDistanceSquare));
    }

    public boolean notMatches(double distance) {
        return Math.abs(this.distance - distance) > EPSILON;
    }
}
