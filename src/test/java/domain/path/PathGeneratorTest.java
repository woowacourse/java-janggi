package domain.path;

import domain.board.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PathGeneratorTest {
    @Test
    void 직선_경로를_정상적으로_생성한다() {
        Position departure = new Position(0, 0);
        Position destination = new Position(0, 2);
        Direction direction = Direction.UP;

        List<Position> path = PathGenerator.generateStraightPath(departure, destination, direction);

        assertThat(path).containsExactly(
                new Position(0, 1),
                new Position(0, 2)
        );
    }

    @Test
    void 복합_경로를_정상적으로_생성한다() {
        Position departure = new Position(8, 0);
        List<Direction> directions = List.of(Direction.UP, Direction.NORTHWEST);

        List<Position> path = PathGenerator.generateComplexPath(departure, directions);

        assertThat(path).containsExactly(
                new Position(8, 1),
                new Position(7, 2)
        );
    }
}
