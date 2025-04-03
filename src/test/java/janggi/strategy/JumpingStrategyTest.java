package janggi.strategy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.direction.Direction;
import janggi.direction.Movement;
import janggi.direction.PieceMoveRule;
import janggi.direction.PieceType;
import janggi.direction.move.EdgeMoveStrategy;
import janggi.direction.obstacle.ObstacleBlockStrategy;
import janggi.direction.obstacle.ObstacleJumpingObstacle;
import janggi.piece.Piece;
import janggi.piece.board.Board;
import janggi.position.Position;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class JumpingStrategyTest {

    private final ObstacleJumpingObstacle jumpingStrategy = new ObstacleJumpingObstacle();
    private final ObstacleBlockStrategy walkingStrategy = new ObstacleBlockStrategy();

    @Test
    void 이동_경로_중간에_기물이_존재하는_경우에만_움직인다() {
        // Given
        final Position currentPosition = new Position(8, 1);
        final Piece cannon = new Piece(new PieceMoveRule(PieceType.CANNON, new EdgeMoveStrategy(), jumpingStrategy));
        final Position soldierPosition = new Position(7, 1);
        final Piece soldier = new Piece(
                new PieceMoveRule(PieceType.HAN_SOLDIER, new EdgeMoveStrategy(), walkingStrategy));

        final Position arrivalPosition = new Position(6, 1);

        // When & Then
        Assertions.assertThatCode(() -> {
            jumpingStrategy.checkObstacle(currentPosition, arrivalPosition, new Movement(Direction.UP, Direction.UP),
                    new Board(Map.of(currentPosition, cannon, soldierPosition, soldier)));
        }).doesNotThrowAnyException();
    }

    @Test
    void 경로상에_두개_이상의_기물이_존재하는_경우_움직일_수_없다() {
        // Given
        final Position currentPosition = new Position(8, 1);
        final Piece cannon = new Piece(new PieceMoveRule(PieceType.CANNON, new EdgeMoveStrategy(), jumpingStrategy));
        final Position soldierPosition = new Position(7, 1);
        final Piece soldier = new Piece(
                new PieceMoveRule(PieceType.HAN_SOLDIER, new EdgeMoveStrategy(), walkingStrategy));
        final Position guardPosition = new Position(6, 1);
        final Piece guard = new Piece(new PieceMoveRule(PieceType.GUARD, new EdgeMoveStrategy(), walkingStrategy));

        final Position arrivalPosition = new Position(5, 1);

        // When & Then
        assertThatThrownBy(
                () -> jumpingStrategy.checkObstacle(currentPosition, arrivalPosition,
                        new Movement(Direction.UP, Direction.UP, Direction.UP),
                        new Board(Map.of(currentPosition, cannon, soldierPosition, soldier, guardPosition, guard))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 오직 하나의 기물만 뛰어넘을 수 있습니다.");
    }

    @Test
    void 같은_전략의_기물을_뛰어넘을_수_없다() {
        // Given
        final Position currentPosition = new Position(8, 1);
        final Piece cannon = new Piece(new PieceMoveRule(PieceType.CANNON, new EdgeMoveStrategy(), jumpingStrategy));
        final Position cannonPosition = new Position(7, 1);
        final Piece cannon2 = new Piece(new PieceMoveRule(PieceType.CANNON, new EdgeMoveStrategy(), jumpingStrategy));
        final Position arrivalPosition = new Position(6, 1);

        // When & Then
        assertThatThrownBy(
                () -> jumpingStrategy.checkObstacle(currentPosition, arrivalPosition,
                        new Movement(Direction.UP, Direction.UP),
                        new Board(Map.of(currentPosition, cannon, cannonPosition, cannon2))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 같은 종류의 기물을 뛰어넘거나 잡을 수 없습니다.");
    }

    @Test
    void 같은_전략의_기물을_잡을_수_없다() {
        // Given
        final Position currentPosition = new Position(8, 1);
        final Piece cannon = new Piece(new PieceMoveRule(PieceType.CANNON, new EdgeMoveStrategy(), jumpingStrategy));
        final Position cannonPosition = new Position(7, 1);
        final Piece cannon2 = new Piece(new PieceMoveRule(PieceType.CANNON, new EdgeMoveStrategy(), jumpingStrategy));
        final Position arrivalPosition = new Position(6, 1);

        // When & Then
        assertThatThrownBy(
                () -> jumpingStrategy.checkObstacle(currentPosition, arrivalPosition,
                        new Movement(Direction.UP, Direction.UP),
                        new Board(Map.of(currentPosition, cannon, cannonPosition, cannon2))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 같은 종류의 기물을 뛰어넘거나 잡을 수 없습니다.");
    }
}
