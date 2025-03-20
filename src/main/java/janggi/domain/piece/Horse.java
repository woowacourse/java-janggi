package janggi.domain.piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Horse extends Piece {
    private static final List<Position> INITIAL_POSITIONS_BLUE_LEFT = List.of(new Position(10, 2), new Position(10, 3));
    private static final List<Position> INITIAL_POSITIONS_BLUE_RIGHT = List.of(new Position(10, 7), new Position(10, 8));
    private static final List<Position> INITIAL_POSITIONS_RED_LEFT = List.of(new Position(1, 2), new Position(1, 3));
    private static final List<Position> INITIAL_POSITIONS_RED_RIGHT = List.of(new Position(1, 7), new Position(1, 8));

    public Horse(final Position position, final TeamType teamType) {
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

    public Horse move(final Map<Position, Piece> pieces, final Position positionToMove) {
        validateIsSameTeamNotInPositionToMove(pieces, positionToMove);
        validateNothingBetweenPositionToMove(pieces, positionToMove);
        return new Horse(positionToMove, teamType);
    }

    private void validateIsSameTeamNotInPositionToMove(Map<Position, Piece> pieces, Position positionToMove) {
        if (pieces.get(positionToMove).getTeamType().equals(TeamType.BLUE)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateNothingBetweenPositionToMove(Map<Position, Piece> pieces, Position positionToMove) {
        HorseDirection horseDirection = HorseDirection.getDirection(
                positionToMove.x() - getPosition().x(),
                positionToMove.y() - getPosition().y()
        );
        Position routePosition = getPosition().plus(horseDirection.getRouteDistance().x(), horseDirection.getRouteDistance().y());
        if (None.isNotNone(pieces.get(routePosition))) {
            throw new IllegalArgumentException();
        }
    }
}
