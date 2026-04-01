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

class SingleStepStrategyTest {

    private static final int SINGLE_STEP_DISTANCE = 1;

    @DisplayName("전방향 1칸 이동(궁, 사) 테스트")
    @Nested
    class AllDirectionStep {

        private final MoveStrategy strategy = new SingleStepStrategy(false);

        private static Stream<Arguments> successMovePositions() {
            return Stream.of(
                    Arguments.of(new Position(1, 4), new Position(1, 5)),
                    Arguments.of(new Position(1, 4), new Position(1, 3)),
                    Arguments.of(new Position(1, 4), new Position(0, 4)),
                    Arguments.of(new Position(1, 4), new Position(2, 4))
            );
        }

        @ParameterizedTest
        @MethodSource("successMovePositions")
        void 직선_방향으로_1칸만_이동한다(Position source, Position destination) {
            List<Position> path = strategy.findPath(source, destination, Camp.HAN);
            SoftAssertions.assertSoftly(assertSoftly -> {
                assertSoftly.assertThat(path).hasSize(1);
                assertSoftly.assertThat(path).containsExactly(destination);
            });
        }

        @Test
        void 직선_방향으로_1칸만_이동하지_않으면_예외가_발생한다() {
            assertThatThrownBy(() -> strategy.findPath(new Position(3, 0), new Position(5, 0), Camp.HAN))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_SINGLE_STEP_MOVE.getMessage(SINGLE_STEP_DISTANCE));
        }
    }

    @DisplayName("전진 및 좌우 1칸 이동(졸, 병) 테스트")
    @Nested
    class ForwardSideStep {

        private final MoveStrategy strategy = new SingleStepStrategy(true);

        private static Stream<Arguments> successMovePositions() {
            return Stream.of(
                    Arguments.of(Camp.HAN, new Position(6, 0), new Position(5, 0)),
                    Arguments.of(Camp.HAN, new Position(6, 0), new Position(6, 1)),
                    Arguments.of(Camp.HAN, new Position(6, 1), new Position(6, 0)),
                    Arguments.of(Camp.CHO, new Position(3, 0), new Position(4, 0)),
                    Arguments.of(Camp.CHO, new Position(3, 0), new Position(3, 1)),
                    Arguments.of(Camp.CHO, new Position(3, 1), new Position(3, 0))
            );
        }

        @ParameterizedTest
        @MethodSource("successMovePositions")
        void 전진_또는_좌우_방향으로_1칸만_이동한다(Camp camp, Position source, Position destination) {
            List<Position> path = strategy.findPath(source, destination, camp);
            SoftAssertions.assertSoftly(assertSoftly -> {
                assertSoftly.assertThat(path).hasSize(1);
                assertSoftly.assertThat(path).containsExactly(destination);
            });
        }

        private static Stream<Arguments> invalidDistancePositions() {
            return Stream.of(
                    Arguments.of(Camp.HAN, new Position(6, 0), new Position(4, 0)),
                    Arguments.of(Camp.CHO, new Position(3, 0), new Position(3, 6))
            );
        }

        @ParameterizedTest
        @MethodSource("invalidDistancePositions")
        void 직선_방향으로_1칸만_이동하지_않으면_예외가_발생한다(Camp camp, Position source, Position destination) {
            assertThatThrownBy(() -> strategy.findPath(source, destination, camp))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_SINGLE_STEP_MOVE.getMessage(SINGLE_STEP_DISTANCE));
        }

        private static Stream<Arguments> backwardMovePositions() {
            return Stream.of(
                    Arguments.of(Camp.HAN, new Position(6, 0), new Position(7, 0)),
                    Arguments.of(Camp.CHO, new Position(3, 0), new Position(2, 0))
            );
        }

        @ParameterizedTest
        @MethodSource("backwardMovePositions")
        void 후진하는_경우_예외가_발생한다(Camp camp, Position source, Position destination) {
            assertThatThrownBy(() -> strategy.findPath(source, destination, camp))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_BACKWARD_MOVEMENT.getMessage());
        }
    }
}
