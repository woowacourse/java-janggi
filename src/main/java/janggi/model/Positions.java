package janggi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Positions {
    private final List<Position> positions;

    public Positions(List<Position> positions) {
        this.positions = new ArrayList<>(positions);
    }

    public boolean isEmpty() {
        return positions.isEmpty();
    }

    public Position lastPosition() {
        return positions.getLast();
    }

    public Set<Position> getCornerPositions() {
        if (positions.size() <= 1) {
            return Collections.emptySet();
        }
        return new HashSet<>(positions.subList(0, positions.size() - 1));
    }

    public Set<Position> getAllPositions() {
        return new HashSet<>(positions);
    }
}
