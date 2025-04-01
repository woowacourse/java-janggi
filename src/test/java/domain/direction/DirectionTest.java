package domain.direction;

import domain.spatial.Position;
import domain.spatial.Vector;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class DirectionTest {

    @ParameterizedTest
    @CsvSource(value = {
            "6,3,true",
            "6,2,false"
    })
    void 반복이_아닌_경우_타겟_위치_도달_여부를_판단한다(int row, int column, boolean expected) {
        // given
        Position start = new Position(5, 5);
        Position target = new Position(row, column);

        Direction direction = new Direction(List.of(
                new Vector(0, -1),
                new Vector(1, -1)
        ));

        // when
        boolean result = direction.canReach(start, target, false);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "5,9,true",
            "5,1,false"
    })
    void 반복인_경우_타겟_위치_도달_여부를_판단한다(int row, int column, boolean expected) {
        // given
        Position start = new Position(5, 5);
        Position target = new Position(row, column);

        Direction direction = new Direction(List.of(new Vector(0, 1)));

        // when
        boolean result = direction.canReach(start, target, true);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void 반복_이동이_없는_방향성에_대해_경로를_반환한다() {
        // given
        Position start = new Position(5, 5);
        Position target = new Position(6, 3);

        Direction direction = new Direction(List.of(
                new Vector(0, -1),
                new Vector(1, -1)
        ));

        List<Position> expected = List.of(
                start,
                new Position(5, 4),
                target
        );

        // when
        List<Position> result = direction.createPath(start, target, false);

        // then
        assertThat(result).containsExactlyElementsOf(expected);
    }

    @Test
    void 반복_이동이_있는_방향성에_대해_경로를_반환한다() {
        // given
        Position start = new Position(5, 5);
        Position target = new Position(5, 8);
        Direction direction = new Direction(List.of(new Vector(0, 1)));

        List<Position> expected = List.of(
                start,
                new Position(5, 6),
                new Position(5, 7),
                target
        );

        // when
        List<Position> result = direction.createPath(start, target, true);

        // then
        assertThat(result).containsExactlyElementsOf(expected);
    }
}
