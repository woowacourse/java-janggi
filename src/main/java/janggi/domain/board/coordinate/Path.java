package janggi.domain.board.coordinate;

import janggi.domain.piece.Direction;
import janggi.domain.piece.Directions;
import java.util.List;

public class Path {
    private final List<Point> path;

    private Path(List<Point> path) {
        this.path = path;
    }

    public Path(Directions directions, Point from) {
        this(directions.pathStrategy().calculate(directions, from));
    }

    public List<Point> getPath() {
        return path;
    }
}
