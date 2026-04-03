package janggi.domain.path;

import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Path implements Iterable<Position> {

    private final List<Position> positions;

    public Path() {
        this.positions = new ArrayList<>();
    }

    public void add(Position target) {
        positions.add(target);
    }

    @Override
    public Iterator<Position> iterator() {
        return positions.iterator();
    }
}
