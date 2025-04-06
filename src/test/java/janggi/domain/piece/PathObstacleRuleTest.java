package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.piece.direction.Direction;
import janggi.domain.piece.direction.Movement;
import janggi.domain.board.Board;
import janggi.domain.piece.position.Path;
import janggi.domain.piece.position.Position;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PathObstacleRuleTest {

    @Nested
    class ObstacleJumpingStrategyTest {

        private final PathObstacleRule jumpingStrategy = PathObstacleRule.MUST_JUMP_EXACTLY_ONE_OBSTACLE;

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
                jumpingStrategy.validatePathObstacles(path,
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
                        jumpingStrategy.validatePathObstacles(path,
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
                        jumpingStrategy.validatePathObstacles(path,
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
                        jumpingStrategy.validatePathObstacles(path,
                                new Board(Map.of(currentPosition, Piece.CANNON, cannonPosition, Piece.CANNON)));
                    })
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("[ERROR] 같은 종류의 기물을 뛰어넘거나 잡을 수 없습니다.");
        }
    }

    @Nested
    class ObstacleBlockStrategyTest {

        private final PathObstacleRule blockStrategy = PathObstacleRule.CANNOT_PASS_OBSTACLES;

        @Test
        void 정해진_거리만큼_이동한다() {
            // Given
            final Position currentPosition = new Position(3, 3);
            final Position arrivalPosition = new Position(6, 5);
            final Movement movement = new Movement(Direction.DOWN, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT);
            final Path path = movement.makePath(currentPosition, arrivalPosition);

            // When & Then
            Assertions.assertThatCode(() ->
                    blockStrategy.validatePathObstacles(path, new Board(Map.of(currentPosition, Piece.ELEPHANT)))
            ).doesNotThrowAnyException();
        }
    }
}
