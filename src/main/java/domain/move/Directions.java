package domain.move;

import domain.point.Point;
import java.util.ArrayList;
import java.util.List;

public class Directions {
    private final List<Direction> directions;

    public Directions(List<Direction> directions) {
        this.directions = directions;
    }

    public List<Point> findPoints(Point from, Point to) {
        return List.copyOf(directions.stream()
                .filter(direction -> direction.canReach(from, to))
                .findFirst()
                .map(direction -> direction.getPoints(from))
                .orElseThrow(() -> new IllegalArgumentException("해당 기물이 이동할 수 없는 위치/방향입니다.")));
    }

    public Directions merge(Directions other) {
        List<Direction> merged = new ArrayList<>(this.directions.size() + other.directions.size());
        merged.addAll(this.directions);
        merged.addAll(other.directions);
        return new Directions(List.copyOf(merged));
    }
}
