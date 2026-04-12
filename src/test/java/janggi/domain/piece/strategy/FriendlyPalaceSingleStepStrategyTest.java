package janggi.domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.board.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.movement.strategy.FriendlyPalaceSingleStepStrategy;
import janggi.domain.piece.movement.strategy.MoveStrategy;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FriendlyPalaceSingleStepStrategyTest {

    private MoveStrategy strategy(Camp camp) {
        return new FriendlyPalaceSingleStepStrategy(camp);
    }

    @DisplayName("정상 경우")
    @Nested
    class success {
        private static Stream<Arguments> successMovePositions() {
            return Stream.of(
                    Arguments.of(new Position(1, 4), new Position(1, 5), Camp.CHO),
                    Arguments.of(new Position(1, 4), new Position(1, 3), Camp.CHO),
                    Arguments.of(new Position(1, 4), new Position(0, 4), Camp.CHO),
                    Arguments.of(new Position(1, 4), new Position(2, 4), Camp.CHO)
            );
        }

        @ParameterizedTest
        @MethodSource("successMovePositions")
        void 궁과_사는_상하좌우_1칸_이동한다(Position source, Position destination, Camp camp) {
            //when
            List<Position> path = strategy(camp).findPath(source, destination);
            //then
            SoftAssertions.assertSoftly(assertSoftly -> {
                assertSoftly.assertThat(path).hasSize(1);
                assertSoftly.assertThat(path).containsExactly(destination);
            });
        }

        private static Stream<Arguments> successDiagonalMovePositions() {
            return Stream.of(
                    Arguments.of(new Position(1, 4), new Position(0, 3), Camp.CHO),
                    Arguments.of(new Position(1, 4), new Position(0, 5), Camp.CHO),
                    Arguments.of(new Position(1, 4), new Position(2, 3), Camp.CHO),
                    Arguments.of(new Position(1, 4), new Position(2, 5), Camp.CHO),

                    Arguments.of(new Position(8, 4), new Position(7, 3), Camp.HAN),
                    Arguments.of(new Position(8, 4), new Position(7, 5), Camp.HAN),
                    Arguments.of(new Position(8, 4), new Position(9, 3), Camp.HAN),
                    Arguments.of(new Position(8, 4), new Position(9, 5), Camp.HAN)
            );
        }

        @ParameterizedTest
        @MethodSource("successDiagonalMovePositions")
        void 궁과_사는_궁성_내부에서_대각선으로_1칸_이동한다(Position source, Position destination, Camp camp) {
            //when
            List<Position> path = strategy(camp).findPath(source, destination);
            //then
            SoftAssertions.assertSoftly(assertSoftly -> {
                assertSoftly.assertThat(path).hasSize(1);
                assertSoftly.assertThat(path).containsExactly(destination);
            });
        }
    }

    @DisplayName("예외 경우")
    @Nested
    class exception {
        @Test
        void 궁과_사는_1칸_이동이_아니면_예외가_발생한다() {
            assertThatThrownBy(() -> strategy(Camp.CHO).findPath(new Position(0, 4), new Position(2, 4)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물은 궁성 내에서 연결된 1칸만 이동할 수 있습니다.");
        }

        private static Stream<Arguments> exceptionPalaceMovePositions() {
            return Stream.of(
                    Arguments.of(new Position(2, 3), new Position(2, 2), Camp.CHO),
                    Arguments.of(new Position(2, 3), new Position(3, 3), Camp.CHO),
                    Arguments.of(new Position(2, 5), new Position(3, 5), Camp.CHO),
                    Arguments.of(new Position(2, 5), new Position(2, 6), Camp.CHO),

                    Arguments.of(new Position(7, 3), new Position(7, 2), Camp.HAN),
                    Arguments.of(new Position(7, 3), new Position(6, 3), Camp.HAN),
                    Arguments.of(new Position(7, 5), new Position(6, 5), Camp.HAN),
                    Arguments.of(new Position(7, 5), new Position(7, 6), Camp.HAN)
            );
        }

        @ParameterizedTest
        @MethodSource("exceptionPalaceMovePositions")
        void 궁과_사는_아군_궁성_밖으로_벗어나면_예외가_발생한다(Position source, Position destination, Camp camp) {
            assertThatThrownBy(() -> strategy(camp).findPath(source, destination))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물은 아군 궁성 영역 밖으로 이동할 수 없습니다.");
        }

        private static Stream<Arguments> exceptionPalaceCampPositions() {
            return Stream.of(
                    Arguments.of(new Position(1, 4), new Position(0, 3), Camp.HAN),
                    Arguments.of(new Position(1, 4), new Position(0, 5), Camp.HAN),
                    Arguments.of(new Position(1, 4), new Position(2, 3), Camp.HAN),
                    Arguments.of(new Position(1, 4), new Position(2, 5), Camp.HAN),

                    Arguments.of(new Position(8, 4), new Position(7, 3), Camp.CHO),
                    Arguments.of(new Position(8, 4), new Position(7, 5), Camp.CHO),
                    Arguments.of(new Position(8, 4), new Position(9, 3), Camp.CHO),
                    Arguments.of(new Position(8, 4), new Position(9, 5), Camp.CHO)
            );
        }

        @ParameterizedTest
        @MethodSource("exceptionPalaceCampPositions")
        void 궁과_사는_상대_궁성_영역에서_이동하면_예외가_발생한다(Position source, Position destination, Camp camp) {
            assertThatThrownBy(() -> strategy(camp).findPath(source, destination))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물은 아군 궁성 영역 밖으로 이동할 수 없습니다.");
        }

        private static Stream<Arguments> exceptionInvalidDiagonalMovePositions() {
            return Stream.of(
                    Arguments.of(new Position(0, 4), new Position(1, 3), Camp.CHO),
                    Arguments.of(new Position(0, 4), new Position(1, 5), Camp.CHO),
                    Arguments.of(new Position(1, 3), new Position(2, 4), Camp.CHO),
                    Arguments.of(new Position(1, 5), new Position(2, 4), Camp.CHO),

                    Arguments.of(new Position(7, 4), new Position(8, 3), Camp.HAN),
                    Arguments.of(new Position(7, 4), new Position(8, 5), Camp.HAN),
                    Arguments.of(new Position(8, 3), new Position(9, 4), Camp.HAN),
                    Arguments.of(new Position(8, 5), new Position(9, 4), Camp.HAN)
            );
        }

        @ParameterizedTest
        @MethodSource("exceptionInvalidDiagonalMovePositions")
        void 궁과_사는_궁성_대각선이_연결되지_않은_칸으로는_대각선_이동할_수_없다(Position source, Position destination, Camp camp) {
            assertThatThrownBy(() -> strategy(camp).findPath(source, destination))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 궁성 내에 대각선이 존재하지 않는 경로 입니다.");
        }
    }
}
