package domain.board;

import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DirectionTest {

    @ParameterizedTest
    @MethodSource("directionCases")
    @DisplayName("단위 방향 좌표에 맞는 방향을 반환한다.")
    void 단위_방향_테스트(int drow, int dcolumn, Direction expected) {
        Direction direction = Direction.from(drow, dcolumn);
        assertThat(direction).isEqualTo(expected);
    }

    // 단위 방향 MethodSource
    static Stream<Arguments> directionCases() {
        return Stream.of(
                Arguments.of(1, 0, Direction.UP),
                Arguments.of(1, 1, Direction.UP_RIGHT),
                Arguments.of(0, 1, Direction.RIGHT),
                Arguments.of(-1, 1, Direction.DOWN_RIGHT),
                Arguments.of(-1, 0, Direction.DOWN),
                Arguments.of(-1, -1, Direction.DOWN_LEFT),
                Arguments.of(0, -1, Direction.LEFT),
                Arguments.of(1, -1, Direction.UP_LEFT)
        );
    }

    @Test
    @DisplayName("(2, 2)를 넣으면 예외가 발생한다.")
    void 단위방향에_맞지_않는_값을_넣으면_예외가_발생한다() {
        int row = 2;
        int column = 2;

        assertThatThrownBy(() -> Direction.from(row, column))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("pathCases")
    @DisplayName("시작-도착-좌표에 따른 방향 경로를 반환한다.")
    void 시작_도착_좌표의_방향_경로_테스트(Position startPosition, Position endPosition, List<Direction> expected) {
        Queue<Direction> directions = Direction.of(startPosition, endPosition);
        assertThat(directions).containsExactlyElementsOf(expected);
    }

    static Stream<Arguments> pathCases() {
        return Stream.of(
                // 직선 2개
                Arguments.of(Position.of(5, 5), Position.of(7, 5),
                        List.of(Direction.UP, Direction.UP)),

                Arguments.of(Position.of(5, 5), Position.of(3, 5),
                        List.of(Direction.DOWN, Direction.DOWN)),

                Arguments.of(Position.of(5, 5), Position.of(5, 3),
                        List.of(Direction.LEFT, Direction.LEFT)),

                Arguments.of(Position.of(5, 5), Position.of(5, 7),
                        List.of(Direction.RIGHT, Direction.RIGHT)),

                // 직선 1개, 대각선 1개
                Arguments.of(Position.of(5, 5), Position.of(7, 6),
                        List.of(Direction.UP, Direction.UP_RIGHT)),

                Arguments.of(Position.of(5, 5), Position.of(7, 4),
                        List.of(Direction.UP, Direction.UP_LEFT)),

                Arguments.of(Position.of(5, 5), Position.of(6, 7),
                        List.of(Direction.RIGHT, Direction.UP_RIGHT)),

                Arguments.of(Position.of(5, 5), Position.of(4, 7),
                        List.of(Direction.RIGHT, Direction.DOWN_RIGHT)),

                Arguments.of(Position.of(5, 5), Position.of(3, 6),
                        List.of(Direction.DOWN, Direction.DOWN_RIGHT)),

                Arguments.of(Position.of(5, 5), Position.of(3, 4),
                        List.of(Direction.DOWN, Direction.DOWN_LEFT)),

                Arguments.of(Position.of(5, 5), Position.of(6, 3),
                        List.of(Direction.LEFT, Direction.UP_LEFT)),

                Arguments.of(Position.of(5, 5), Position.of(4, 3),
                        List.of(Direction.LEFT, Direction.DOWN_LEFT)),

                // 직선 1개, 대각선 2개
                Arguments.of(Position.of(5, 5), Position.of(8, 7),
                        List.of(Direction.UP, Direction.UP_RIGHT, Direction.UP_RIGHT)),

                Arguments.of(Position.of(5, 5), Position.of(8, 3),
                        List.of(Direction.UP, Direction.UP_LEFT, Direction.UP_LEFT)),

                Arguments.of(Position.of(5, 5), Position.of(7, 8),
                        List.of(Direction.RIGHT, Direction.UP_RIGHT, Direction.UP_RIGHT)),

                Arguments.of(Position.of(5, 5), Position.of(3, 8),
                        List.of(Direction.RIGHT, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT)),

                Arguments.of(Position.of(5, 5), Position.of(2, 7),
                        List.of(Direction.DOWN, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT)),

                Arguments.of(Position.of(5, 5), Position.of(2, 3),
                        List.of(Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_LEFT)),

                Arguments.of(Position.of(5, 5), Position.of(7, 2),
                        List.of(Direction.LEFT, Direction.UP_LEFT, Direction.UP_LEFT)),

                Arguments.of(Position.of(5, 5), Position.of(3, 2),
                        List.of(Direction.LEFT, Direction.DOWN_LEFT, Direction.DOWN_LEFT))
        );
    }
}
