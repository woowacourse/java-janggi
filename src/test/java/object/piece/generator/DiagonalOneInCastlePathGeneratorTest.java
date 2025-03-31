package object.piece.generator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import object.coordinate.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiagonalOneInCastlePathGeneratorTest {

    @Test
    @DisplayName("현재 좌표 기준으로 대각선으로 한 칸씩 이동한 궁성의 좌표들을 구할 수 있다.")
    void test1() {
        // given
        DiagonalOneInCastlePathGenerator generator = new DiagonalOneInCastlePathGenerator();
        Coordinate departure = new Coordinate(4, 1);

        // when
        Set<Coordinate> coordinates = generator.generate(departure);

        // then
        assertThat(coordinates).containsOnly(
                new Coordinate(5, 2)
        );
    }

    @Test
    @DisplayName("현재 좌표가 궁성이 아니라면 빈 Set을 반환한다.")
    void test2() {
        // given
        DiagonalOneInCastlePathGenerator generator = new DiagonalOneInCastlePathGenerator();
        Coordinate departure = new Coordinate(5, 5);

        // when
        Set<Coordinate> coordinates = generator.generate(departure);

        // then
        assertThat(coordinates).isEmpty();
    }
}
