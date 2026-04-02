package domain.move.directions;

import domain.piece.Team;
import domain.point.Point;

import java.util.ArrayList;
import java.util.List;

public record Direction(
        List<Vector> vectors
) {

    public Direction(List<Vector> vectors) {
        this.vectors = List.copyOf(vectors);
    }

    public boolean canReach(Point start, Point target) {
        int dy = vectors.stream().mapToInt(Vector::dy).sum();
        int dx = vectors.stream().mapToInt(Vector::dx).sum();

        if (start.canMake(dy, dx)) {
            Point destination = start.movePoint(dy, dx);
            return destination.equals(target);
        }

        return false;
    }

    public List<Point> getPoints(Point current) {
        List<Point> points = new ArrayList<>();
        for (Vector vector : this.vectors) {
            current = current.next(vector);
            points.add(current);
        }
        return points;
    }

    public Direction toForward(Team team) {
        List<Vector> forwardVector = vectors.stream()
                .filter(vector -> vector.isForwardFor(team))
                .toList();
        return new Direction(forwardVector);
    }

}
