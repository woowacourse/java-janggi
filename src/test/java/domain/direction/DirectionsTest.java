package domain.direction;

import domain.piece.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DirectionsTest {

    @Test
    void 타겟_위치_방향을_찾아_이동_경로를_반환한다() {
        // given
        Position start = Position.of(5, 5);
        Position target = Position.of(6, 3);
        List<Position> expected = List.of(Position.of(5, 4));

        List<Position> positions = List.of(Position.ofDirection(0, -1), Position.ofDirection(1, -1));
        List<Direction> directionElements = List.of(new Direction(positions, false));
        Directions directions = new Directions(directionElements);

        // when
        List<Position> result = directions.getPath(start, target);

        // then
        assertThat(result).containsAll(expected);
    }
}
