package janggiGame.position;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PalaceTest {

    @DisplayName("위치가 궁성에 속하는지 판별 가능하다")
    @ParameterizedTest
    @MethodSource("providePositionsAndExpected")
    void isInPalace(Position position, boolean expected) {
        // when
        boolean actual = Palace.isInPalace(position);
        // then
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> providePositionsAndExpected() {
        return Stream.of(
                Arguments.of(Position.getInstanceBy(3, 7), true),
                Arguments.of(Position.getInstanceBy(5, 2), true),
                Arguments.of(Position.getInstanceBy(2, 7), false),
                Arguments.of(Position.getInstanceBy(6, 7), false)
        );
    }


    @DisplayName("궁성에서는 중심을 지나는 대각선은 이동 가능하다")
    @ParameterizedTest
    @MethodSource("provideOriginAndDestinationAndExpected")
    void isDiagonalThroughCenter(Position origin, Position destination, boolean expected) {
        // when
        boolean actual = Palace.isPalaceDiagonalMove(origin, destination);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> provideOriginAndDestinationAndExpected() {
        return Stream.of(
                Arguments.of(Position.getInstanceBy(3, 9), Position.getInstanceBy(4, 8), true),
                Arguments.of(Position.getInstanceBy(3, 9), Position.getInstanceBy(5, 7), true),
                Arguments.of(Position.getInstanceBy(4, 9), Position.getInstanceBy(5, 8), false),
                Arguments.of(Position.getInstanceBy(4, 8), Position.getInstanceBy(5, 9), true),
                Arguments.of(Position.getInstanceBy(3, 7), Position.getInstanceBy(5, 7), false)
        );
    }
}
