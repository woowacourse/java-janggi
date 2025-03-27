package janggi.domain.piece;

import janggi.domain.piece.movement.Movement;
import java.util.List;
import java.util.Map;

final public class Validator {

    public static void validateStraightMovement(final Position beforePosition, final Position afterPosition) {
        if (!isStraightMovement(beforePosition, afterPosition)) {
            throw new IllegalArgumentException("불가능한 이동입니다.");
        }
    }

    private static boolean isStraightMovement(final Position beforePosition, final Position afterPosition) {
        return afterPosition.x() == beforePosition.x() || afterPosition.y() == beforePosition.y();
    }

    public static void validateDestinationNotCannon(final Map<Position, Piece> board, final Position afterPosition) {
        if (board.get(afterPosition).isCannon()) {
            throw new IllegalArgumentException("불가능한 이동입니다.");
        }
    }

    public static void validateCannonJumpRule(
            final Map<Position, Piece> board,
            final Position beforePosition,
            final Position afterPosition) {

        int obstaclesCount = countObstaclesOnPath(board, beforePosition, afterPosition);

        if (obstaclesCount != 1) {
            throw new IllegalArgumentException("포는 반드시 하나의 장애물을 넘어야 합니다.");
        }
    }

    private static int countObstaclesOnPath(
            final Map<Position, Piece> board,
            final Position beforePosition,
            final Position afterPosition) {

        Movement nextMovement = Movement.findStraightUnitMovement(
                afterPosition.x() - beforePosition.x(),
                afterPosition.y() - beforePosition.y()
        );

        int obstaclesCount = 0;
        Position currentPosition = beforePosition.plus(nextMovement.x(), nextMovement.y());

        while (!currentPosition.equals(afterPosition)) {
            validateNoCannonOnPath(board, currentPosition);

            if (!board.get(currentPosition).isNone()) {
                obstaclesCount++;
            }
            currentPosition = currentPosition.plus(nextMovement.x(), nextMovement.y());
        }
        return obstaclesCount;
    }

    private static void validateNoCannonOnPath(final Map<Position, Piece> pieces, final Position position) {
        if (pieces.get(position).isCannon()) {
            throw new IllegalArgumentException("불가능한 이동입니다");
        }
    }

    public static void validateNotMovingTowardsOwnSide(final Team team, final Position beforePosition,
                                                       final Position afterPosition) {
        if (team == Team.BLUE && afterPosition.x() - beforePosition.x() > 0) {
            throw new IllegalArgumentException("청팀: 아래 방향으로 이동할 수 없는 기물입니다.");
        }
        if (team == Team.RED && afterPosition.x() - beforePosition.x() < 0) {
            throw new IllegalArgumentException("홍팀: 윗 방향으로 이동할 수 없는 기물입니다.");
        }
    }

    public static void validateSingleStepMovement(final Position beforePosition, final Position afterPosition) {
        if (!isSingleStepMovement(beforePosition, afterPosition)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
    }

    private static boolean isSingleStepMovement(final Position beforePosition, final Position afterPosition) {
        return Math.abs(afterPosition.x() - beforePosition.x()) + Math.abs(afterPosition.y() - beforePosition.y()) == 1;
    }

    public static void validateNoSameTeamPieceAt(
            final Team team,
            final Map<Position, Piece> board,
            final Position afterPosition) {
        Piece existingPiece = board.get(afterPosition);
        if (existingPiece.getTeam().equals(team)) {
            throw new IllegalArgumentException("같은 팀 기물이 있는 위치로는 이동할 수 없습니다.");
        }
    }

    public static void validateNoObstaclesOnPath(
            final Map<Position, Piece> board,
            final Position beforePosition,
            final Position afterPosition
    ) {
        Movement nextMovement = Movement.findStraightUnitMovement(
                afterPosition.x() - beforePosition.x(),
                afterPosition.y() - beforePosition.y()
        );

        Position currentPosition = beforePosition.plus(nextMovement.x(), nextMovement.y());
        while (!currentPosition.equals(afterPosition)) {
            if (!board.get(currentPosition).isNone()) {
                throw new IllegalArgumentException("불가능한 이동입니다");
            }
            currentPosition = currentPosition.plus(nextMovement.x(), nextMovement.y());
        }
    }

    public static void validateNoObstaclesOnPath(final Map<Position, Piece> board, final Position beforePosition,
                                                 final List<Movement> pathMovements) {
        boolean hasObstacle = pathMovements.stream()
                .map(routeDistance -> beforePosition.plus(routeDistance.x(), routeDistance.y()))
                .anyMatch(position -> !board.get(position).isNone());

        if (hasObstacle) {
            throw new IllegalArgumentException("불가능한 이동입니다.");
        }
    }
}
