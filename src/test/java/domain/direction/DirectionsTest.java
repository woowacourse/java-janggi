package domain.direction;

import static org.assertj.core.api.Assertions.assertThat;

import domain.spatial.Position;
import domain.spatial.Vector;
import java.util.List;
import org.junit.jupiter.api.Test;

class DirectionsTest {

    @Test
    void 반복이_아닌_타겟_위치_방향을_찾아_이동_경로를_반환한다() {
        // given
        Position start = new Position(5, 5);
        Position target = new Position(6, 3);
        List<Position> expected = List.of(new Position(5, 4));

        List<Vector> vectors = List.of(new Vector(0, -1), new Vector(1, -1));
        List<Direction> directionElements = List.of(new Direction(vectors, false));
        Directions directions = new Directions(directionElements);

        // when
        List<Position> result = directions.getPath(start, target);

        // then
        assertThat(result).containsAll(expected);
    }

    @Test
    void 반복인_타겟_위치_방향을_찾아_이동_경로를_반환한다() {
        // given
        Position start = new Position(5, 5);
        Position target = new Position(9, 5);
        List<Position> expected = List.of(new Position(6, 5), new Position(7, 5), new Position(8, 5));

        List<Vector> vectors = List.of(new Vector(1, 0));
        List<Direction> directionElements = List.of(new Direction(vectors, true));
        Directions directions = new Directions(directionElements);

        // when
        List<Position> result = directions.getPath(start, target);

        // then
        assertThat(result).containsAll(expected);
    }
}
