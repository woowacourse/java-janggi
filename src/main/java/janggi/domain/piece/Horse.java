package janggi.domain.piece;

import janggi.domain.piece.movement.HorseMovement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Horse extends Piece {
    private static final List<Position> INITIAL_POSITIONS_BLUE_LEFT = List.of(
            new Position(10, 2),
            new Position(10, 3)
    );
    private static final List<Position> INITIAL_POSITIONS_BLUE_RIGHT = List.of(
            new Position(10, 7),
            new Position(10, 8)
    );
    private static final List<Position> INITIAL_POSITIONS_RED_LEFT = List.of(
            new Position(1, 2),
            new Position(1, 3)
    );
    private static final List<Position> INITIAL_POSITIONS_RED_RIGHT = List.of(
            new Position(1, 7),
            new Position(1, 8)
    );

    public Horse(final Position position, final Team team) {
        super("마", position, team);
    }

    public static List<Piece> createWithInitialPositions(
            final Team team,
            final HorseSide leftHorsePosition,
            final HorseSide rightHorsePosition) {
        if (team.equals(Team.BLUE)) {
            return createBlueInitialPositions(leftHorsePosition, rightHorsePosition);
        }
        return createRedInitialPositions(leftHorsePosition, rightHorsePosition);
    }

    private static List<Piece> createBlueInitialPositions(
            final HorseSide leftHorsePosition,
            final HorseSide rightHorsePosition
    ) {
        List<Piece> horses = new ArrayList<>();
        horses.add(new Horse(INITIAL_POSITIONS_BLUE_LEFT.get(leftHorsePosition.value()), Team.BLUE));
        horses.add(new Horse(INITIAL_POSITIONS_BLUE_RIGHT.get(rightHorsePosition.value()), Team.BLUE));
        return horses;
    }

    private static List<Piece> createRedInitialPositions(
            final HorseSide leftHorsePosition,
            final HorseSide rightHorsePosition
    ) {
        List<Piece> horses = new ArrayList<>();
        horses.add(new Horse(INITIAL_POSITIONS_RED_LEFT.get(leftHorsePosition.value()), Team.RED));
        horses.add(new Horse(INITIAL_POSITIONS_RED_RIGHT.get(rightHorsePosition.value()), Team.RED));
        return horses;
    }

    @Override
    public Horse move(final Map<Position, Piece> pieces, final Position positionToMove) {
        validateIsSameTeamNotInPositionToMove(pieces, positionToMove);
        validateNothingBetweenPositionToMove(pieces, positionToMove);
        return new Horse(positionToMove, team);
    }

    private void validateNothingBetweenPositionToMove(Map<Position, Piece> pieces, Position positionToMove) {
        HorseMovement horseMovement = HorseMovement.getDirection(
                positionToMove.x() - getPosition().x(),
                positionToMove.y() - getPosition().y()
        );
        Position routePosition = getPosition().plus(horseMovement.getRouteDistance().x(),
                horseMovement.getRouteDistance().y());
        if (None.isNotNone(pieces.get(routePosition))) {
            throw new IllegalArgumentException("불가능한 이동입니다.");
        }
    }
}
