package janggi.domain.path;

import janggi.domain.board.BoardInfo;
import janggi.domain.path.generator.PathStrategy;
import janggi.domain.point.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class CandidatePath {
    private final Point base;
    private final List<Point> path;

    public CandidatePath(Point base, List<Point> path) {
        this.base = base;
        this.path = path;
    }

    public CandidatePath(List<Point> path) {
        this.base = null;
        this.path = path;
    }

    public CandidatePath(Movement movement, Point base, PathStrategy pathStrategy, Predicate<Point> predicate) {
        this(base, pathStrategy.calculate(movement, base, predicate));
    }

    public Point getPointEncounterPiece(BoardInfo boardInfo, int encounters) {
        int count = 0;
        for (Point point : path) {
            if (boardInfo.isEmpty(point)) {
                continue;
            }
            count++;
            if (count == encounters) {
                return point;
            }
        }
        return null;
    }

    public boolean isThereNoPieceInPathExceptForLast(BoardInfo boardInfo) {
        return path.stream()
                .limit((long) path.size() - 1)
                .allMatch(boardInfo::isEmpty);
    }

    public boolean isForward(Direction direction) {
        return path.stream()
                .anyMatch(point -> isForward(point, direction));
    }

    private boolean isForward(Point nextPoint, Direction direction) {
        Point vector = nextPoint.minus(base);

        boolean dx = direction.getDx() != 0 && vector.x() == direction.getDx();
        boolean dy = direction.getDy() != 0 && vector.y() == direction.getDy();

        return dx || dy;
    }

    public List<Point> getPath() {
        return List.copyOf(path);
    }

    public boolean isEmpty() {
        return path.isEmpty();
    }

    public CandidatePath takeLast() {
        return new CandidatePath(base, List.of(path.getLast()));
    }

    public CandidatePath add(Point point) {
        List<Point> points = new ArrayList<>(path);
        points.add(point);
        return new CandidatePath(base, points);
    }

    public CandidatePath between(Point from, Point to) {
        List<Point> curPath = new ArrayList<>();
        boolean started = false;

        for (Point point : path) {
            if (point.equals(to)) {
                break;
            }
            if (started) {
                curPath.add(point);
            }
            if (point.equals(from)) {
                started = true;
            }
        }

        return new CandidatePath(this.base, curPath);
    }

    public CandidatePath takeUntil(Point to) {
        int toIndex = path.indexOf(to);
        return new CandidatePath(base, path.stream()
                .limit((long) toIndex + 1)
                .toList());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(path);
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof CandidatePath candidatePath1)) {
            return false;
        }

        return Objects.equals(path, candidatePath1.path);
    }
}
