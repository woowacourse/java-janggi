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

    public static List<Elephant> createWithInitialPositions(
            TeamType teamType,
            PositionSide horseLeftPosition,
            PositionSide horseRightPosition) {
        if (teamType.equals(TeamType.BLUE)) {
            return createBlueInitialPositions(horseLeftPosition, horseRightPosition);
        }
        return createRedInitialPositions(horseLeftPosition, horseRightPosition);
    }

    private static List<Elephant> createBlueInitialPositions(
            PositionSide horseLeftPosition,
            PositionSide horseRightPosition
    ) {
        List<Elephant> elephants = new ArrayList<>();
        elephants.add(new Elephant(INITIAL_POSITIONS_BLUE_LEFT.get(horseLeftPosition.value()), TeamType.BLUE));
        elephants.add(new Elephant(INITIAL_POSITIONS_BLUE_RIGHT.get(horseRightPosition.value()), TeamType.BLUE));
        return elephants;
    }

    private static List<Elephant> createRedInitialPositions(
            PositionSide horseLeftPosition,
            PositionSide horseRightPosition
    ) {
        List<Elephant> elephants = new ArrayList<>();
        elephants.add(new Elephant(INITIAL_POSITIONS_RED_LEFT.get(horseLeftPosition.value()), TeamType.RED));
        elephants.add(new Elephant(INITIAL_POSITIONS_RED_RIGHT.get(horseRightPosition.value()), TeamType.RED));
        return elephants;
    }
}
