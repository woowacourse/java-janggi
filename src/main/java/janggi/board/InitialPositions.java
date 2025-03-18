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
    )),
    CHO_ELEPHANT_POSITIONS(List.of(
            new Position(2, 9),
            new Position(6, 9)
    )),
    HAN_ELEPHANT_POSITIONS(List.of(
            new Position(2, 0),
            new Position(6, 0)
    )),
    CHO_HORSE_POSITIONS(List.of(
            new Position(1, 9),
            new Position(7, 9)
    )),
    HAN_HORSE_POSITIONS(List.of(
            new Position(1, 0),
            new Position(7, 0)
    )),
    CHO_CANNON_POSITIONS(List.of(
            new Position(1, 7),
            new Position(7, 7)
    )),
    HAN_CANNON_POSITIONS(List.of(
            new Position(1, 2),
            new Position(7, 2)
    )),
    CHO_CHARIOT_POSITIONS(List.of(
            new Position(0, 9),
            new Position(8, 9)
    )),
    HAN_CHARIOT_POSITIONS(List.of(
            new Position(0, 0),
            new Position(8, 0)
    )),
    CHO_GUARD_POSITIONS(List.of(
            new Position(3, 9),
            new Position(5, 9)
    )),
    HAN_GUARD_POSITIONS(List.of(
            new Position(3, 0),
            new Position(5, 0)
    ));


    private final List<Position> positions;

    InitialPositions(List<Position> positions) {
        this.positions = positions;
    }

    public List<Position> getPositions() {
        return positions;
    }
}
