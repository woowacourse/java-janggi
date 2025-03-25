package domain.direction;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

class DirectionsTest {

    @Test
    void 반복이_아닌_타겟_위치_방향을_찾아_이동_경로를_반환한다() {
        // given
        Position start = Position.of(5, 5);
        Position target = Position.of(6, 3);
        List<Position> expected = List.of(Position.of(5, 4));

        List<Vector> vectors = List.of(Vector.UP, Vector.UP_RIGHT);
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
        Position start = Position.of(5, 5);
        Position target = Position.of(9, 5);
        List<Position> expected = List.of(Position.of(6, 5), Position.of(7, 5), Position.of(8, 5));

        List<Vector> vectors = List.of(Vector.RIGHT);
        List<Direction> directionElements = List.of(new Direction(vectors, true));
        Directions directions = new Directions(directionElements);

        // when
        List<Position> result = directions.getPath(start, target);

        // then
        assertThat(result).containsAll(expected);
    }
}
