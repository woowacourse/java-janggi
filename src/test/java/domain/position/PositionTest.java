package domain.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class PositionTest {

    @ParameterizedTest
    @CsvSource({
            "3, 10",
            "2, -1"
    })
    @DisplayName("열의 범위를 넘어가면 예외가 발생한다")
    void createColumnPositionException(int row, int col) {
        // given

        // when & then
        assertThatThrownBy(() -> Position.of(row, col))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("좌표가 장기판의 범위를 벗어났습니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "11, 3",
            "-10, 2"
    })
    @DisplayName("행의 범위를 넘어가면 예외가 발생한다")
    void createRowPositionException(int row, int col) {
        // given

        // when & then
        assertThatThrownBy(() -> Position.of(row, col))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("좌표가 장기판의 범위를 벗어났습니다.");
    }

    static Stream<Arguments> moveDirectionTest() {
        return Stream.of(
                Arguments.of(Direction.UP, Position.of(3, 2)),
                Arguments.of(Direction.RIGHT, Position.of(2, 3)),
                Arguments.of(Direction.DOWN, Position.of(1, 2)),
                Arguments.of(Direction.LEFT, Position.of(2, 1))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("특정 방향으로 이동한 위치를 반환한다")
    void moveDirectionTest(Direction direction, Position expectedPosition) {
        // given
        Position startPosition = Position.of(2, 2);

        // when
        Position endPosition = startPosition.moveDirection(direction);

        // then
        assertThat(endPosition).isEqualTo(expectedPosition);
    }

    static Stream<Arguments> canMoveDirectionTest() {
        return Stream.of(
                Arguments.of(Direction.UP, Position.of(9, 0), false),
                Arguments.of(Direction.RIGHT, Position.of(0, 8), false),
                Arguments.of(Direction.DOWN, Position.of(0, 8), false),
                Arguments.of(Direction.LEFT, Position.of(9, 0), false),
                Arguments.of(Direction.UP, Position.of(8, 0), true),
                Arguments.of(Direction.RIGHT, Position.of(0, 7), true),
                Arguments.of(Direction.DOWN, Position.of(1, 0), true),
                Arguments.of(Direction.LEFT, Position.of(0, 1), true)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("특정 방향 이동 가능 여부를 반환한다")
    void canMoveDirectionTest(Direction direction, Position startPosition, boolean expected) {
        // when
        boolean actual = startPosition.canMoveDirection(direction);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> canMoveDirectionInPalaceTrue() {
        return Stream.of(
                Arguments.of(
                        Position.of(0, 3),
                        Direction.RIGHT_UP
                ),
                Arguments.of(
                        Position.of(0, 5),
                        Direction.LEFT_UP
                ),
                Arguments.of(
                        Position.of(2, 3),
                        Direction.RIGHT_DOWN
                ),
                Arguments.of(
                        Position.of(2, 5),
                        Direction.LEFT_DOWN
                ),
                Arguments.of(
                        Position.of(9, 3),
                        Direction.RIGHT_DOWN
                ),
                Arguments.of(
                        Position.of(9, 5),
                        Direction.LEFT_DOWN
                ),
                Arguments.of(
                        Position.of(7, 3),
                        Direction.RIGHT_UP
                ),
                Arguments.of(
                        Position.of(7, 5),
                        Direction.LEFT_UP
                ),
                Arguments.of(
                        Position.of(1, 4),
                        Direction.LEFT_UP
                ),
                Arguments.of(
                        Position.of(1, 4),
                        Direction.RIGHT_UP
                ),
                Arguments.of(
                        Position.of(1, 4),
                        Direction.LEFT_DOWN
                ),
                Arguments.of(
                        Position.of(1, 4),
                        Direction.RIGHT_DOWN
                )
        );
    }

    static Stream<Arguments> canMoveDirectionInPalaceFalse() {
        return Stream.of(
                Arguments.of(
                        Position.of(0, 4),
                        Direction.LEFT_UP
                ),
                Arguments.of(
                        Position.of(0, 4),
                        Direction.RIGHT_UP
                ),
                Arguments.of(
                        Position.of(2, 4),
                        Direction.LEFT_UP
                ),
                Arguments.of(
                        Position.of(2, 4),
                        Direction.LEFT_UP
                ),
                Arguments.of(
                        Position.of(1, 3),
                        Direction.RIGHT_UP
                ),
                Arguments.of(
                        Position.of(1, 3),
                        Direction.RIGHT_DOWN
                ),
                Arguments.of(
                        Position.of(1, 5),
                        Direction.LEFT_UP
                ),
                Arguments.of(
                        Position.of(1, 5),
                        Direction.LEFT_DOWN
                )
        );
    }

    static Stream<Arguments> canMoveDirectionInPalaceTest() {
        return Stream.of(
                Arguments.of(
                        Position.of(1, 4),
                        Direction.UP
                ),
                Arguments.of(
                        Position.of(1, 4),
                        Direction.DOWN
                ),
                Arguments.of(
                        Position.of(1, 4),
                        Direction.RIGHT
                ),
                Arguments.of(
                        Position.of(1, 4),
                        Direction.LEFT
                ),
                Arguments.of(
                        Position.of(0, 5),
                        Direction.RIGHT
                ),
                Arguments.of(
                        Position.of(0, 3),
                        Direction.LEFT
                ),
                Arguments.of(
                        Position.of(2, 5),
                        Direction.UP
                )
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("궁에서 대각선 이동이 가능하면 true를 반환한다")
    void canMoveDirectionInPalaceTrue(Position position, Direction direction) {
        // given

        // when
        boolean actual = position.canMoveDirection(direction);

        // then
        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("궁에서 대각선 이동이 불가능하면 false를 반환한다")
    void canMoveDirectionInPalaceFalse(Position position, Direction direction) {
        // given

        // when
        boolean actual = position.canMoveDirection(direction);

        // then
        assertThat(actual).isFalse();
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("궁에서는 상하좌우 이동이 가능하다")
    void canMoveDirectionInPalaceTest(Position position, Direction direction) {
        // given

        // when
        boolean actual = position.canMoveDirection(direction);

        // then
        assertThat(actual).isTrue();
    }
}
