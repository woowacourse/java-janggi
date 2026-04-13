package model.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import model.coordinate.Direction;
import model.coordinate.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DirectionTest {

    private static Stream<Arguments> 좌표_차이에_대한_방향_도출_정보() {
        return Stream.of(
                // 사방위 (Cardinal) - 거리가 1보다 커도 부호만 맞으면 성공해야 함
                Arguments.of(-3, 0, Direction.NORTH),
                Arguments.of(5, 0, Direction.SOUTH),
                Arguments.of(0, -1, Direction.WEST),
                Arguments.of(0, 2, Direction.EAST),

                // 사간방 (Diagonal) - 마/상/궁성 이동 관련
                Arguments.of(-1, 1, Direction.NORTH_EAST),
                Arguments.of(2, 2, Direction.SOUTH_EAST),
                Arguments.of(-10, -10, Direction.NORTH_WEST),
                Arguments.of(3, -2, Direction.SOUTH_WEST)
        );
    }

    private static Stream<Arguments> 방향에_따라_5comma5에서_다음_위치로_이동하는_정보() {
        return Stream.of(
                // 사방위 (Cardinal)
                Arguments.of(Direction.NORTH, 4, 5),
                Arguments.of(Direction.SOUTH, 6, 5),
                Arguments.of(Direction.WEST, 5, 4),
                Arguments.of(Direction.EAST, 5, 6),

                // 사간방 (Diagonal)
                Arguments.of(Direction.NORTH_WEST, 4, 4),
                Arguments.of(Direction.NORTH_EAST, 4, 6),
                Arguments.of(Direction.SOUTH_WEST, 6, 4),
                Arguments.of(Direction.SOUTH_EAST, 6, 6)
        );
    }

    @ParameterizedTest(name = "rowDiff: {0}, colDiff: {1} => {2}")
    @MethodSource("좌표_차이에_대한_방향_도출_정보")
    void 좌표_차이를_기반으로_올바른_방향을_구할_수_있다(int rowDiff, int colDiff, Direction expected) {
        // when
        Direction actual = Direction.of(rowDiff, colDiff);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 제자리_이동의_경우_방향이_아니다() {
        assertThatThrownBy(() -> Direction.of(0, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest(name = "{0} 방향으로 이동: (5,5) -> ({1}, {2})")
    @MethodSource("방향에_따라_5comma5에서_다음_위치로_이동하는_정보")
    void 위치_정보에서_방향을_통해_이동한_위치를_구할_수_있다(Direction direction, int expectedRow, int expectedCol) {
        // given
        Position start = new Position(5, 5);

        // when
        Position next = direction.move(start);

        // then
        assertThat(next).isEqualTo(new Position(expectedRow, expectedCol));
    }
}