package janggi.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DirectionTest {

    @ParameterizedTest
    @DisplayName("시작점과 도착점이 주어질 때 올바른 방향을 계산해 반환한다")
    @MethodSource("calculateDirectionArguments")
    void should_return_direction_by_start_end_position(Position start, Position end, Direction expected) {
        // when
        Direction direction = Direction.calculateDirection(start, end);

        // then
        assertThat(direction).isEqualTo(expected);
    }

    private static Stream<Arguments> calculateDirectionArguments() {
        return Stream.of(
                Arguments.of(
                        new Position(5, 5),
                        new Position(5, 4),
                        Direction.UP
                ),
                Arguments.of(
                        new Position(5, 5),
                        new Position(5, 6),
                        Direction.DOWN
                ),
                Arguments.of(
                        new Position(5, 5),
                        new Position(4, 5),
                        Direction.LEFT
                ),
                Arguments.of(
                        new Position(5, 5),
                        new Position(6, 5),
                        Direction.RIGHT
                ),
                Arguments.of(
                        new Position(5, 5),
                        new Position(4, 4),
                        Direction.LEFT_UP
                ),
                Arguments.of(
                        new Position(5, 5),
                        new Position(4, 6),
                        Direction.LEFT_DOWN
                ),
                Arguments.of(
                        new Position(5, 5),
                        new Position(6, 4),
                        Direction.RIGHT_UP
                ),
                Arguments.of(
                        new Position(5, 5),
                        new Position(6, 6),
                        Direction.RIGHT_DOWN
                )
        );
    }

    @ParameterizedTest
    @DisplayName("상하좌우,대각 방향이 아닌 잘못된 방향을 계산하려 하면 예외가 발생한다")
    @MethodSource("calculateDirectionExceptionArguments")
    void should_throw_exception_when_invalid_start_and_end_position(Position start, Position end) {
        // when & then
        assertThatThrownBy(() -> Direction.calculateDirection(start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> calculateDirectionExceptionArguments() {
        return Stream.of(
                Arguments.of(
                        // 상 좌상
                        new Position(5, 5),
                        new Position(4, 3)
                ),
                Arguments.of(
                        // 상 우상
                        new Position(5, 5),
                        new Position(6, 3)
                ),
                Arguments.of(
                        // 하 좌하
                        new Position(5, 5),
                        new Position(4, 7)
                ),
                Arguments.of(
                        // 하 우하
                        new Position(5, 5),
                        new Position(6, 7)
                ),
                Arguments.of(
                        // 좌 좌상
                        new Position(5, 5),
                        new Position(3, 4)
                ),
                Arguments.of(
                        // 좌 좌하
                        new Position(5, 5),
                        new Position(3, 6)
                ),
                Arguments.of(
                        // 우 우상
                        new Position(5, 5),
                        new Position(7, 4)
                ),
                Arguments.of(
                        // 우 우하
                        new Position(5, 5),
                        new Position(7, 6)
                )
        );
    }
}
