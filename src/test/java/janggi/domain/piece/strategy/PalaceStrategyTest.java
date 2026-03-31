package janggi.domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PalaceStrategyTest {

    private final MoveStrategy strategy = new PalaceStrategy();

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
        List<Position> path = strategy.findPath(source, destination, camp);
        //then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(path).hasSize(1);
            assertSoftly.assertThat(path).containsExactly(destination);
        });
    }

    @Test
    void 궁과_사는_1칸_이동이_아니면_예외가_발생한다() {
        assertThatThrownBy(() -> strategy.findPath(new Position(0, 4), new Position(2, 4), Camp.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 직선으로 1칸 이동해야 합니다.");
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
        assertThatThrownBy(() -> strategy.findPath(source, destination, camp))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 아군 궁성 영역 밖으로 이동할 수 없습니다.");
    }
}
