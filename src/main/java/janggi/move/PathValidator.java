package janggi.move;

import janggi.piece.board.Board;
import janggi.position.Path;
import janggi.position.Position;

public class PathValidator {

    private final Piece piece;
    private final ObstacleStrategy obstacleStrategy;

    public PathValidator(final Piece piece, final ObstacleStrategy obstacleStrategy) {
        this.piece = piece;
        this.obstacleStrategy = obstacleStrategy;
    }

    public void validatePath(final Position from, final Position to, final Board board, final Movement movement) {
        if (piece.doesLiveInPalace()) {
            from.validateIsInPalace(to);
        }
        final Path path = movement.makePath(from, to);
        obstacleStrategy.checkObstacle(path, board);
    }
}
