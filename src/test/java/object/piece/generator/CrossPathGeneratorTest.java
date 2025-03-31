package object.piece.generator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import object.coordinate.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrossPathGeneratorTest {

    @Test
    @DisplayName("현재 좌표 기준으로 자신을 제외한 같은 x축과 같은 y축의 좌표들을 구할 수 있다.")
    void test1() {
        // given
        CrossPathGenerator generator = new CrossPathGenerator();
        Coordinate departure = new Coordinate(5, 5);

        // when
        Set<Coordinate> coordinates = generator.generate(departure);

        // then
        assertThat(coordinates).containsOnly(
                new Coordinate(1, 5),
                new Coordinate(2, 5),
                new Coordinate(3, 5),
                new Coordinate(4, 5),
                new Coordinate(6, 5),
                new Coordinate(7, 5),
                new Coordinate(8, 5),
                new Coordinate(9, 5),

                new Coordinate(5, 1),
                new Coordinate(5, 2),
                new Coordinate(5, 3),
                new Coordinate(5, 4),
                new Coordinate(5, 6),
                new Coordinate(5, 7),
                new Coordinate(5, 8),
                new Coordinate(5, 9),
                new Coordinate(5, 10)
        );
    }
}
