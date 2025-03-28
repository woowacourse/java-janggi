package domain.position;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PalaceTest {

    static Stream<Arguments> isInPalaceTest() {
        return Stream.of(
                Arguments.of(
                        Position.of(0, 3),
                        true
                ),
                Arguments.of(
                        Position.of(0, 4),
                        true
                ),
                Arguments.of(
                        Position.of(0, 5),
                        true
                ),
                Arguments.of(
                        Position.of(9, 3),
                        true
                ),
                Arguments.of(
                        Position.of(8, 3),
                        true
                ),
                Arguments.of(
                        Position.of(6, 3),
                        false
                ),
                Arguments.of(
                        Position.of(4, 6),
                        false
                ),
                Arguments.of(
                        Position.of(0, 7),
                        false
                )
        );
    }

    static Stream<Arguments> isValidDirectionTest() {
        return Stream.of(
                Arguments.of(
                        Position.of(0, 3),
                        Direction.RIGHT_UP,
                        true
                ),
                Arguments.of(
                        Position.of(0, 5),
                        Direction.LEFT_UP,
                        true
                ),
                Arguments.of(
                        Position.of(2, 3),
                        Direction.RIGHT_DOWN,
                        true
                ),
                Arguments.of(
                        Position.of(2, 5),
                        Direction.LEFT_DOWN,
                        true
                ),
                Arguments.of(
                        Position.of(2, 5),
                        Direction.RIGHT,
                        true
                ),
                Arguments.of(
                        Position.of(4, 5),
                        Direction.LEFT_DOWN,
                        false
                ),
                Arguments.of(
                        Position.of(6, 7),
                        Direction.LEFT_DOWN,
                        false
                )
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("궁성 내에 존재하는 지 판별한다")
    void isInPalaceTest(Position position, boolean expected) {
        // when
        boolean actual = Palace.isInPalace(position);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("이동 가능한 방향인지 판별한다")
    void isValidDirectionTest(Position from, Direction direction, boolean expected) {
        // when
        boolean actual = Palace.isValidDirection(from, direction);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
