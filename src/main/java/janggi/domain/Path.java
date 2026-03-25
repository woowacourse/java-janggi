package janggi.domain;

import java.util.List;

public class Path {

    private final List<Position> positions;

    public Path(List<Position> positions) {
        this.positions = positions;
    }

    public void makePath(Position nextPosition) {
        positions.add(nextPosition);
    }
}
