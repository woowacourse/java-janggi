package domain;

import java.util.List;

public class InitialPositions {

    public static final List<Position> GENERAL_POSITIONS = List.of(new Position(4, 1));
    public static final List<Position> GUARD_POSITIONS = List.of(new Position(3, 0),
            new Position(5, 0));
    public static final List<Position> CANNON_POSITIONS = List.of(new Position(1, 2),
            new Position(7, 2));
    public static final List<Position> SOLDIER_POSITIONS = List.of(new Position(0, 3),
            new Position(2, 3),
            new Position(4, 3), new Position(6, 3), new Position(8, 3));
    public static final List<Position> CHARIOT_POSITIONS = List.of(new Position(0, 0),
            new Position(8, 0));
    public static final List<Position> HORSE_ELEPHANT_POSITIONS = List.of(
            new Position(1, 0),
            new Position(2, 0),
            new Position(6, 0),
            new Position(7, 0)
    );
}
