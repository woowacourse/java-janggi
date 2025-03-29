package janggi.strategy;

import janggi.direction.Direction;
import janggi.direction.Movement;
import janggi.direction.PieceMoveRule;
import janggi.direction.PieceType;
import janggi.board.Board;
import janggi.piece.Piece;
import janggi.position.Position;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class WalkingStrategyTest {

    private final ObstacleBlockStrategy walkingStrategy = new ObstacleBlockStrategy();

    @Test
    void 정해진_거리만큼_이동한다() {
        // Given
        final Position currentPosition = new Position(3, 3);
        final Position arrivalPosition = new Position(6, 5);
        final Piece piece = new Piece(new PieceMoveRule(PieceType.ELEPHANT, walkingStrategy), currentPosition);

        // When & Then
        Assertions.assertThatCode(() ->
                walkingStrategy.checkObstacle(currentPosition, arrivalPosition,
                        new Movement(Direction.DOWN, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT),
                        Board.from(Set.of(piece)))
        ).doesNotThrowAnyException();
    }
}
