package piece;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import board.Board;
import board.Position;

public class Cannon extends Piece {

    public Cannon(final Team team) {
        super(team, PieceType.CANNON);
    }

    @Override
    protected Set<Position> getMovablePositions(final Position position, final Board board) {
        Map<Direction, Position> hurdlePositions = findHurdlePositions(position, board);
        if (hurdlePositions.isEmpty()) {
            return Collections.emptySet();
        }
        return findMovablePositions(board, hurdlePositions);
    }

    @Override
    public PieceType getType() {
        return this.pieceType;
    }

    private Map<Direction, Position> findHurdlePositions(final Position position, final Board board) {
        Map<Direction, Position> hurdlePositions = new EnumMap<>(Direction.class);
        for (Direction straightDirection : Direction.getStraightDirection()) {
            Position hurdlePosition = findHurdlePosition(position, board, straightDirection);
            if (hasHurdle(position, hurdlePosition)) {
                hurdlePositions.put(straightDirection, hurdlePosition);
            }
        }
        return hurdlePositions;
    }

    private boolean hasHurdle(final Position position, final Position hurdlePosition) {
        return !position.equals(hurdlePosition);
    }

    private Position findHurdlePosition(final Position position, final Board board, final Direction straightDirection) {
        Position movePosition = position.moveByDirection(straightDirection);
        while (!isBlockedPosition(board, movePosition)) {
            if (board.isExists(movePosition)) {
                return movePosition;
            }
            movePosition = movePosition.moveByDirection(straightDirection);
        }
        return position;
    }

    private Set<Position> findMovablePositions(final Board board, final Map<Direction, Position> hurdlePositions) {
        Set<Position> movablePositions = new HashSet<>();
        for (Direction direction : hurdlePositions.keySet()) {
            Position hurdlePosition = hurdlePositions.get(direction);
            Position startPosition = hurdlePosition.moveByDirection(direction);
            movablePositions.addAll(findMovablePositionsEachDirection(board, startPosition, direction));
        }
        return movablePositions;
    }

    private Set<Position> findMovablePositionsEachDirection(
            final Board board, Position movablePosition, final Direction direction
    ) {
        Set<Position> movablePositionsEachDirection = new HashSet<>();
        while (!isMovablePosition(board, movablePosition, team)) {
            movablePositionsEachDirection.add(movablePosition);
            if (board.isExists(movablePosition)) {
                break;
            }
            movablePosition = movablePosition.moveByDirection(direction);
        }
        return movablePositionsEachDirection;
    }

    private boolean isMovablePosition(final Board board, final Position position, final Team team) {
        return isBlockedPosition(board, position) || board.isSameTeamPosition(team, position);
    }

    private boolean isBlockedPosition(final Board board, final Position movePosition) {
        return movePosition.isInValidPosition() || board.isCannonPosition(movePosition);
    }

}
