package janggi.domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.board.Position;
import janggi.domain.piece.Camp;
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

    private MoveStrategy strategy(Camp camp) {
        return new SoldierStrategy(camp);
    }

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

        @ParameterizedTest
        @MethodSource("successMovePositions")
        void 병의_1칸_이동_여부를_확인한다(Position source, Position destination) {
            List<Position> path = strategy(Camp.HAN).findPath(source, destination);

            SoftAssertions.assertSoftly(assertSoftly -> {
                assertSoftly.assertThat(path).hasSize(1);
                assertSoftly.assertThat(path).containsExactly(destination);
            });
        }

        private static Stream<Arguments> successDiagonalMovePositions() {
            return Stream.of(
                    Arguments.of(new Position(2, 3), new Position(1, 4), Camp.HAN),
                    Arguments.of(new Position(2, 5), new Position(1, 4), Camp.HAN),
                    Arguments.of(new Position(1, 4), new Position(0, 3), Camp.HAN),
                    Arguments.of(new Position(1, 4), new Position(0, 5), Camp.HAN)
            );
        }

        @ParameterizedTest
        @MethodSource("successDiagonalMovePositions")
        void 병은_상대_궁성_내부에서_대각선_이동한다(Position source, Position destination, Camp camp) {
            List<Position> path = strategy(camp).findPath(source, destination);

            SoftAssertions.assertSoftly(assertSoftly -> {
                assertSoftly.assertThat(path).hasSize(1);
                assertSoftly.assertThat(path).containsExactly(destination);
            });
        }

        private static Stream<Arguments> exceptionMovePositions() {
            return Stream.of(
                    Arguments.of(new Position(6, 0), new Position(5, 1)),
                    Arguments.of(new Position(6, 0), new Position(6, 2)),
                    Arguments.of(new Position(3, 2), new Position(2, 3))
            );
        }

        @ParameterizedTest
        @MethodSource("exceptionMovePositions")
        void 병은_1칸_이동이_아니면_예외가_발생한다(Position source, Position destination) {
            assertThatThrownBy(() -> strategy(Camp.HAN).findPath(source, destination))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물은 직선으로 1칸 이동해야 합니다.");
        }

        @Test
        void 병은_후진_시_예외가_발생한다() {
            assertThatThrownBy(() -> strategy(Camp.HAN).findPath(new Position(6, 0), new Position(7, 0)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물은 후진할 수 없습니다.");
        }

        @Test
        void 병은_궁성에서_대각선이_없을때_대각_이동_시_예외가_발생한다() {
            assertThatThrownBy(() -> strategy(Camp.HAN).findPath(new Position(1, 3), new Position(0, 4)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 궁성 내에 대각선이 존재하지 않는 경로 입니다.");
        }
    }

    @DisplayName("졸 행마법 테스트")
    @Nested
    class Zol {
        @Test
        void 졸의_1칸_이동_여부를_확인한다() {
            Position source = new Position(3, 0);
            Position destination = new Position(4, 0);

            List<Position> path = strategy(Camp.CHO).findPath(source, destination);

            SoftAssertions.assertSoftly(assertSoftly -> {
                assertSoftly.assertThat(path).hasSize(1);
                assertSoftly.assertThat(path).containsExactly(destination);
            });
        }

        private static Stream<Arguments> successDiagonalMovePositions() {
            return Stream.of(
                    Arguments.of(new Position(7, 3), new Position(8, 4), Camp.CHO),
                    Arguments.of(new Position(7, 5), new Position(8, 4), Camp.CHO),
                    Arguments.of(new Position(8, 4), new Position(9, 3), Camp.CHO),
                    Arguments.of(new Position(8, 4), new Position(9, 5), Camp.CHO)
            );
        }

        @ParameterizedTest
        @MethodSource("successDiagonalMovePositions")
        void 졸은_상대_궁성_내부에서_대각선_이동한다(Position source, Position destination, Camp camp) {
            List<Position> path = strategy(camp).findPath(source, destination);

            SoftAssertions.assertSoftly(assertSoftly -> {
                assertSoftly.assertThat(path).hasSize(1);
                assertSoftly.assertThat(path).containsExactly(destination);
            });
        }

        @Test
        void 졸은_1칸_이동이_아니면_예외가_발생한다() {
            assertThatThrownBy(() -> strategy(Camp.CHO).findPath(new Position(3, 0), new Position(5, 0)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물은 직선으로 1칸 이동해야 합니다.");
        }

        @Test
        void 졸은_후진_시_예외가_발생한다() {
            assertThatThrownBy(() -> strategy(Camp.CHO).findPath(new Position(3, 0), new Position(2, 0)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물은 후진할 수 없습니다.");
        }

        @Test
        void 졸은_궁성에서_대각선이_없을때_대각_이동_시_예외가_발생한다() {
            assertThatThrownBy(() -> strategy(Camp.CHO).findPath(new Position(8, 3), new Position(9, 4)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 궁성 내에 대각선이 존재하지 않는 경로 입니다.");
        }
    }
}
