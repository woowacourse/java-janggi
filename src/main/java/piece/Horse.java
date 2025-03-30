package piece;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import board.Board;
import board.Position;
import piece.movement.Movement;

public class Horse extends Piece {

    private static final List<Movement> MOVEMENTS = List.of(
            Movement.TOP_LEFT_TOP,
            Movement.TOP_RIGHT_TOP,
            Movement.BOTTOM_LEFT_BOTTOM,
            Movement.BOTTOM_RIGHT_BOTTOM,
            Movement.LEFT_LEFT_TOP,
            Movement.LEFT_LEFT_BOTTOM,
            Movement.RIGHT_RIGHT_TOP,
            Movement.RIGHT_RIGHT_BOTTOM
    );

    public Horse(final Team team) {
        super(team, PieceType.HORSE);
    }

    @Override
    protected Set<Position> getMovablePositions(final Position position, final Board board) {
        Set<Position> movablePositions = new HashSet<>();
        for (Movement movement : MOVEMENTS) {
            List<Position> routePositions = movement.applyMovement(position);
            if (canApplyMovement(board, routePositions)) {
                movablePositions.add(routePositions.getLast());
            }
        }
        return movablePositions;
    }

    private boolean canApplyMovement(final Board board, final List<Position> routePositions) {
        for (int routeIndex = 0; routeIndex < routePositions.size() - 1; routeIndex++) {
            Position routePosition = routePositions.get(routeIndex);
            if (hasObstacle(board, routePosition)) {
                return false;
            }
        }
        Position destination = routePositions.getLast();
        return canMoveDestination(board, destination);
    }

    private boolean hasObstacle(final Board board, final Position position) {
        return position.isInValidPosition() || board.isExists(position);
    }

    private boolean canMoveDestination(final Board board, final Position position) {
        return !position.isInValidPosition() && !board.isSameTeamPosition(team, position);
    }

    @Override
    public PieceType getType() {
        return this.pieceType;
    }

}
