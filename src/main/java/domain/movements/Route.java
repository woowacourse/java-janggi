package domain.movements;

import domain.board.BoardPoint;
import domain.board.Point;
import java.util.List;
import java.util.stream.IntStream;

public final class Route {
    private final List<Direction> directions;

    public Route(final List<Direction> directions) {
        this.directions = directions;
    }

    public Point navigateArrivalPoint(BoardPoint startBoardPoint) {
        Point point = startBoardPoint.toPoint();
        for (final Direction direction : directions) {
            point = point.move(direction);
        }
        return point;
    }

    public boolean canArrive(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint) {
        final Point point = navigateArrivalPoint(startBoardPoint);
        if (!point.isInRange()) {
            return false;
        }
        return point.toBoardPoint().equals(arrivalBoardPoint);
    }

    public List<BoardPoint> getAllPointsOnRoute(BoardPoint boardPoint) {
        Point start = boardPoint.toPoint();

        return IntStream.range(0, directions.size())
                .mapToObj(i -> directions.subList(0, i + 1))
                .map(subList -> moveBy(start, subList))
                .takeWhile(Point::isInRange)
                .map(Point::toBoardPoint)
                .toList();
    }

    private Point moveBy(Point start, List<Direction> directions) {
        Point current = start;
        for (Direction dir : directions) {
            current = current.move(dir);
        }
        return current;
    }
}
