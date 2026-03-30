package janggi.domain.piece.path;

import janggi.domain.board.point.Point;
import janggi.domain.piece.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Path {
    private final List<Point> path;

    public Path(List<Point> path) {
        this.path = path;
    }

    public Path(Pattern pattern, Point from, PathStrategy pathStrategy) {
        this(pathStrategy.calculate(pattern, from));
    }

    public List<Point> getPath() {
        return path;
    }

    public boolean isEmpty() {
        return path.isEmpty();
    }

    public Path takeLast() {
        return new Path(List.of(path.getLast()));
    }

    public Path takeUntil(Point to) {
        return subPath(path.getFirst(), to);
    }

    private Path subPath(Point from, Point to) {
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
        return new Path(curPath);

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
        if (!(object instanceof Path path1)) {
            return false;
        }

        return Objects.equals(path, path1.path);
    }
}
