package domain.piece.move;

import domain.point.Point;

import java.util.List;

public class Direction {

    List<Vector> vectors;

    public Direction(List<Vector> vectors) {
        this.vectors = vectors;
    }

    public boolean canReach(Point start, Point target) {
        int dy = vectors.stream().mapToInt(Vector::dy).sum();
        int dx = vectors.stream().mapToInt(Vector::dx).sum();

        Point destination = start.movePoint(dy, dx);
        return destination.equals(target);
    }

}
