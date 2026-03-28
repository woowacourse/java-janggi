package janggi.domain.route;

import janggi.domain.board.Position;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Path implements Iterable<Position> {

    private final List<Position> positions;

    public Path() {
        this.positions = new ArrayList<>();
    }

    public void makePath(Position nextPosition) {
        positions.add(nextPosition);
    }

    public boolean isEmpty() {
        return positions.isEmpty();
    }

    @Override
    public Iterator<Position> iterator() {
        return positions.iterator();
    }

    @Override
    public String toString() {
        return positions.toString();
    }
}
