package janggi.domain.piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Elephant extends Piece {
    private static final List<Position> INITIAL_POSITIONS_BLUE_LEFT = List.of(new Position(10, 3), new Position(10, 2));
    private static final List<Position> INITIAL_POSITIONS_BLUE_RIGHT = List.of(new Position(10, 8),
            new Position(10, 7));
    private static final List<Position> INITIAL_POSITIONS_RED_LEFT = List.of(new Position(1, 3), new Position(1, 2));
    private static final List<Position> INITIAL_POSITIONS_RED_RIGHT = List.of(new Position(1, 8), new Position(1, 7));

    public Elephant(final Position position, final TeamType teamType) {
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

    public Elephant move(final Map<Position, Piece> pieces, final Position positionToMove) {
        validateIsSameTeamNotInPositionToMove(pieces, positionToMove);
        validateNothingBetweenPositionToMove(pieces, positionToMove);
        return new Elephant(positionToMove, teamType);
    }

    private void validateIsSameTeamNotInPositionToMove(Map<Position, Piece> pieces, Position positionToMove) {
        if (pieces.get(positionToMove).getTeamType().equals(teamType)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateNothingBetweenPositionToMove(Map<Position, Piece> pieces, Position positionToMove) {
        ElephantDirection elephantDirection = ElephantDirection.getDirection(
                positionToMove.x() - getPosition().x(),
                positionToMove.y() - getPosition().y()
        );

        boolean hasPieceOnRoutes = elephantDirection.getRouteDistances().stream()
                .map(routeDistance -> getPosition().plus(routeDistance.x(), routeDistance.y()))
                .anyMatch(position -> None.isNotNone(pieces.get(position)));

        if (hasPieceOnRoutes) {
            throw new IllegalArgumentException();
        }
    }
}
