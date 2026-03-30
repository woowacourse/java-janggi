package domain.piece.strategy;

import domain.position.Position;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SangMoveStrategyTest {

    @ParameterizedTest
    @MethodSource("moveWays")
    @DisplayName("움직일 수 있다면 경로를 반환한다")
    void findMovablePath_success(Position destination, int expectSize, List<Position> expectPath) {
        // given
        Position start = Position.of(3, 3);

        MoveStrategy strategy = new SangMoveStrategy();
        List<Position> movablePath = strategy.findMovablePath(start, destination);

        // then
        Assertions.assertThat(movablePath.size()).isEqualTo(expectSize);
        Assertions.assertThat(movablePath).containsAll(expectPath);
    }

    private static Stream<Arguments> moveWays() {
        return Stream.of(
                Arguments.of(Position.of(0, 5), 2,
                        List.of(Position.of(2, 3), Position.of(1, 4))),
                Arguments.of(Position.of(1, 6), 2,
                        List.of(Position.of(3, 4), Position.of(2, 5))),
                Arguments.of(Position.of(5, 6), 2,
                        List.of(Position.of(3, 4), Position.of(4, 5))),
                Arguments.of(Position.of(6, 5), 2,
                        List.of(Position.of(4, 3), Position.of(5, 4))),
                Arguments.of(Position.of(6, 1), 2,
                        List.of(Position.of(4, 3), Position.of(5, 2))),
                Arguments.of(Position.of(5, 0), 2,
                        List.of(Position.of(3, 2), Position.of(4, 1))),
                Arguments.of(Position.of(1, 0), 2,
                        List.of(Position.of(3, 2), Position.of(2, 1))),
                Arguments.of(Position.of(0, 1), 2,
                        List.of(Position.of(2, 3), Position.of(1, 2)))
        );
    }

    @Test
    @DisplayName("목적지까지 이동한 가능한 경로가 없는 경우 예외가 발상해야 한다")
    void findMovablePath_fail_cause_of_incorrect_position() {
        // given
        Position start = Position.of(3, 3);
        Position destination = Position.of(4, 4);

        MoveStrategy strategy = new SangMoveStrategy();

        Assertions.assertThatThrownBy(() -> strategy.findMovablePath(start, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(MoveStrategyErrorMessage.NOT_EXIST_MOVABLE_PATH.getMessage());
    }
}
