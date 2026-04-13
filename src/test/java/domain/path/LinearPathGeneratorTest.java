package domain.path;

import domain.board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LinearPathGeneratorTest {
    LinearPathGenerator linearPathGenerator;

    @BeforeEach
    void setUp() {
        linearPathGenerator = new LinearPathGenerator();
    }

    @Test
    void 직선_이동_경로를_생성한다() {
        Position departure = new Position(0, 0);
        Position destination = new Position(0, 3);

        List<Position> path = linearPathGenerator.getPath(departure, destination);

        assertThat(path).containsExactly(
                new Position(0, 1),
                new Position(0, 2),
                new Position(0, 3)
        );
    }

    @Test
    void 궁성_내_대각선_이동_경로를_생성한다() {
        Position departure = new Position(3, 0);
        Position destination = new Position(5, 2);

        List<Position> path = linearPathGenerator.getPath(departure, destination);

        assertThat(path).containsExactly(
                new Position(4, 1),
                new Position(5, 2)
        );
    }

    @Test
    void 궁성_밖에서_직선이_아닌_이동은_예외가_발생한다() {
        Position departure = new Position(0, 0);
        Position destination = new Position(2, 1);

        assertThatThrownBy(() -> linearPathGenerator.getPath(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
