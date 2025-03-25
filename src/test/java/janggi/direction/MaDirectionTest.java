package janggi.direction;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.value.Position;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MaDirectionTest {

    static final Position START_POSITION = new Position(4, 4);

    @ParameterizedTest
    @DisplayName("시작점과 목적지를 통해 방향을 구할 수 있다.")
    @MethodSource
    void canParse(MaDirection expectedDirection, Position destination) {
        MaDirection actualPosition = MaDirection.parse(START_POSITION, destination);
        assertThat(expectedDirection).isEqualTo(actualPosition);
    }

    static Stream<Arguments> canParse() {
        return Stream.of(
                Arguments.of(MaDirection.RIGHT, new Position(START_POSITION.x() + 2, START_POSITION.y() + 1)),
                Arguments.of(MaDirection.RIGHT, new Position(START_POSITION.x() + 2, START_POSITION.y() - 1)),
                Arguments.of(MaDirection.LEFT, new Position(START_POSITION.x() - 2, START_POSITION.y() - 1)),
                Arguments.of(MaDirection.LEFT, new Position(START_POSITION.x() - 2, START_POSITION.y() + 1)),
                Arguments.of(MaDirection.UP, new Position(START_POSITION.x() + 1, START_POSITION.y() - 2)),
                Arguments.of(MaDirection.UP, new Position(START_POSITION.x() - 1, START_POSITION.y() - 2)),
                Arguments.of(MaDirection.DOWN, new Position(START_POSITION.x() + 1, START_POSITION.y() + 2)),
                Arguments.of(MaDirection.DOWN, new Position(START_POSITION.x() - 1, START_POSITION.y() + 2)),
                Arguments.of(MaDirection.NONE, new Position(START_POSITION.x() + 3, START_POSITION.y() + 3)),
                Arguments.of(MaDirection.NONE, new Position(START_POSITION.x() - 3, START_POSITION.y() + 3))
        );
    }

    @ParameterizedTest
    @DisplayName("입력된 위치값이 경로상의 위치한 것인지 체크할 수 있다.")
    @MethodSource
    void canCheckPositionInPath(MaDirection direction, Position checkTarget, boolean expectedIsInPath) {
        boolean actualIsInPath = direction.checkPositionInPath(START_POSITION, checkTarget);
        assertThat(actualIsInPath).isEqualTo(expectedIsInPath);
    }

    static Stream<Arguments> canCheckPositionInPath() {
        return Stream.of(
                Arguments.of(MaDirection.RIGHT, new Position(START_POSITION.x() + 1, START_POSITION.y()), true),
                Arguments.of(MaDirection.RIGHT, new Position(START_POSITION.x() + 2, START_POSITION.y()), false),
                Arguments.of(MaDirection.LEFT, new Position(START_POSITION.x() - 1, START_POSITION.y()), true),
                Arguments.of(MaDirection.LEFT, new Position(START_POSITION.x() - 2, START_POSITION.y()), false),
                Arguments.of(MaDirection.DOWN, new Position(START_POSITION.x(), START_POSITION.y() + 1), true),
                Arguments.of(MaDirection.DOWN, new Position(START_POSITION.x(), START_POSITION.y() - 2), false),
                Arguments.of(MaDirection.UP, new Position(START_POSITION.x(), START_POSITION.y() - 1), true),
                Arguments.of(MaDirection.UP, new Position(START_POSITION.x(), START_POSITION.y() + 2), false),
                Arguments.of(MaDirection.NONE, new Position(START_POSITION.x() + 2, START_POSITION.y() + 2), false)
        );
    }
}