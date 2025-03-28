package piece;

import java.util.HashSet;
import java.util.Set;

import board.Board;
import board.Position;

public class Chariot extends Piece {

    public Chariot(final Team team) {
        super(team, PieceType.CHARIOT);
    }

    @Override
    public Set<Position> getMovablePositions(final Position position, final Board board) {
        Set<Position> movablePositions = new HashSet<>();
        for (Direction straightDirection : Direction.getStraightDirection()) {
            movablePositions.addAll(findMovablePositionInDirection(position, straightDirection, board));
        }
        return movablePositions;
    }

    private Set<Position> findMovablePositionInDirection(
            final Position position, final Direction direction, final Board board
    ) {
        Set<Position> movablePositionsInDirection = new HashSet<>();
        Position movePosition = position.moveByDirection(direction);
        while (!isBlockedPosition(board, movePosition)) {
            movablePositionsInDirection.add(movePosition);
            if (board.isExists(movePosition)) {
                break;
            }
            movePosition = movePosition.moveByDirection(direction);
        }
        return movablePositionsInDirection;
    }

    @Override
    public PieceType getType() {
        return this.pieceType;
    }

    private boolean isBlockedPosition(final Board board, final Position position) {
        return position.isInValidPosition() || board.isSameTeamPosition(team, position);
    }

}
