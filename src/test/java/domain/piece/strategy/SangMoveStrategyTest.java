package domain.piece.strategy;

import domain.position.Position;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SangMoveStrategyTest {

    @ParameterizedTest
    @MethodSource("moveWays")
    void 움직일_수_있다면_경로를_반환한다(Position destination, int expectSize, List<Position> expectPath) {
        // given
        Position start = Position.of(3, 3);
        MoveStrategy strategy = new SangMoveStrategy();

        // when
        List<Position> movablePath = strategy.findMovablePath(start, destination);

        // then
        Assertions.assertThat(movablePath.size()).isEqualTo(expectSize);
        Assertions.assertThat(movablePath).containsAll(expectPath);
    }

    private static Stream<Arguments> moveWays() {
        return Stream.of(
                Arguments.of(Position.of(1, 6), 3,
                        List.of(Position.of(3, 4), Position.of(2, 5), Position.of(1, 6))),
                Arguments.of(Position.of(5, 6), 3,
                        List.of(Position.of(3, 4), Position.of(4, 5), Position.of(5, 6))),
                Arguments.of(Position.of(6, 5), 3,
                        List.of(Position.of(4, 3), Position.of(5, 4), Position.of(6, 5))),
                Arguments.of(Position.of(6, 1), 3,
                        List.of(Position.of(4, 3), Position.of(5, 2), Position.of(6, 1)))
        );
    }

    @Test
    void 잘못된_위치가_제공되면_예외가_발생해야_한다() {
        // given
        Position start = Position.of(3, 3);
        Position destination = Position.of(4, 4);
        MoveStrategy strategy = new SangMoveStrategy();

        // when & then
        Assertions.assertThatThrownBy(() -> strategy.findMovablePath(start, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }
}