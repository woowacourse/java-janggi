package janggi.domain.piece;

import janggi.domain.piece.movement.ElephantMovement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Elephant extends Piece {
    private static final List<Position> INITIAL_POSITIONS_BLUE_LEFT = List.of(
            new Position(10, 3),
            new Position(10, 2)
    );
    private static final List<Position> INITIAL_POSITIONS_BLUE_RIGHT = List.of(
            new Position(10, 8),
            new Position(10, 7)
    );
    private static final List<Position> INITIAL_POSITIONS_RED_LEFT = List.of(
            new Position(1, 3),
            new Position(1, 2)
    );
    private static final List<Position> INITIAL_POSITIONS_RED_RIGHT = List.of(
            new Position(1, 8),
            new Position(1, 7)
    );

    public Elephant(final Position position, final Team team) {
        super("상", position, team);
    }

    public static List<Piece> createWithInitialPositions(
            Team team,
            HorseSide leftHorsePosition,
            HorseSide rightHorsePosition) {
        if (team.equals(Team.BLUE)) {
            return createBlueInitialPositions(leftHorsePosition, rightHorsePosition);
        }
        return createRedInitialPositions(leftHorsePosition, rightHorsePosition);
    }

    private static List<Piece> createBlueInitialPositions(
            HorseSide leftHorsePosition,
            HorseSide rightHorsePosition
    ) {
        List<Piece> elephants = new ArrayList<>();
        elephants.add(new Elephant(INITIAL_POSITIONS_BLUE_LEFT.get(leftHorsePosition.value()), Team.BLUE));
        elephants.add(new Elephant(INITIAL_POSITIONS_BLUE_RIGHT.get(rightHorsePosition.value()), Team.BLUE));
        return elephants;
    }

    private static List<Piece> createRedInitialPositions(
            HorseSide leftHorsePosition,
            HorseSide rightHorsePosition
    ) {
        List<Piece> elephants = new ArrayList<>();
        elephants.add(new Elephant(INITIAL_POSITIONS_RED_LEFT.get(leftHorsePosition.value()), Team.RED));
        elephants.add(new Elephant(INITIAL_POSITIONS_RED_RIGHT.get(rightHorsePosition.value()), Team.RED));
        return elephants;
    }

    @Override
    public Elephant move(final Map<Position, Piece> pieces, final Position positionToMove) {
        validateIsSameTeamNotInPositionToMove(pieces, positionToMove);
        validateNothingBetweenPositionToMove(pieces, positionToMove);
        return new Elephant(positionToMove, team);
    }

    private void validateNothingBetweenPositionToMove(Map<Position, Piece> pieces, Position positionToMove) {
        ElephantMovement elephantMovement = ElephantMovement.getDirection(
                positionToMove.x() - getPosition().x(),
                positionToMove.y() - getPosition().y()
        );

        boolean hasPieceOnRoutes = elephantMovement.getRouteDistances().stream()
                .map(routeDistance -> getPosition().plus(routeDistance.x(), routeDistance.y()))
                .anyMatch(position -> None.isNotNone(pieces.get(position)));

        if (hasPieceOnRoutes) {
            throw new IllegalArgumentException("불가능한 이동입니다.");
        }
    }
}
