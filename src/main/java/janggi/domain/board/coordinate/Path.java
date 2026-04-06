package janggi.domain.board.coordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import janggi.domain.piece.Pattern;

public class Path {

    private final List<Point> path;
    private final boolean diagonal;

    public Path(List<Point> path, boolean diagonal) {
        this.path = new ArrayList<>(path);
        this.diagonal = diagonal;
    }

    public Path(Pattern pattern, Point from, PathStrategy pathStrategy) {
        this(pathStrategy.calculate(pattern, from), pattern.isDiagonal());
    }

    public List<Point> getPath() {
        return Collections.unmodifiableList(path);
    }

    public boolean isDiagonal() {
        return diagonal;
    }

    public boolean isEmpty() {
        return path.isEmpty();
    }

    public Path cutUntil(Point point) {
        List<Point> curPath = new ArrayList<>();
        for (Point pathPoint : path) {
            curPath.add(pathPoint);
            if (pathPoint.equals(point)) {
                break;
            }
        }
        return new Path(curPath, this.diagonal);
    }
}
