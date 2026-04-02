package janggi.domain.piece.path;

import janggi.domain.board.Coordination;
import janggi.domain.board.point.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Movement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CandidatePath {
    private final Point from;
    private final List<Point> path;

    public CandidatePath(Point from, List<Point> path) {
        this.from = from;
        this.path = path;
    }

    public CandidatePath(List<Point> path) {
        this.from = null;
        this.path = path;
    }

    public CandidatePath(Movement movement, Point from, PathStrategy pathStrategy, Coordination coordination) {
        this(from, pathStrategy.calculate(movement, from, coordination));
    }

    public boolean isForward(Direction direction) {
        int dx = direction.getDx();
        int dy = direction.getDy();

        for (Point point : path) {
            if (isForward(from.x(), point.x(), dx) || isForward(from.y(), point.y(), dy)) {
                return true;
            }
        }
        return false;
    }

    private boolean isForward(int value, int next, int deltaValue) {
        return deltaValue != 0 && value + deltaValue == next;
    }

    public List<Point> getPath() {
        return List.copyOf(path);
    }

    public boolean isEmpty() {
        return path.isEmpty();
    }

    public CandidatePath takeLast() {
        return new CandidatePath(from, List.of(path.getLast()));
    }

    public CandidatePath takeUntil(Point to) {
        return subPath(path.getFirst(), to);
    }

    private CandidatePath subPath(Point from, Point to) {
        if (to == null || from == null) {
            throw new IllegalStateException("Point 값은 null이 될 수 없습니다.");
        }
        List<Point> curPath = new ArrayList<>();
        for (Point pathPoint : path) {
            curPath.add(pathPoint);
            if (pathPoint.equals(to)) {
                break;
            }
        }
        return new CandidatePath(from, curPath);

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
