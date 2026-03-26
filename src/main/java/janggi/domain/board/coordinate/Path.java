package janggi.domain.board.coordinate;

import janggi.domain.piece.Direction;
import java.util.List;

public class Path {
    private final List<Point> path;

    private Path(List<Point> path) {
        this.path = path;
    }

    public Path(List<Direction> directions, Point from, PathStrategy pathStrategy) {
        this(pathStrategy.calculate(directions, from));
    }

    public List<Point> getPath() {
        return path;
    }
}
