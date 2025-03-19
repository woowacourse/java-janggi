package janggi.piece;

import java.util.ArrayList;
import java.util.List;

public class Elephant extends Piece {
    private static final List<Position> INITIAL_POSITIONS_BLUE_LEFT = List.of(new Position(0, 2), new Position(0, 3));
    private static final List<Position> INITIAL_POSITIONS_BLUE_RIGHT = List.of(new Position(0, 7), new Position(0, 8));
    private static final List<Position> INITIAL_POSITIONS_RED_LEFT = List.of(new Position(1, 2), new Position(1, 3));
    private static final List<Position> INITIAL_POSITIONS_RED_RIGHT = List.of(new Position(1, 7), new Position(1, 8));

    private Elephant(final Position position, final TeamType teamType) {
        super(position, teamType);
    }

    public static List<Piece> createWithInitialPositions(
            TeamType teamType,
            PositionSide elephantLeftPosition,
            PositionSide elephantRightPosition) {
        if (teamType.equals(TeamType.BLUE)) {
            return createBlueInitialPositions(elephantLeftPosition, elephantRightPosition);
        }
        return createRedInitialPositions(elephantLeftPosition, elephantRightPosition);
    }

    private static List<Piece> createBlueInitialPositions(
            PositionSide elephantLeftPosition,
            PositionSide elephantRightPosition
    ) {
        List<Piece> elephants = new ArrayList<>();
        elephants.add(new Elephant(INITIAL_POSITIONS_BLUE_LEFT.get(elephantLeftPosition.value()), TeamType.BLUE));
        elephants.add(new Elephant(INITIAL_POSITIONS_BLUE_RIGHT.get(elephantRightPosition.value()), TeamType.BLUE));
        return elephants;
    }

    private static List<Piece> createRedInitialPositions(
            PositionSide elephantLeftPosition,
            PositionSide elephantRightPosition
    ) {
        List<Piece> elephants = new ArrayList<>();
        elephants.add(new Elephant(INITIAL_POSITIONS_RED_LEFT.get(elephantLeftPosition.value()), TeamType.RED));
        elephants.add(new Elephant(INITIAL_POSITIONS_RED_RIGHT.get(elephantRightPosition.value()), TeamType.RED));
        return elephants;
    }
}
