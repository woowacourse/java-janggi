package janggi.moving;

import janggi.board.position.Position;
import java.util.ArrayList;
import java.util.List;

public class Path {
    private final List<Position> path;
    public static final int MIN_LENGTH = 2;

    public Path(List<Position> path) {
        this.path = new ArrayList<>(path);
    }

    public List<Position> getIntermediatePath() {
        int pathSize = path.size();
        if (pathSize > MIN_LENGTH) {
            return path.subList(1, pathSize - 1);
        }
        return new ArrayList<>();
    }

    public boolean lastEquals(Position position) {
        Position last = path.getLast();
        return last.equals(position);
    }
}
