package domain.piece.strategy;

import domain.position.Position;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MaMoveStrategyTest {
    @ParameterizedTest
    @MethodSource("moveWays")
    void 움직일_수_있다면_경로를_반환한다(Position destination, int expectSize, List<Position> expectPath) {
        // given
        Position start = Position.of(3, 3);

        MoveStrategy strategy = new MaMoveStrategy();
        List<Position> movablePath = strategy.findMovablePath(start, destination);
        Assertions.assertThat(movablePath.size()).isEqualTo(expectSize);
        Assertions.assertThat(movablePath).containsAll(expectPath);
    }

    private static Stream<Arguments> moveWays() {
        return Stream.of(
                Arguments.of(Position.of(1, 2), 1,
                        List.of(Position.of(2, 3))),
                Arguments.of(Position.of(1, 4), 1,
                        List.of(Position.of(2, 3))),
                Arguments.of(Position.of(2, 1), 1,
                        List.of(Position.of(3, 2))),
                Arguments.of(Position.of(2, 5), 1,
                        List.of(Position.of(3, 4))),
                Arguments.of(Position.of(4, 1), 1,
                        List.of(Position.of(3, 2))),
                Arguments.of(Position.of(4, 5), 1,
                        List.of(Position.of(3, 4))),
                Arguments.of(Position.of(5, 2), 1,
                        List.of(Position.of(4, 3))),
                Arguments.of(Position.of(5, 4), 1,
                        List.of(Position.of(4, 3)))
        );
    }

    @Test
    void 잘못된_위치가_제공되면_예외가_발상해야_한다() {
        // given
        Position start = Position.of(2, 9);
        Position destination = Position.of(4, 9);

        MoveStrategy strategy = new JolMoveStrategy();

        Assertions.assertThatThrownBy(() -> strategy.findMovablePath(start, destination)).isInstanceOf(
                IllegalArgumentException.class);
    }

}