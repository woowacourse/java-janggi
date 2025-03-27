package janggi.position;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Route {
    private final List<Position> positions;

    private Route(List<Position> positions) {
        if (positions.isEmpty()) {
            throw new IllegalArgumentException("빈 경로는 존재할 수 없습니다.");
        }
        this.positions = positions;
    }

    public static Route of(List<Position> positions) {
        return new Route(positions);
    }

    public Position searchEndPoint(Position startPoint) {
        return positions.stream()
                .max(Comparator.comparingInt(position ->
                        calculateDistance(startPoint, position)))
                .orElse(positions.get(0));
    }

    private int calculateDistance(Position startPoint, Position now) {
        return Math.abs(now.getX() - startPoint.getX())
                + Math.abs(now.getY() - startPoint.getY());
    }

    public List<Position> getPointsExceptEndPoint() {
        return positions.subList(0, positions.size() - 1);
    }

    public List<Position> getPoints() {
        return new ArrayList<>(positions);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Route route = (Route) object;
        return Objects.equals(positions, route.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(positions);
    }
}
