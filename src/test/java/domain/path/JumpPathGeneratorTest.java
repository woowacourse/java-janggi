package domain.path;

import domain.board.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JumpPathGeneratorTest {

    @Test
    void 점프_횟수가_한_번인_경로를_생성한다() {
        Position departure = new Position(4, 4);
        Position destination = new Position(3, 6);

        List<Position> path = JumpPathGenerator.getPath(departure, destination, 1);

        assertThat(path).containsExactly(
                new Position(4, 5),
                new Position(3, 6)
        );
    }

    @Test
    void 점프_횟수가_두_번인_경로를_생성한다() {
        Position departure = new Position(4, 4);
        Position destination = new Position(2, 7);

        List<Position> path = JumpPathGenerator.getPath(departure, destination, 2);

        assertThat(path).containsExactly(
                new Position(4, 5),
                new Position(3, 6),
                new Position(2, 7)
        );
    }
}
