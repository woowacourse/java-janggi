package domain.board;

import java.util.List;

public enum InitialPosition {
    SOLDIER(
            List.of(new Position(0, 3), new Position(2, 3), new Position(4, 3), new Position(6, 3), new Position(8, 3)),
            List.of(new Position(0, 6), new Position(2, 6), new Position(4, 6), new Position(6, 6), new Position(8, 6))
    ),
    GUARD(
            List.of(new Position(3, 0), new Position(5, 0)),
            List.of(new Position(3, 9), new Position(5, 9))
    ),
    ELEPHANT_AND_HORSE(
            List.of(new Position(1, 0), new Position(2, 0), new Position(6, 0), new Position(7, 0)),
            List.of(new Position(1, 9), new Position(2, 9), new Position(6, 9), new Position(7, 9))
    ),
    CANNON(
            List.of(new Position(1, 2), new Position(7, 2)),
            List.of(new Position(1, 7), new Position(7, 7))
    ),
    CHARIOT(
            List.of(new Position(0, 0), new Position(8, 0)),
            List.of(new Position(0, 9), new Position(8, 9))
    ),
    GENERAL(
            List.of(new Position(4, 1)),
            List.of(new Position(4, 8))
    );

    private final List<Position> choPositions;
    private final List<Position> hanPositions;

    InitialPosition(List<Position> choPositions, List<Position> hanPositions) {
        this.choPositions = choPositions;
        this.hanPositions = hanPositions;
    }

    public List<Position> getChoPositions() {
        return choPositions;
    }

    public List<Position> getHanPositions() {
        return hanPositions;
    }
}
