package piece;

import board.Position;
import java.util.List;
import java.util.Map;

public enum PieceType {
    GENERAL(
            List.of(new Position(2, 5)),
            List.of(new Position(9, 5))
    ),
    GUARD(
            List.of(new Position(1, 4), new Position(1, 6)),
            List.of(new Position(10, 4), new Position(10, 6))
    ),
    HORSE(
            List.of(new Position(1, 2), new Position(1, 8)),
            List.of(new Position(10, 3), new Position(10, 7))
    ),
    ELEPHANT(
            List.of(new Position(1, 3), new Position(1, 7)),
            List.of(new Position(10, 2), new Position(10, 8))
    ),
    CHARIOT(
            List.of(new Position(1, 1), new Position(1, 9)),
            List.of(new Position(10, 1), new Position(10, 9))
    ),
    CANNON(
            List.of(new Position(3, 2), new Position(3, 8)),
            List.of(new Position(8, 2), new Position(8, 8))
    ),
    SOLDIER(
            List.of(new Position(4, 1), new Position(4, 3), new Position(4, 5), new Position(4, 7), new Position(4, 9)),
            List.of(new Position(7, 1), new Position(7, 3), new Position(7, 5), new Position(7, 7), new Position(7, 9))
    );

    private final Map<TeamType, List<Position>> initPositions;

    PieceType(final List<Position> redTeam, final List<Position> blueTeam) {
        initPositions = Map.of(
                TeamType.RED, redTeam,
                TeamType.BLUE, blueTeam
        );
    }

    public List<Position> getInitPositions(final TeamType teamType) {
        return initPositions.get(teamType);
    }
}
