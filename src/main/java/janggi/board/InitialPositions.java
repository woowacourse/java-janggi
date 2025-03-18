package janggi.board;

import java.util.List;

public enum InitialPositions {

    CHO_SOLDIER_POSITION(List.of(
            new Position(0, 6),
            new Position(2, 6),
            new Position(4, 6),
            new Position(6, 6),
            new Position(8, 6)
    )),

    HAN_SOLDIER_POSITIONS(List.of(
            new Position(0, 3),
            new Position(2, 3),
            new Position(4, 3),
            new Position(6, 3),
            new Position(8, 3)
    ));

    private final List<Position> positions;

    InitialPositions(List<Position> positions) {
        this.positions = positions;
    }

    public List<Position> getPositions() {
        return positions;
    }
}
