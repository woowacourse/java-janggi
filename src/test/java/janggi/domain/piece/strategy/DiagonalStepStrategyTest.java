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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DiagonalStepStrategyTest {

    @DisplayName("직선 1칸 후 대각선 1칸 이동(마) 테스트")
    @Nested
    class OneStepDiagonal {

        private static final int STRAIGHT_DISTANCE = 1;
        private static final int DIAGONAL_DISTANCE = 1;
        private final MoveStrategy strategy = new DiagonalStepStrategy(DIAGONAL_DISTANCE);

        private static Stream<Arguments> successPaths() {
            Position source = new Position(6, 4);
            return Stream.of(
                    Arguments.of(source, List.of(new Position(7, 4), new Position(8, 5))),
                    Arguments.of(source, List.of(new Position(7, 4), new Position(8, 3))),
                    Arguments.of(source, List.of(new Position(5, 4), new Position(4, 3))),
                    Arguments.of(source, List.of(new Position(5, 4), new Position(4, 5))),
                    Arguments.of(source, List.of(new Position(6, 3), new Position(5, 2))),
                    Arguments.of(source, List.of(new Position(6, 3), new Position(7, 2))),
                    Arguments.of(source, List.of(new Position(6, 5), new Position(5, 6))),
                    Arguments.of(source, List.of(new Position(6, 5), new Position(7, 6)))
            );
        }

        @ParameterizedTest
        @MethodSource("successPaths")
        void 직선_1칸_이동_후_대각선_1칸_이동한다(Position source, List<Position> expectedPath) {
            List<Position> path = strategy.findPath(source, expectedPath.getLast(), Camp.HAN);
            SoftAssertions.assertSoftly(assertSoftly -> {
                assertSoftly.assertThat(path).hasSize(expectedPath.size());
                assertSoftly.assertThat(path).containsExactlyElementsOf(expectedPath);
            });
        }

        private static Stream<Arguments> invalidDistancePositions() {
            return Stream.of(
                    Arguments.of(new Position(6, 4), new Position(5, 1)),
                    Arguments.of(new Position(6, 4), new Position(4, 2)),
                    Arguments.of(new Position(6, 4), new Position(6, 4))
            );
        }

        @ParameterizedTest
        @MethodSource("invalidDistancePositions")
        void 행마법_대로_움직이지_않으면_예외가_발생한다(Position source, Position destination) {
            assertThatThrownBy(() -> strategy.findPath(source, destination, Camp.CHO))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_DIAGONAL_STEP_MOVE.getMessage(STRAIGHT_DISTANCE, DIAGONAL_DISTANCE));
        }
    }

    @DisplayName("직선 1칸 후 대각선 2칸 이동 테스트")
    @Nested
    class TwoStepDiagonal {

        private static final int STRAIGHT_DISTANCE = 1;
        private static final int DIAGONAL_DISTANCE = 2;
        private final MoveStrategy strategy = new DiagonalStepStrategy(DIAGONAL_DISTANCE);

        private static Stream<Arguments> createPaths() {
            Position source = new Position(6, 4);
            return Stream.of(
                    Arguments.of(source,
                            List.of(new Position(5, 4), new Position(4, 3), new Position(3, 2))),
                    Arguments.of(source,
                            List.of(new Position(6, 3), new Position(5, 2), new Position(4, 1))),
                    Arguments.of(source,
                            List.of(new Position(6, 3), new Position(7, 2), new Position(8, 1))),
                    Arguments.of(source,
                            List.of(new Position(7, 4), new Position(8, 3), new Position(9, 2))),
                    Arguments.of(source,
                            List.of(new Position(7, 4), new Position(8, 5), new Position(9, 6))),
                    Arguments.of(source,
                            List.of(new Position(6, 5), new Position(7, 6), new Position(8, 7))),
                    Arguments.of(source,
                            List.of(new Position(6, 5), new Position(5, 6), new Position(4, 7))),
                    Arguments.of(source,
                            List.of(new Position(5, 4), new Position(4, 5), new Position(3, 6)))
            );
        }

        @ParameterizedTest
        @MethodSource("createPaths")
        void 직선_1칸_이동_후_대각선_2칸_이동한다(Position source, List<Position> expectedPath) {
            List<Position> path = strategy.findPath(source, expectedPath.getLast(), Camp.HAN);
            SoftAssertions.assertSoftly(assertSoftly -> {
                assertSoftly.assertThat(path).hasSize(expectedPath.size());
                assertSoftly.assertThat(path).containsExactlyElementsOf(expectedPath);
            });
        }

        private static Stream<Arguments> invalidDistancePositions() {
            return Stream.of(
                    Arguments.of(new Position(6, 4), new Position(3, 4)),
                    Arguments.of(new Position(6, 4), new Position(6, 4))
            );
        }

        @ParameterizedTest
        @MethodSource("invalidDistancePositions")
        void 행마법_대로_움직이지_않으면_예외가_발생한다(Position source, Position destination) {
            assertThatThrownBy(() -> strategy.findPath(source, destination, Camp.CHO))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_DIAGONAL_STEP_MOVE.getMessage(STRAIGHT_DISTANCE, DIAGONAL_DISTANCE));
        }
    }
}
