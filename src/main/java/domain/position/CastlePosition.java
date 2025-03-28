package domain.position;

import domain.direction.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CastlePosition extends JanggiPosition {
    private final List<Direction> directions;

    protected CastlePosition(int row, int col, Direction... linked) {
        super(row, col);
        this.directions = new ArrayList<>(defaultDirections);
        this.directions.addAll(List.of(linked));
    }

    @Override
    public final boolean isCastle() {
        return true;
    }

    @Override
    protected List<Direction> getLinkedDirections() {
        return Collections.unmodifiableList(directions);
    }
}
