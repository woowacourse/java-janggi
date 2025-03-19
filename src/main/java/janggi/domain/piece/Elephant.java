package janggi.domain.piece;

import java.util.ArrayList;
import java.util.List;

public class Elephant extends Piece {
    private static final List<Position> INITIAL_POSITIONS_BLUE_LEFT = List.of(new Position(10, 3), new Position(10, 2));
    private static final List<Position> INITIAL_POSITIONS_BLUE_RIGHT = List.of(new Position(10, 8), new Position(10, 7));
    private static final List<Position> INITIAL_POSITIONS_RED_LEFT = List.of(new Position(1, 3), new Position(1, 2));
    private static final List<Position> INITIAL_POSITIONS_RED_RIGHT = List.of(new Position(1, 8), new Position(1, 7));

    private Elephant(final Position position, final TeamType teamType) {
        super("상", position, teamType);
    }

    public static List<Piece> createWithInitialPositions(
            TeamType teamType,
            PositionSide leftHorsePosition,
            PositionSide rightHorsePosition) {
        if (teamType.equals(TeamType.BLUE)) {
            return createBlueInitialPositions(leftHorsePosition, rightHorsePosition);
        }
        return createRedInitialPositions(leftHorsePosition, rightHorsePosition);
    }

    private static List<Piece> createBlueInitialPositions(
            PositionSide leftHorsePosition,
            PositionSide rightHorsePosition
    ) {
        List<Piece> elephants = new ArrayList<>();
        elephants.add(new Elephant(INITIAL_POSITIONS_BLUE_LEFT.get(leftHorsePosition.value()), TeamType.BLUE));
        elephants.add(new Elephant(INITIAL_POSITIONS_BLUE_RIGHT.get(rightHorsePosition.value()), TeamType.BLUE));
        return elephants;
    }

    private static List<Piece> createRedInitialPositions(
            PositionSide leftHorsePosition,
            PositionSide rightHorsePosition
    ) {
        List<Piece> elephants = new ArrayList<>();
        elephants.add(new Elephant(INITIAL_POSITIONS_RED_LEFT.get(leftHorsePosition.value()), TeamType.RED));
        elephants.add(new Elephant(INITIAL_POSITIONS_RED_RIGHT.get(rightHorsePosition.value()), TeamType.RED));
        return elephants;
    }
}
