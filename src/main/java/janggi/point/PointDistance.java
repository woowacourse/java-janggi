package janggi.point;


public class PointDistance {
    private final double distance;

    public PointDistance(double distance) {
        this.distance = distance;
    }

    public static PointDistance calculate(Point startPoint, Point targetPoint) {
        double rowDistanceSquare = Math.pow(startPoint.row() - targetPoint.row(), 2);
        double columnDistanceSquare = Math.pow(startPoint.column() - targetPoint.column(), 2);
        return new PointDistance(Math.sqrt(rowDistanceSquare + columnDistanceSquare));
    }

    public boolean isSameWith(double distance) {
        return this.distance == distance;
    }

    public double getDistance() {
        return distance;
    }
}
