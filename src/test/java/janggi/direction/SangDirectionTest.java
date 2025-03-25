package janggi.direction;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.value.Position;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SangDirectionTest {

    static final Position START_POSITION = new Position(4, 4);

    @ParameterizedTest
    @DisplayName("시작점과 목적지을 통해 방향을 구할 수 있다.")
    @MethodSource
    void canParse(SangDirection expectedDirection, Position destination) {
        SangDirection actualDirection = SangDirection.parse(START_POSITION, destination);
        assertThat(expectedDirection).isEqualTo(actualDirection);
    }

    static Stream<Arguments> canParse() {
        return Stream.of(
                Arguments.of(SangDirection.RIGHT, new Position(START_POSITION.x() + 3, START_POSITION.y() + 2)),
                Arguments.of(SangDirection.RIGHT, new Position(START_POSITION.x() + 3, START_POSITION.y() - 2)),
                Arguments.of(SangDirection.LEFT, new Position(START_POSITION.x() - 3, START_POSITION.y() - 2)),
                Arguments.of(SangDirection.LEFT, new Position(START_POSITION.x() - 3, START_POSITION.y() + 2)),
                Arguments.of(SangDirection.UP, new Position(START_POSITION.x() + 2, START_POSITION.y() - 3)),
                Arguments.of(SangDirection.UP, new Position(START_POSITION.x() - 2, START_POSITION.y() - 3)),
                Arguments.of(SangDirection.DOWN, new Position(START_POSITION.x() + 2, START_POSITION.y() + 3)),
                Arguments.of(SangDirection.DOWN, new Position(START_POSITION.x() - 2, START_POSITION.y() + 3)),
                Arguments.of(SangDirection.NONE, new Position(START_POSITION.x() + 3, START_POSITION.y() + 3)),
                Arguments.of(SangDirection.NONE, new Position(START_POSITION.x() - 3, START_POSITION.y() + 3))
        );
    }

    @ParameterizedTest
    @DisplayName("입력된 위치값이 경로상의 위치한 것인지 체크할 수 있다.")
    @MethodSource
    void canCheckPositionInPath(SangDirection direction, Position checkTarget) {
        boolean actualIsInPath = direction.checkPositionInPath(START_POSITION, checkTarget);
        assertThat(actualIsInPath).isTrue();
    }

    static Stream<Arguments> canCheckPositionInPath() {
        return Stream.of(
                Arguments.of(SangDirection.RIGHT, new Position(START_POSITION.x() + 1, START_POSITION.y()),
                        Arguments.of(SangDirection.RIGHT, new Position(START_POSITION.x() + 2, START_POSITION.y() - 1)),
                        Arguments.of(SangDirection.RIGHT, new Position(START_POSITION.x() + 2, START_POSITION.y() + 1)),
                        Arguments.of(SangDirection.LEFT, new Position(START_POSITION.x() - 1, START_POSITION.y())),
                        Arguments.of(SangDirection.LEFT, new Position(START_POSITION.x() - 2, START_POSITION.y() - 1)),
                        Arguments.of(SangDirection.LEFT, new Position(START_POSITION.x() - 2, START_POSITION.y() + 1)),
                        Arguments.of(SangDirection.UP, new Position(START_POSITION.x(), START_POSITION.y() - 1)),
                        Arguments.of(SangDirection.UP, new Position(START_POSITION.x() - 1, START_POSITION.y() - 2)),
                        Arguments.of(SangDirection.UP, new Position(START_POSITION.x() + 1, START_POSITION.y() - 2)),
                        Arguments.of(SangDirection.DOWN, new Position(START_POSITION.x(), START_POSITION.y() + 1)),
                        Arguments.of(SangDirection.DOWN, new Position(START_POSITION.x() - 1, START_POSITION.y() + 2)),
                        Arguments.of(SangDirection.DOWN, new Position(START_POSITION.x() + 1, START_POSITION.y() + 2)))
        );
    }

    @ParameterizedTest
    @DisplayName("입력된 위치값이 경로상의 위치한 것인지 체크할 수 있다.")
    @MethodSource
    void canCheckPositionOutPath(SangDirection direction, Position checkTarget) {
        boolean actualIsInPath = direction.checkPositionInPath(START_POSITION, checkTarget);
        assertThat(actualIsInPath).isFalse();
    }

    static Stream<Arguments> canCheckPositionOutPath() {
        return Stream.of(
                Arguments.of(SangDirection.RIGHT, new Position(START_POSITION.x() + 3, START_POSITION.y() + 3)),
                Arguments.of(SangDirection.LEFT, new Position(START_POSITION.x() + 3, START_POSITION.y() + 3)),
                Arguments.of(SangDirection.UP, new Position(START_POSITION.x() + 3, START_POSITION.y() + 3)),
                Arguments.of(SangDirection.DOWN, new Position(START_POSITION.x() + 3, START_POSITION.y() + 3)),
                Arguments.of(SangDirection.NONE, new Position(START_POSITION.x() + 3, START_POSITION.y() + 3))
        );
    }
}