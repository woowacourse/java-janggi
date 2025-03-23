package janggi.point;


public class PointDistance {

    private static final int SQUARE_NUMBER = 2;

    private final double distance;

    public PointDistance(double distance) {
        this.distance = distance;
    }

    public static PointDistance calculate(Point startPoint, Point targetPoint) {
        double rowDistanceSquare = Math.pow(startPoint.row() - targetPoint.row(), SQUARE_NUMBER);
        double columnDistanceSquare = Math.pow(startPoint.column() - targetPoint.column(), SQUARE_NUMBER);

        return new PointDistance(Math.sqrt(rowDistanceSquare + columnDistanceSquare));
    }

    public boolean isSameWith(double distance) {
        return this.distance == distance;
    }

    public double getDistance() {
        return distance;
    }
}
