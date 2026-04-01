package janggi.domain.route;

import janggi.domain.board.Position;
import java.util.ArrayList;
import java.util.List;

public class Path {

    private final List<Position> positions;

    public Path() {
        this.positions = new ArrayList<>();
    }

    public void makePath(Position nextPosition) {
        positions.add(nextPosition);
    }

    public List<Position> getPositions() {
        return List.copyOf(positions);
    }
}
