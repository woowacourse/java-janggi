package object.piece.generator;

import static object.coordinate.CrossMoveVector.LEFT;
import static object.coordinate.CrossMoveVector.RIGHT;
import static object.coordinate.CrossMoveVector.UP;
import static org.assertj.core.api.Assertions.assertThat;

import object.coordinate.Coordinate;
import object.coordinate.MoveVector;
import java.util.List;
import java.util.Set;
import object.piece.generator.SpecificOnePathGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SpecificOnePathGeneratorTest {

    @Test
    @DisplayName("현재 좌표 기준으로 특정 움직임 이후 좌표들을 구할 수 있다.")
    void test1() {
        // given
        List<MoveVector> moveVectors = List.of(UP, RIGHT, LEFT);
        SpecificOnePathGenerator generator = new SpecificOnePathGenerator(moveVectors);
        Coordinate departure = new Coordinate(5, 5);

        // when
        Set<Coordinate> coordinates = generator.generate(departure);

        // then
        assertThat(coordinates).containsOnly(
                new Coordinate(5, 4),
                new Coordinate(6, 5),
                new Coordinate(4, 5)
        );
    }
}
