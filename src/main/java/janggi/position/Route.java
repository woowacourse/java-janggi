package janggi.position;

import janggi.game.Pieces;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Route {
    private final List<Position> positions;

    Route(List<Position> positions) {
        if (positions.isEmpty()) {
            throw new IllegalArgumentException("빈 경로는 존재할 수 없습니다.");
        }
        this.positions = positions;
    }

    public static Route of(List<Position> positions) {
        return new Route(positions);
    }

    public boolean canBombJump(Pieces pieces) {
        if (pieces.isExistBombInRoute(positions)) {
            return false;
        }
        long count = getPointsExceptEndPoint().stream()
                .filter(pieces::isExistPiece)
                .filter(position -> !pieces.isBombPiece(position))
                .count();

        return count == 1;
    }

    public Position searchEndPoint(Position startPoint) {
        return positions.stream()
                .max(Comparator.comparingInt(position ->
                        calculateDistance(startPoint, position)))
                .orElse(positions.getFirst());
    }

    private int calculateDistance(Position startPoint, Position now) {
        return Math.abs(now.getColumn() - startPoint.getColumn())
                + Math.abs(now.getRow() - startPoint.getRow());
    }

    public List<Position> getPointsExceptEndPoint() {
        return positions.subList(0, positions.size() - 1);
    }

    public int length() {
        return this.positions.size();
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
