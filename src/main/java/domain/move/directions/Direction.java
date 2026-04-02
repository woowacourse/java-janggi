package domain.move.directions;

import domain.piece.Team;
import domain.point.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Direction {

    List<Vector> vectors;

    public Direction(List<Vector> vectors) {
        this.vectors = vectors;
    }

    public boolean canReach(Point start, Point target) {
        int dy = vectors.stream().mapToInt(Vector::dy).sum();
        int dx = vectors.stream().mapToInt(Vector::dx).sum();

        if(start.canMake(dy, dx)){
            Point destination = start.movePoint(dy, dx);
            return destination.equals(target);
        }

        return false;
    }

    public List<Point> getPoints(Point current){
        List<Point> points = new ArrayList<>();
        for(Vector vector : this.vectors){
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Direction direction = (Direction) o;
        return Objects.equals(vectors, direction.vectors);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(vectors);
    }

}
