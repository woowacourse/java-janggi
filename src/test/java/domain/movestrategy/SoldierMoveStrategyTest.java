package domain.movestrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Position;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SoldierMoveStrategyTest {

    @Test
    @DisplayName("졸은 앞과 양 옆으로 1칸 이동할 수 있다.")
    public void moveTest() {
        // given
        Position from = Position.of(4, 3);

        List<Position> expected = List.of(
                Position.of(4, 2),
                Position.of(4, 4),
                Position.of(5, 3)
        );

        // when
        List<Position> movable = new SoldierMoveStrategy().calculateMovablePositions(from, Map.of());

        // then
        assertThat(movable).containsAll(expected);

    }
}
