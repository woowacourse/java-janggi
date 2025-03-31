package janggi.domain.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum PalacePoint {

    PALACE_POSITIONS(Arrays.asList(
            new Position(3, 0), new Position(4, 0), new Position(5, 0),
            new Position(3, 1), new Position(4, 1), new Position(5, 1),
            new Position(3, 2), new Position(4, 2), new Position(5, 2),
            new Position(3, 9), new Position(4, 9), new Position(5, 9),
            new Position(3, 8), new Position(4, 8), new Position(5, 8),
            new Position(3, 7), new Position(4, 7), new Position(5, 7)
    )),
    DIAGONAL_MOVABLE_POSITIONS(Arrays.asList(
            new Position(3, 0), new Position(5, 0),
            new Position(4, 1),
            new Position(3, 2), new Position(5, 2),
            new Position(3, 9), new Position(5, 9),
            new Position(4, 8),
            new Position(3, 7), new Position(5, 7)
    ));

    private final List<Position> positions;

    PalacePoint(final List<Position> positions) {
        this.positions = positions;
    }

    public boolean contains(final Position position) {
        return positions.contains(position);
    }

    public List<Position> getPositions() {
        return Collections.unmodifiableList(positions);
    }
}
