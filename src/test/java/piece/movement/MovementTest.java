package piece.movement;

import static org.assertj.core.api.Assertions.assertThat;
import static piece.movement.Movement.BOTTOM_LEFT_BOTTOM;
import static piece.movement.Movement.BOTTOM_RIGHT_BOTTOM;
import static piece.movement.Movement.LEFT_LEFT_BOTTOM;
import static piece.movement.Movement.LEFT_LEFT_TOP;
import static piece.movement.Movement.RIGHT_RIGHT_BOTTOM;
import static piece.movement.Movement.RIGHT_RIGHT_TOP;
import static piece.movement.Movement.TOP_LEFT_TOP;
import static piece.movement.Movement.TOP_RIGHT_TOP;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import board.Position;
import piece.Direction;

class MovementTest {


    @MethodSource
    @ParameterizedTest
    void 움직임이_적용된_경로를_계산한다(Movement movement, List<Direction> expected) {
        Position position = new Position(4, 4);

        assertThat(movement.applyMovement(position)).isEqualTo(expected);
    }

    private static Stream<Arguments> 움직임이_적용된_경로를_계산한다() {
        return Stream.of(
                Arguments.of(TOP_LEFT_TOP, List.of(position(3, 4), position(2, 3))),
                Arguments.of(TOP_RIGHT_TOP, List.of(position(3, 4), position(2, 5))),
                Arguments.of(BOTTOM_LEFT_BOTTOM, List.of(position(5, 4), position(6, 3))),
                Arguments.of(BOTTOM_RIGHT_BOTTOM, List.of(position(5, 4), position(6, 5))),
                Arguments.of(LEFT_LEFT_TOP, List.of(position(4, 3), position(3, 2))),
                Arguments.of(LEFT_LEFT_BOTTOM, List.of(position(4, 3), position(5, 2))),
                Arguments.of(RIGHT_RIGHT_TOP, List.of(position(4, 5), position(3, 6))),
                Arguments.of(RIGHT_RIGHT_BOTTOM, List.of(position(4, 5), position(5, 6)))
        );
    }

    private static Position position(int row, int column) {
        return new Position(row, column);
    }

}
