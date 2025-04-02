package domain.direction;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Position;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DirectionsTest {

    @Test
    void 반복이_아닌_타겟_위치_방향을_찾아_이동_경로를_반환한다() {
        // given
        Position start = Position.of(5, 5);
        Position target = Position.of(6, 3);
        List<Position> expected = List.of(Position.of(5, 4));

        List<Vector> vectors = List.of(Vector.UP, Vector.UP_RIGHT);
        Set<Direction> directionElements = Set.of(new Direction(vectors));
        Directions directions = new Directions(directionElements, false);

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
        Set<Direction> directionElements = Set.of(new Direction(vectors));
        Directions directions = new Directions(directionElements, true);

        // when
        List<Position> result = directions.getPath(start, target);

        // then
        assertThat(result).containsAll(expected);
    }

    @Test
    void 현재_방향들에_새로운_방향을_추가한다() {
        // given
        List<Vector> vectors = List.of(Vector.RIGHT);
        Set<Direction> directionElements = Set.of(new Direction(vectors));
        Directions directions = new Directions(directionElements, true);
        Set<Direction> targetDirections = Set.of(
                new Direction(List.of(Vector.UP_RIGHT)),
                new Direction(List.of(Vector.UP)),
                new Direction(List.of(Vector.UP_LEFT))
        );

        Directions expect = new Directions(
                Set.of(
                        new Direction(List.of(Vector.UP_RIGHT)),
                        new Direction(List.of(Vector.UP)),
                        new Direction(List.of(Vector.UP_LEFT)),
                        new Direction(List.of(Vector.RIGHT))
                ), true
        );

        // when
        Directions result = directions.addDirection(targetDirections);

        // then
        assertThat(result).isEqualTo(expect);
    }
}
