package janggi.direction;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.value.Position;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class OneStepDirectionTest {

    static final Position START_POSITION = new Position(4, 4);

    @ParameterizedTest
    @DisplayName("시작점과 목적지를 통해 방향을 구할 수 있다.")
    @MethodSource
    void canParse(OneStepDirection expectedDirection, Position destination) {
        OneStepDirection actualDirection = OneStepDirection.parse(START_POSITION, destination);
        assertThat(actualDirection).isEqualTo(expectedDirection);
    }

    static Stream<Arguments> canParse() {
        return Stream.of(
                Arguments.of(OneStepDirection.RIGHT, new Position(START_POSITION.x() + 1, START_POSITION.y())),
                Arguments.of(OneStepDirection.LEFT, new Position(START_POSITION.x() - 1, START_POSITION.y())),
                Arguments.of(OneStepDirection.UP, new Position(START_POSITION.x(), START_POSITION.y() - 1)),
                Arguments.of(OneStepDirection.DOWN, new Position(START_POSITION.x(), START_POSITION.y() + 1)),
                Arguments.of(OneStepDirection.NONE, new Position(START_POSITION.x() + 1, START_POSITION.y() + 1))
        );
    }
}