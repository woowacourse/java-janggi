package domain.direction;

import domain.piece.Position;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class DirectionTest {

    @Disabled
    @Test
    void 이동_경로를_반환한다() {
        // given
        // 마의 이동
        Position start = new Position(5, 5);
        Position target = new Position(6, 3);

        List<Position> positions = List.of(new Position(0, -1), new Position(1, -1));
//        Direction direction = new Direction(positions);

        // when
//        direction.

        // then

    }

    @Test
    void 해당_위치로_도달할_수_있는지_판단한다() {
        // given
        Position start = new Position(5, 5);
        Position target = new Position(6, 3);

        List<Position> positions = List.of(new Position(0, -1), new Position(1, -1));
        Direction direction = new Direction(positions);

        // when
        boolean result = direction.canReach(start, target);

        // then
        Assertions.assertThat(result).isTrue();
    }
}
