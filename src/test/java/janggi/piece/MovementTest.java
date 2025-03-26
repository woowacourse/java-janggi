package janggi.piece;

import janggi.position.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class MovementTest {
    @ParameterizedTest
    @MethodSource("makeMovementTestData")
    @DisplayName("이동 방향에 따른 Position 변화 확인")
    void movementTest(List<Movement> movements,List<Position> expected) {
        //given

        //when
        List<Position> arrivedPositions = movements.stream()
                .map(movement -> movement.move(new Position(1, 1)))
                .toList();
        //then
        Assertions.assertThat(arrivedPositions).containsAll(expected);
    }

    static Stream<Arguments> makeMovementTestData() {
        return Stream.of(
                Arguments.arguments(
                        List.of(
                                Movement.UP,
                                Movement.DOWN,
                                Movement.RIGHT,
                                Movement.LEFT
                        ),
                        List.of(
                                new Position(2, 1),
                                new Position(0, 1),
                                new Position(1, 2),
                                new Position(1, 0)
                        )
                ),
                Arguments.arguments(
                        List.of(
                                Movement.RIGHT_UP,
                                Movement.RIGHT_DOWN,
                                Movement.LEFT_UP,
                                Movement.LEFT_DOWN
                        ),
                        List.of(
                                new Position(2, 2),
                                new Position(2, 0),
                                new Position(0, 2),
                                new Position(0, 0)
                        )
                )
        );
    }
}
