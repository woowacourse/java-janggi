package domain.piece.strategy;

import domain.piece.MoveErrorMessage;
import domain.position.Position;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SlidingMoveStrategyTest {
    @Test
    @DisplayName("같은 Row이면 목적지까지의 경로를 반환해야 한다")
    void findMovablePath_success_same_row() {
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

    @Test
    @DisplayName("같은 Row이면 목적지까지의 경로를 반환해야 한다")
    void findMovablePath_success_same_column() {
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

    @Test
    @DisplayName("목적지까지 이동한 가능한 경로가 없는 경우 예외가 발상해야 한다")
    void findMovablePath_fail_now_same_row_and_column() {
        // given
        Position start = Position.of(1, 2);
        Position destination = Position.of(3, 4);

        // when
        MoveStrategy strategy = new SlidingMoveStrategy();

        Assertions.assertThatThrownBy(() -> strategy.findMovablePath(start, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(MoveErrorMessage.NOT_EXIST_MOVABLE_PATH.getMessage());
    }
}
