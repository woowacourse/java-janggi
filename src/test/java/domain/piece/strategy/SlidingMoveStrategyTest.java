package domain.piece.strategy;

import domain.position.Position;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class SlidingMoveStrategyTest {
    // 같은 row일떄
    @Test
    void 같은_Row이면_목적지까지의_경로를_반환해야_한다() {
        // given
        Position start = Position.of(1, 1);
        Position destination = Position.of(1, 8);

        // when
        MoveStrategy strategy = new SlidingMoveStrategy();
        List<Position> movablePath = strategy.findMovablePath(start, destination);

        // then
        int expect = destination.getColumn().getValue() - start.getColumn().getValue() - 1;
        Assertions.assertThat(movablePath.size()).isEqualTo(expect);
        Assertions.assertThat(movablePath).contains(
                Position.of(1, 2),
                Position.of(1, 3),
                Position.of(1, 4),
                Position.of(1, 5),
                Position.of(1, 6),
                Position.of(1, 7)
        );
    }
    // 같은 col 일때

    @Test
    void 같은_Column이면_목적지까지의_경로를_반환해야_한다() {
        // given
        Position start = Position.of(1, 1);
        Position destination = Position.of(8, 1);

        // when
        MoveStrategy strategy = new SlidingMoveStrategy();
        List<Position> movablePath = strategy.findMovablePath(start, destination);

        // then
        int expect = destination.getRow().getValue() - start.getRow().getValue() - 1;
        Assertions.assertThat(movablePath.size()).isEqualTo(expect);
        Assertions.assertThat(movablePath).contains(
                Position.of(2, 1),
                Position.of(3, 1),
                Position.of(4, 1),
                Position.of(5, 1),
                Position.of(6, 1),
                Position.of(7, 1)
        );
    }

    // 둘 다 아닐때
    @Test
    void 둘_다_위치가_다를_경우_예외가_발생해야_한다() {
        // given
        Position start = Position.of(1, 2);
        Position destination = Position.of(3, 4);

        // when
        MoveStrategy strategy = new SlidingMoveStrategy();

        Assertions.assertThatThrownBy(() -> strategy.findMovablePath(start, destination)).isInstanceOf(
                IllegalArgumentException.class);
    }
}
