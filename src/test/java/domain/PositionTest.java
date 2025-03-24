package domain;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.position.Direction;
import domain.position.Position;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.ArgumentUtils;

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
                Arguments.of(Direction.LEFT, Position.of(2, 1)),
                Arguments.of(Direction.RIGHT_UP, Position.of(3, 3)),
                Arguments.of(Direction.RIGHT_DOWN, Position.of(1, 3)),
                Arguments.of(Direction.LEFT_UP, Position.of(3, 1)),
                Arguments.of(Direction.LEFT_DOWN, Position.of(1, 1))
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
                Arguments.of(Direction.RIGHT_UP, Position.of(9, 8), false),
                Arguments.of(Direction.RIGHT_DOWN, Position.of(0, 8), false),
                Arguments.of(Direction.LEFT_UP, Position.of(9, 0), false),
                Arguments.of(Direction.LEFT_DOWN, Position.of(0, 0), false),
                Arguments.of(Direction.UP, Position.of(8, 0), true),
                Arguments.of(Direction.RIGHT, Position.of(0, 7), true),
                Arguments.of(Direction.DOWN, Position.of(1, 0), true),
                Arguments.of(Direction.LEFT, Position.of(0, 1), true),
                Arguments.of(Direction.RIGHT_UP, Position.of(8, 7), true),
                Arguments.of(Direction.RIGHT_DOWN, Position.of(1, 7), true),
                Arguments.of(Direction.LEFT_UP, Position.of(8, 1), true),
                Arguments.of(Direction.LEFT_DOWN, Position.of(1, 1), true)
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
}
