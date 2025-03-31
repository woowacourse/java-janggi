package object.piece.generator;

import static object.coordinate.CrossMoveVector.DOWN;
import static object.coordinate.CrossMoveVector.LEFT;
import static object.coordinate.CrossMoveVector.RIGHT;
import static object.coordinate.CrossMoveVector.UP;
import static object.coordinate.DiagonalMoveVector.LEFT_DOWN;
import static object.coordinate.DiagonalMoveVector.LEFT_UP;
import static object.coordinate.DiagonalMoveVector.RIGHT_DOWN;
import static object.coordinate.DiagonalMoveVector.RIGHT_UP;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;
import object.coordinate.Coordinate;
import object.coordinate.MoveVector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SpecificPathGeneratorTest {

    @Test
    @DisplayName("현재 좌표 기준으로 특정 움직임 이후 좌표들을 구할 수 있다.")
    void test1() {
        // given
        List<List<MoveVector>> moveVectors = List.of(
                List.of(UP, RIGHT_UP, RIGHT_UP),
                List.of(UP, LEFT_UP, LEFT_UP),
                List.of(DOWN, RIGHT_DOWN, RIGHT_DOWN),
                List.of(DOWN, LEFT_DOWN, LEFT_DOWN),
                List.of(RIGHT, RIGHT_UP, RIGHT_UP),
                List.of(RIGHT, RIGHT_DOWN, RIGHT_DOWN),
                List.of(LEFT, LEFT_UP, LEFT_UP),
                List.of(LEFT, LEFT_DOWN, LEFT_DOWN)
        );
        SpecificPathGenerator generator = new SpecificPathGenerator(moveVectors);
        Coordinate departure = new Coordinate(5, 5);

        // when
        Set<Coordinate> coordinates = generator.generate(departure);

        // then
        assertThat(coordinates).containsOnly(
                new Coordinate(2, 3),
                new Coordinate(3, 2),
                new Coordinate(7, 2),
                new Coordinate(8, 3),
                new Coordinate(2, 7),
                new Coordinate(3, 8),
                new Coordinate(7, 8),
                new Coordinate(8, 7)
        );
    }
}
