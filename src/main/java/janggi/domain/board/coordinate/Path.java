package janggi.domain.board.coordinate;

import janggi.domain.piece.Pattern;
import java.util.List;

public class Path {
    private final List<Point> path;

    private Path(List<Point> path) {
        this.path = path;
    }

    public Path(Pattern pattern, Point from) {
        this(pattern.pathStrategy().calculate(pattern, from));
    }

    public List<Point> getPath() {
        return path;
    }
}
