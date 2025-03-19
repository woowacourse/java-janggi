package janggi.domain.piece;

import java.util.ArrayList;
import java.util.List;

public class Horse extends Piece {
    private static final List<Position> INITIAL_POSITIONS_BLUE_LEFT = List.of(new Position(10, 2), new Position(10, 3));
    private static final List<Position> INITIAL_POSITIONS_BLUE_RIGHT = List.of(new Position(10, 7), new Position(10, 8));
    private static final List<Position> INITIAL_POSITIONS_RED_LEFT = List.of(new Position(1, 2), new Position(1, 3));
    private static final List<Position> INITIAL_POSITIONS_RED_RIGHT = List.of(new Position(1, 7), new Position(1, 8));

    private Horse(final Position position, final TeamType teamType) {
        super("마", position, teamType);
    }

    public static List<Piece> createWithInitialPositions(
            final TeamType teamType,
            final PositionSide leftHorsePosition,
            final PositionSide rightHorsePosition) {
        if (teamType.equals(TeamType.BLUE)) {
            return createBlueInitialPositions(leftHorsePosition, rightHorsePosition);
        }
        return createRedInitialPositions(leftHorsePosition, rightHorsePosition);
    }

    private static List<Piece> createBlueInitialPositions(
            final PositionSide leftHorsePosition,
            final PositionSide rightHorsePosition
    ) {
        List<Piece> horses = new ArrayList<>();
        horses.add(new Horse(INITIAL_POSITIONS_BLUE_LEFT.get(leftHorsePosition.value()), TeamType.BLUE));
        horses.add(new Horse(INITIAL_POSITIONS_BLUE_RIGHT.get(rightHorsePosition.value()), TeamType.BLUE));
        return horses;
    }

    private static List<Piece> createRedInitialPositions(
            final PositionSide leftHorsePosition,
            final PositionSide rightHorsePosition
    ) {
        List<Piece> horses = new ArrayList<>();
        horses.add(new Horse(INITIAL_POSITIONS_RED_LEFT.get(leftHorsePosition.value()), TeamType.RED));
        horses.add(new Horse(INITIAL_POSITIONS_RED_RIGHT.get(rightHorsePosition.value()), TeamType.RED));
        return horses;
    }
}
