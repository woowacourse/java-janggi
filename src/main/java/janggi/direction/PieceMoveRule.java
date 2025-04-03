package janggi.direction;

import janggi.direction.move.MoveStrategy;
import janggi.direction.obstacle.ObstacleStrategy;
import janggi.piece.board.Board;
import janggi.position.Position;

public class PieceMoveRule {

    private final PieceType pieceType;
    private final MoveStrategy moveStrategy;
    private final ObstacleStrategy obstacleStrategy;

    public PieceMoveRule(final PieceType pieceType, final MoveStrategy moveStrategy,
                         final ObstacleStrategy givenObstacleStrategy) {
        this.pieceType = pieceType;
        this.moveStrategy = moveStrategy;
        this.obstacleStrategy = givenObstacleStrategy;
    }

    public void validatePath(final Position currentPosition, final Position arrivalPosition, final Board board) {
        if (pieceType.doesLiveInPalace()) {
            currentPosition.validateIsInPalace(arrivalPosition);
        }
        final Movement movement = moveStrategy.move(currentPosition, arrivalPosition, pieceType);
        obstacleStrategy.checkObstacle(currentPosition, arrivalPosition, movement, board);
    }

    public boolean isObstacleJumping() {
        return obstacleStrategy.isObstacleJumping();
    }

    public PieceType getPieceType() {
        return pieceType;
    }
}
