package domain.direction;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Position;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DirectionTest {

    @Test
    void 반복_이동이_없는_방향성에_대해_경로를_반환한다() {
        // given
        Position start = Position.of(5, 5);
        Position target = Position.of(6, 3);
        List<Position> expected = List.of(Position.of(5, 4));

        List<Position> positions = List.of(Position.ofDirection(0, -1), Position.ofDirection(1, -1));
        Direction direction = new Direction(positions, false);

        // when
        List<Position> result = direction.createPath(start, target);

        // then
        assertThat(result).containsAll(expected);
    }

    @Test
    void 반복_이동이_있는_방향성에_대해_경로를_반환한다() {
        // given
        Position start = Position.of(5, 5);
        Position target = Position.of(5, 9);
        List<Position> expected = List.of(Position.of(5, 6), Position.of(5, 7), Position.of(5, 8));

        List<Position> positions = List.of(Position.ofDirection(0, 1));
        Direction direction = new Direction(positions, true);

        // when
        List<Position> result = direction.createPath(start, target);

        // then
        assertThat(result).containsAll(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "6,3,true",
            "6,2,false"
    })
    void 반복이_아닌_경우_타겟_위치_도달_여부를_판단한다(int row, int column, boolean expectedResult) {
        // given
        Position start = Position.of(5, 5);
        Position target = Position.of(row, column);

        List<Position> positions = List.of(Position.ofDirection(0, -1), Position.ofDirection(1, -1));
        Direction direction = new Direction(positions, false);

        // when
        boolean result = direction.canReach(start, target);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "5,9,true",
            "5,1,false"
    })
    void 반복인_경우_타겟_위치_도달_여부를_판단한다(int row, int column, boolean expectedResult) {
        // given
        Position start = Position.of(5, 5);
        Position target = Position.of(row, column);

        List<Position> positions = List.of(Position.ofDirection(0, 1));
        Direction direction = new Direction(positions, true);

        // when
        boolean result = direction.canReach(start, target);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }
}
