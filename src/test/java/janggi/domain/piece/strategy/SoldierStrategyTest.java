package janggi.domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.exception.ExceptionMessage;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SoldierStrategyTest {

    private final MoveStrategy strategy = new SoldierStrategy();

    @DisplayName("병 행마법 테스트")
    @Nested
    class Byung {
        private static Stream<Arguments> successMovePositions() {
            return Stream.of(
                    Arguments.of(new Position(6, 0), new Position(5, 0)),
                    Arguments.of(new Position(5, 1), new Position(4, 1)),
                    Arguments.of(new Position(6, 0), new Position(6, 1)),
                    Arguments.of(new Position(6, 1), new Position(6, 0))
            );
        }

        private static Stream<Arguments> exceptionMovePositions() {
            return Stream.of(
                    Arguments.of(new Position(6, 0), new Position(5, 1)),
                    Arguments.of(new Position(6, 0), new Position(6, 2)),
                    Arguments.of(new Position(3, 2), new Position(2, 3))
            );
        }

        @ParameterizedTest
        @MethodSource("successMovePositions")
        void 병의_1칸_이동_여부를_확인한다(Position source, Position destination) {
            List<Position> path = strategy.findPath(source, destination, Camp.HAN);

            SoftAssertions.assertSoftly(assertSoftly -> {
                assertSoftly.assertThat(path).hasSize(1);
                assertSoftly.assertThat(path).containsExactly(destination);
            });
        }

        @ParameterizedTest
        @MethodSource("exceptionMovePositions")
        void 병은_1칸_이동이_아니면_예외가_발생한다(Position source, Position destination) {
            assertThatThrownBy(() -> strategy.findPath(source, destination, Camp.HAN))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_SOLDIER_MOVE.getMessage());
        }

        @Test
        void 병은_후진_시_예외가_발생한다() {
            assertThatThrownBy(() -> strategy.findPath(new Position(6, 0), new Position(7, 0), Camp.HAN))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_BACKWARD_MOVEMENT.getMessage());
        }
    }

    @DisplayName("졸 행마법 테스트")
    @Nested
    class Zol {
        @Test
        void 졸의_1칸_이동_여부를_확인한다() {
            Position source = new Position(3, 0);
            Position destination = new Position(4, 0);

            List<Position> path = strategy.findPath(source, destination, Camp.CHO);

            SoftAssertions.assertSoftly(assertSoftly -> {
                assertSoftly.assertThat(path).hasSize(1);
                assertSoftly.assertThat(path).containsExactly(destination);
            });
        }

        @Test
        void 졸은_1칸_이동이_아니면_예외가_발생한다() {
            assertThatThrownBy(() -> strategy.findPath(new Position(3, 0), new Position(5, 0), Camp.CHO))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_SOLDIER_MOVE.getMessage());
        }

        @Test
        void 졸은_후진_시_예외가_발생한다() {
            assertThatThrownBy(() -> strategy.findPath(new Position(3, 0), new Position(2, 0), Camp.CHO))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_BACKWARD_MOVEMENT.getMessage());
    }
}
}
