package janggi.strategy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

class JumpingStrategyTest {

    private final ObstacleJumpingObstacle jumpingStrategy = new ObstacleJumpingObstacle();
    private final ObstacleBlockStrategy walkingStrategy = new ObstacleBlockStrategy();

    @Test
    void 이동_경로_중간에_기물이_존재하는_경우에만_움직인다() {
        // Given
        final Position currentPosition = new Position(8, 1);
        final Piece cannon = new Piece(new PieceMoveRule(PieceType.CANNON, jumpingStrategy), currentPosition);
        final Piece soldier = new Piece(new PieceMoveRule(PieceType.HAN_SOLDIER, walkingStrategy),
                new Position(7, 1));

        final Position arrivalPosition = new Position(6, 1);

        // When & Then
        Assertions.assertThatCode(() -> {
            jumpingStrategy.checkObstacle(currentPosition, arrivalPosition, new Movement(Direction.UP, Direction.UP),
                    Board.from(Set.of(cannon, soldier)));
        }).doesNotThrowAnyException();
    }

    @Test
    void 경로상에_두개_이상의_기물이_존재하는_경우_움직일_수_없다() {
        // Given
        final Position currentPosition = new Position(8, 1);
        final Piece cannon = new Piece(new PieceMoveRule(PieceType.CANNON, jumpingStrategy), currentPosition);
        final Piece soldier = new Piece(new PieceMoveRule(PieceType.HAN_SOLDIER, walkingStrategy),
                new Position(7, 1));
        final Piece guard = new Piece(new PieceMoveRule(PieceType.GUARD, walkingStrategy), new Position(6, 1));

        final Position arrivalPosition = new Position(5, 1);

        // When & Then
        assertThatThrownBy(
                () -> jumpingStrategy.checkObstacle(currentPosition, arrivalPosition,
                        new Movement(Direction.UP, Direction.UP, Direction.UP),
                        Board.from(Set.of(cannon, soldier, guard))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 오직 하나의 기물만 뛰어넘을 수 있습니다.");
    }

    @Test
    void 같은_전략의_기물을_뛰어넘을_수_없다() {
        // Given
        final Position currentPosition = new Position(8, 1);
        final Piece cannon = new Piece(new PieceMoveRule(PieceType.CANNON, jumpingStrategy), currentPosition);
        final Piece cannon2 = new Piece(new PieceMoveRule(PieceType.CANNON, jumpingStrategy),
                new Position(7, 1));
        final Position arrivalPosition = new Position(6, 1);

        // When & Then
        assertThatThrownBy(
                () -> jumpingStrategy.checkObstacle(currentPosition, arrivalPosition,
                        new Movement(Direction.UP, Direction.UP),
                        Board.from(Set.of(cannon, cannon2))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 같은 종류의 기물을 뛰어넘거나 잡을 수 없습니다.");
    }

    @Test
    void 같은_전략의_기물을_잡을_수_없다() {
        // Given
        final Position currentPosition = new Position(8, 1);
        final Piece cannon = new Piece(new PieceMoveRule(PieceType.CANNON, jumpingStrategy), currentPosition);
        final Piece cannon2 = new Piece(new PieceMoveRule(PieceType.CANNON, jumpingStrategy),
                new Position(7, 1));
        final Position arrivalPosition = new Position(6, 1);

        // When & Then
        assertThatThrownBy(
                () -> jumpingStrategy.checkObstacle(currentPosition, arrivalPosition,
                        new Movement(Direction.UP, Direction.UP),
                        Board.from(Set.of(cannon, cannon2))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 같은 종류의 기물을 뛰어넘거나 잡을 수 없습니다.");
    }
}
