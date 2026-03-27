package janggi.domain.board.coordinate;

import janggi.domain.piece.Pattern;
import java.util.ArrayList;
import java.util.List;

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

    public boolean isEmpty(){
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
        return new Path(curPath);
    }
}
