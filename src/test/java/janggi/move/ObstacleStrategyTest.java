package janggi.move;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.piece.board.Board;
import janggi.position.Path;
import janggi.position.Position;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ObstacleStrategyTest {

    @Nested
    class ObstacleJumpingStrategyTest {

        private final ObstacleStrategy jumpingStrategy = ObstacleStrategy.JUMPING;

        @Test
        void 이동_경로_중간에_기물이_존재하는_경우에만_움직인다() {
            // Given
            final Position currentPosition = new Position(8, 1);
            final Piece cannon = Piece.CANNON;
            final Position soldierPosition = new Position(7, 1);
            final Piece soldier = Piece.HAN_SOLDIER;
            final Position arrivalPosition = new Position(6, 1);
            final Movement movement = new Movement(Direction.UP, Direction.UP);
            final Path path = movement.makePath(currentPosition, arrivalPosition);

            // When & Then
            Assertions.assertThatCode(() -> {
                jumpingStrategy.checkObstacle(path,
                        new Board(Map.of(currentPosition, cannon, soldierPosition, soldier)));
            }).doesNotThrowAnyException();
        }

        @Test
        void 경로상에_두개_이상의_기물이_존재하는_경우_움직일_수_없다() {
            // Given
            final Position currentPosition = new Position(8, 1);
            final Position soldierPosition = new Position(7, 1);
            final Position guardPosition = new Position(6, 1);

            final Position arrivalPosition = new Position(5, 1);
            final Movement movement = new Movement(Direction.UP, Direction.UP, Direction.UP);
            final Path path = movement.makePath(currentPosition, arrivalPosition);

            // When & Then
            assertThatThrownBy(
                    () -> {
                        jumpingStrategy.checkObstacle(path,
                                new Board(Map.of(currentPosition, Piece.CANNON, soldierPosition, Piece.CHO_SOLDIER,
                                        guardPosition, Piece.GUARD)));
                    })
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("[ERROR] 오직 하나의 기물만 뛰어넘을 수 있습니다.");
        }

        @Test
        void 같은_전략의_기물을_뛰어넘을_수_없다() {
            // Given
            final Position currentPosition = new Position(8, 1);
            final Position cannonPosition = new Position(7, 1);
            final Position arrivalPosition = new Position(6, 1);
            final Movement movement = new Movement(Direction.UP, Direction.UP);
            final Path path = movement.makePath(currentPosition, arrivalPosition);

            // When & Then
            assertThatThrownBy(
                    () -> {
                        jumpingStrategy.checkObstacle(path,
                                new Board(Map.of(currentPosition, Piece.CANNON, cannonPosition, Piece.CANNON)));
                    })
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("[ERROR] 같은 종류의 기물을 뛰어넘거나 잡을 수 없습니다.");
        }

        @Test
        void 같은_전략의_기물을_잡을_수_없다() {
            // Given
            final Position currentPosition = new Position(8, 1);
            final Position cannonPosition = new Position(7, 1);
            final Position arrivalPosition = new Position(6, 1);
            final Movement movement = new Movement(Direction.UP, Direction.UP);
            final Path path = movement.makePath(currentPosition, arrivalPosition);

            // When & Then
            assertThatThrownBy(
                    () -> {
                        jumpingStrategy.checkObstacle(path,
                                new Board(Map.of(currentPosition, Piece.CANNON, cannonPosition, Piece.CANNON)));
                    })
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("[ERROR] 같은 종류의 기물을 뛰어넘거나 잡을 수 없습니다.");
        }
    }

    @Nested
    class ObstacleBlockStrategyTest {

        private final ObstacleStrategy blockStrategy = ObstacleStrategy.BLOCK;

        @Test
        void 정해진_거리만큼_이동한다() {
            // Given
            final Position currentPosition = new Position(3, 3);
            final Position arrivalPosition = new Position(6, 5);
            final Movement movement = new Movement(Direction.DOWN, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT);
            final Path path = movement.makePath(currentPosition, arrivalPosition);

            // When & Then
            Assertions.assertThatCode(() ->
                    blockStrategy.checkObstacle(path, new Board(Map.of(currentPosition, Piece.ELEPHANT)))
            ).doesNotThrowAnyException();
        }
    }
}
