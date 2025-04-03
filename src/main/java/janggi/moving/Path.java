package janggi.moving;

import janggi.board.position.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Path {
    private final List<Position> path;
    public static final int MIN_LENGTH = 2;

    public Path(List<Position> path) {
        this.path = Collections.unmodifiableList(path);
    }

    public List<Position> getIntermediatePath() {
        int pathSize = path.size();
        if (pathSize > MIN_LENGTH) {
            return path.subList(1, pathSize - 1);
        }
        return new ArrayList<>();
    }

    public boolean isValidPath() {
        return path.size() >= 2;
    }

    public boolean lastEquals(Position position) {
        if (isValidPath()) {
            Position last = path.getLast();
            return last.equals(position);
        }
        return false;
    }

    public boolean isOneStep() {
        if (isValidPath()) {
            return path.size() == 2;
        }
        return false;
    }

    public List<Position> getPath() {
        return path;
    }
}
