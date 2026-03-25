package domain.movestrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Position;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotMoveStrategyTest {

    @Test
    @DisplayName("차는 수직/수평으로 이동할 수 있다.")
    public void moveTest() {
        // given
        Position from = Position.of(1, 1);

        List<Position> expected = List.of(
                Position.of(1, 2),
                Position.of(1, 3),
                Position.of(1, 4),
                Position.of(1, 5),
                Position.of(1, 6),
                Position.of(1, 7),
                Position.of(1, 8),
                Position.of(1, 9),
                Position.of(2, 1),
                Position.of(3, 1),
                Position.of(4, 1),
                Position.of(5, 1),
                Position.of(6, 1),
                Position.of(7, 1),
                Position.of(8, 1),
                Position.of(9, 1),
                Position.of(10, 1)
        );

        // when
        List<Position> movable = new ChariotMoveStrategy().calculateMovablePositions(from, Map.of());

        // then
        assertThat(movable).containsAll(expected);
    }
}
