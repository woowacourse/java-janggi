package janggi.domain.position;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static janggi.test_util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class MovementTest {

    @ParameterizedTest
    @MethodSource("provideMovementAndExpectedPositions")
    void 시작_위치로부터_움직인_결과를_반환한다(Movement movement, List<Position> expected) {
        // given
        final Position position = POSITION_5_5;

        // when
        final List<Position> result = movement.getPositionsWith(position);

        // then
        assertThat(result).containsSequence(expected);
    }

    public static Stream<Arguments> provideMovementAndExpectedPositions() {
        return Stream.of(
                Arguments.of(Movement.UP, List.of(POSITION_5_6)),
                Arguments.of(Movement.DOWN, List.of(POSITION_5_4)),
                Arguments.of(Movement.LEFT, List.of(POSITION_4_5)),
                Arguments.of(Movement.RIGHT, List.of(POSITION_6_5)),

                Arguments.of(Movement.UP_LEFT, List.of(POSITION_4_6)),
                Arguments.of(Movement.UP_RIGHT, List.of(POSITION_6_6)),
                Arguments.of(Movement.DOWN_LEFT, List.of(POSITION_4_4)),
                Arguments.of(Movement.DOWN_RIGHT, List.of(POSITION_6_4)),

                Arguments.of(Movement.UP_UPLEFT, List.of(POSITION_5_6, POSITION_4_7)),
                Arguments.of(Movement.UP_UPRIGHT, List.of(POSITION_5_6, POSITION_6_7)),
                Arguments.of(Movement.RIGHT_UPRIGHT, List.of(POSITION_6_5, POSITION_7_6)),
                Arguments.of(Movement.RIGHT_DOWNRIGHT, List.of(POSITION_6_5, POSITION_7_4)),
                Arguments.of(Movement.DOWN_DOWNLEFT, List.of(POSITION_5_4, POSITION_4_3)),
                Arguments.of(Movement.DOWN_DOWNRIGHT, List.of(POSITION_5_4, POSITION_6_3)),
                Arguments.of(Movement.LEFT_DOWNLEFT, List.of(POSITION_4_5, POSITION_3_4)),
                Arguments.of(Movement.LEFT_UPLEFT, List.of(POSITION_4_5, POSITION_3_6)),

                Arguments.of(Movement.UP_UPLEFT_UPLEFT, List.of(POSITION_5_6, POSITION_4_7, POSITION_3_8)),
                Arguments.of(Movement.UP_UPRIGHT_UPRIGHT, List.of(POSITION_5_6, POSITION_6_7, POSITION_7_8)),
                Arguments.of(Movement.RIGHT_UPRIGHT_UPRIGHT, List.of(POSITION_6_5, POSITION_7_6, POSITION_8_7)),
                Arguments.of(Movement.RIGHT_DOWNRIGHT_DOWNRIGHT, List.of(POSITION_6_5, POSITION_7_4, POSITION_8_3)),
                Arguments.of(Movement.DOWN_DOWNLEFT_DOWNLEFT, List.of(POSITION_5_4, POSITION_4_3, POSITION_3_2)),
                Arguments.of(Movement.DOWN_DOWNRIGHT_DOWNRIGHT, List.of(POSITION_5_4, POSITION_6_3, POSITION_7_2)),
                Arguments.of(Movement.LEFT_DOWNLEFT_DOWNLEFT, List.of(POSITION_4_5, POSITION_3_4, POSITION_2_3)),
                Arguments.of(Movement.LEFT_UPLEFT_UPLEFT, List.of(POSITION_4_5, POSITION_3_6, POSITION_2_7))
        );
    }

}