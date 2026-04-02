package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;

public class DirectionTest {

    @Test
    void 두_칸이_한_칸_북쪽_차이면_NORTH를_반환한다() {
        Position from = Position.of(5, 5);
        Position to = Position.of(4, 5);

        Optional<Direction> direction = Direction.of(from, to);

        assertThat(direction).contains(Direction.NORTH);
    }

    @Test
    void 두_칸이_한_칸_대각_변위면_해당_대각_방향을_반환한다() {
        Position from = Position.of(1, 1);
        Position to = Position.of(2, 2);

        Optional<Direction> direction = Direction.of(from, to);

        assertThat(direction).contains(Direction.SOUTH_EAST);
    }

    @Test
    void 단위_변위_한_칸이_아니면_빈_Optional을_반환한다() {
        Position from = Position.of(0, 0);
        Position to = Position.of(0, 2);

        Optional<Direction> direction = Direction.of(from, to);

        assertThat(direction).isEmpty();
    }
}
