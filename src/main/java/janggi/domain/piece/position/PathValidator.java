package janggi.domain.piece.position;

import janggi.domain.piece.direction.Movement;
import janggi.domain.piece.ObstacleTraversalRule;
import janggi.domain.board.Board;

public class PathValidator {

    private final ObstacleTraversalRule obstacleTraversalRule;

    public PathValidator(final ObstacleTraversalRule obstacleTraversalRule) {
        this.obstacleTraversalRule = obstacleTraversalRule;
    }

    public void validatePath(final Position from, final Position to, final boolean doesLiveInPalace, final Board board,
                             final Movement movement) {
        if (doesLiveInPalace) {
            from.validateIsInPalace(to);
        }
        final Path path = movement.makePath(from, to);
        obstacleTraversalRule.validatePathObstacles(path, board);
    }
}
