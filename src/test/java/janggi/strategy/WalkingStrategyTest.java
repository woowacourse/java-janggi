package janggi.strategy;

import janggi.direction.Direction;
import janggi.direction.Movement;
import janggi.direction.PieceMoveRule;
import janggi.direction.PieceType;
import janggi.direction.move.RelativeMoveStrategy;
import janggi.direction.obstacle.ObstacleBlockStrategy;
import janggi.piece.Piece;
import janggi.piece.board.Board;
import janggi.position.Position;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class WalkingStrategyTest {

    private final ObstacleBlockStrategy walkingStrategy = new ObstacleBlockStrategy();

    @Test
    void 정해진_거리만큼_이동한다() {
        // Given
        final Position currentPosition = new Position(3, 3);
        final Position arrivalPosition = new Position(6, 5);
        final Piece piece = new Piece(
                new PieceMoveRule(PieceType.ELEPHANT, new RelativeMoveStrategy(), walkingStrategy));

        // When & Then
        Assertions.assertThatCode(() ->
                walkingStrategy.checkObstacle(currentPosition, arrivalPosition,
                        new Movement(Direction.DOWN, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT),
                        new Board(Map.of(currentPosition, piece)))
        ).doesNotThrowAnyException();
    }
}
