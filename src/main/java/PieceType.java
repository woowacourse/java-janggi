import java.util.Collections;
import java.util.List;

public enum PieceType {
    GENERAL(
            List.of(
                    new Position(9, 5)),
            List.of(
                    new Position(2, 5))
    ),
    GUARD(
            List.of(
                    new Position(10, 4),
                    new Position(10, 6)),
            List.of(
                    new Position(1, 4),
                    new Position(1, 6))
    ),
    CHARIOT(
            List.of(
                    new Position(10, 1),
                    new Position(10, 9)),
            List.of(
                    new Position(1, 1),
                    new Position(1, 9))),
    CANNON(
            List.of(
                    new Position(8, 2),
                    new Position(8, 8)),
            List.of(
                    new Position(3, 2),
                    new Position(3, 8))),
    SOLDIER(
            List.of(
                    new Position(7, 1),
                    new Position(7, 3),
                    new Position(7, 5),
                    new Position(7, 7),
                    new Position(7, 9)),
            List.of(
                    new Position(4, 1),
                    new Position(4, 3),
                    new Position(4, 5),
                    new Position(4, 7),
                    new Position(4, 9))),
    EMPTY(List.of(), List.of());

    private final List<Position> blueInitialPositions;
    private final List<Position> redInitialPositions;

    PieceType(List<Position> blueInitialPositions, List<Position> redInitialPositions) {
        this.blueInitialPositions = blueInitialPositions;
        this.redInitialPositions = redInitialPositions;
    }

    public List<Position> getInitialPositions(TeamType teamType) {
        if(teamType.equals(TeamType.RED)) {
            return Collections.unmodifiableList(redInitialPositions);
        }
        return Collections.unmodifiableList(blueInitialPositions);
    }
}
