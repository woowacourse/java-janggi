package domain.direction;

import domain.piece.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DirectionTest {

    @Test
    void 이동_경로를_반환한다() {
        // given
        Position start = Position.of(5, 5);
        Position target = Position.of(6, 3);
        List<Position> expected = List.of(Position.of(5, 4));

        List<Position> positions = List.of(Position.ofDirection(0, -1), Position.ofDirection(1, -1));
        Direction direction = new Direction(positions);

        // when
        List<Position> result = direction.createPath(start, target);

        // then
        assertThat(result)
                .containsAll(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "6,3,true",
            "6,2,false"
    })
    void 해당_위치로_도달할_수_있는지_판단한다(int row, int column, boolean expectedResult) {
        // given
        Position start = Position.of(5, 5);
        Position target = Position.of(row, column);

        List<Position> positions = List.of(Position.ofDirection(0, -1), Position.ofDirection(1, -1));
        Direction direction = new Direction(positions);

        // when
        boolean result = direction.canReach(start, target);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }
}
