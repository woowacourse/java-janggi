package janggi.direction;

import janggi.direction.move.MoveStrategy;
import janggi.direction.obstacle.ObstacleMoveStrategy;
import janggi.piece.board.Board;
import janggi.position.Position;

public class PieceMoveRule {

    protected final PieceType pieceType;
    private final MoveStrategy moveStrategy;
    private final ObstacleMoveStrategy obstacleMoveStrategy;

    public PieceMoveRule(final PieceType pieceType, final MoveStrategy moveStrategy,
                         final ObstacleMoveStrategy givenObstacleMoveStrategy) {
        this.pieceType = pieceType;
        this.moveStrategy = moveStrategy;
        this.obstacleMoveStrategy = givenObstacleMoveStrategy;
    }

    public void validatePath(final Position currentPosition, final Position arrivalPosition, final Board board) {
        if (pieceType.doesLiveInPalace()) {
            currentPosition.validateIsInPalace(arrivalPosition);
        }
        final Movement movement = moveStrategy.move(currentPosition, arrivalPosition, pieceType);
        obstacleMoveStrategy.checkObstacle(currentPosition, arrivalPosition, movement, board);
    }

    public PieceType getPieceType() {
        return pieceType;
    }
}
