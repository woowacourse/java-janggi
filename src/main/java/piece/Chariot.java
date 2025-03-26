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
        Direction.getStraightDirection()
                .forEach(direction ->
                        addMovablePositionsInDirection(position, direction, board, movablePositions)
                );
        return movablePositions;
    }

    @Override
    public PieceType getType() {
        return this.pieceType;
    }

    private void addMovablePositionsInDirection(final Position position, final Direction direction, final Board board,
                                                final Set<Position> movablePositions
    ) {
        Position movePosition = position;
        while (true) {
            movePosition = movePosition.moveByDirection(direction);
            if (movePosition.isInValidPosition() || board.isSameTeamPosition(team, movePosition)) {
                break;
            }
            movablePositions.add(movePosition);
            if (board.isExists(movePosition)) {
                break;
            }
        }
    }

}
