package janggi.piece;

import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
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
                .map(movement -> movement.move(new Position(new Row(1), new Column(1))))
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
                                new Position(new Row(2), new Column(1)),
                                new Position(new Row(0), new Column(1)),
                                new Position(new Row(1), new Column(2)),
                                new Position(new Row(1), new Column(0))
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
                                new Position(new Row(2), new Column(2)),
                                new Position(new Row(2), new Column(0)),
                                new Position(new Row(0), new Column(2)),
                                new Position(new Row(0), new Column(0))
                        )
                ),
                Arguments.arguments(
                        List.of(
                                Movement.UP_UP_RIGHT,
                                Movement.UP_UP_LEFT,
                                Movement.DOWN_DOWN_RIGHT,
                                Movement.DOWN_DOWN_LEFT,
                                Movement.RIGHT_RIGHT_UP,
                                Movement.RIGHT_RIGHT_DOWN,
                                Movement.LEFT_LEFT_UP,
                                Movement.LEFT_LEFT_DOWN
                        ),
                        List.of(
                                new Position(new Row(2), new Column(3)),
                                new Position(new Row(0), new Column(3)),
                                new Position(new Row(2), new Column(-1)),
                                new Position(new Row(0), new Column(-1)),
                                new Position(new Row(3), new Column(2)),
                                new Position(new Row(3), new Column(0)),
                                new Position(new Row(-1), new Column(2)),
                                new Position(new Row(-1), new Column(0))
                        )
                )
        );
    }
}
