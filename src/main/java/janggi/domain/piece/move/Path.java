package janggi.domain.piece.move;

import janggi.domain.board.Direction;
import janggi.domain.board.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Path {
    private final List<Direction> path;

    public Path(List<Direction> path) {
        this.path = path;
    }

    public static Path calculatePath(Point start, Point end, Set<List<Direction>> paths) {
        return paths.stream()
                .filter(path -> canMoveEndPointByPath(start, end, path))
                .findFirst()
                .map(Path::new)
                .orElseThrow(() -> new IllegalArgumentException("해당 위치로 이동할 수 없습니다."));
    }

    private static boolean canMoveEndPointByPath(final Point start, Point end, List<Direction> path) {
        Point current = start;
        for (Direction direction : path) {
            if (!direction.canMoveFrom(current)) {
                break;
            }
            current = direction.moveFrom(current);
        }
        return current.equals(end);
    }

    public static Path calculatePath(Point start, Point end, List<Direction> directions) {
        Direction movedDirection = directions.stream()
                .filter(direction -> canMoveEndPointByDirection(start, end, direction))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치로 이동할 수 없습니다."));
        return getPathByDirection(start, end, movedDirection);
    }

    private static boolean canMoveEndPointByDirection(final Point start, Point end,
                                                      Direction direction) {
        Point current = start;
        while (!current.equals(end) && direction.canMoveFrom(current)) {
            current = direction.moveFrom(current);
        }
        return current.equals(end);
    }

    private static Path getPathByDirection(Point start, Point end, Direction movedDirection) {
        List<Direction> path = new ArrayList<>();
        int moveCount = Math.abs(start.x() - end.x()) + Math.abs(start.y() - end.y());
        if (movedDirection.isDiagonal()) {
            return getPath(movedDirection, moveCount/2, path);
        }
        return getPath(movedDirection, moveCount, path);
    }

    private static Path getPath(Direction movedDirection, int moveCount, List<Direction> path) {
        for (int i = 0; i < moveCount; i++) {
            path.add(movedDirection);
        }
        return new Path(path);
    }

    public List<Point> getMovedPoints(final Point start, Point end) {
        Point current = start;
        List<Point> movedPoints = new ArrayList<>();
        for (Direction direction : path) {
            movedPoints.add(current);
            current = direction.moveFrom(current);
        }
        movedPoints.add(end);
        return movedPoints;
    }

    public List<Direction> getPath() {
        return Collections.unmodifiableList(path);
    }
}
