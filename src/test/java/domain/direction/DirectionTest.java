package domain.direction;

import domain.piece.Position;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DirectionTest {

    @Disabled
    @Test
    void 이동_경로를_반환한다() {
        // given
        // 마의 이동
//        Position start = Position.ofDirection(5, 5);
//        Position target = Position.ofDirection((6, 3);
//
//        List<Position> positions = List.of(Position.ofDirection((0, -1), Position.ofDirection((1, -1));
////        Direction direction = new Direction(positions);

        // when
//        direction.

        // then

    }

    @ParameterizedTest
    @CsvSource(value = {
            "6,3,true",
            "6,2,false"
    })
    void 해당_위치로_도달할_수_있는지_판단한다(int row, int column, boolean expectedResult) {
        // given
        Position start = Position.ofDirection(5, 5);
        Position target = Position.ofDirection(row, column);

        List<Position> positions = List.of(Position.ofDirection(0, -1), Position.ofDirection(1, -1));
        Direction direction = new Direction(positions);

        // when
        boolean result = direction.canReach(start, target);

        // then
        Assertions.assertThat(result).isEqualTo(expectedResult);
    }
}
