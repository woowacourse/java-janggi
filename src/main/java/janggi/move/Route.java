package janggi.move;

import janggi.board.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Route {

    private final List<Position> positions;

    public Route() {
        this.positions = new ArrayList<>();
    }

    public Route(final Position position) {
        this.positions = new ArrayList<>();
        addRoute(position);
    }

    public void addRoute(final Position position) {
        positions.add(position);
    }

    public Position getLastPosition() {
        return positions.getLast();
    }

    public List<Position> getIntermediatePositions() {
        return positions.subList(0, positions.size() - 1);
    }

    public void deleteFirstPosition() {
        positions.removeFirst();
    }

    public List<Position> getPositions() {
        return Collections.unmodifiableList(positions);
    }

}
