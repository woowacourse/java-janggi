package domain.movestrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Position;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GuardMoveStrategyTest {

    @Test
    @DisplayName("사는 상하좌우 1칸 이동 가능하다.")
    public void moveTest() {
        // given
        Position from = Position.of(2, 5);

        List<Position> expected = List.of(
                Position.of(1, 4),
                Position.of(1, 5),
                Position.of(1, 6),
                Position.of(2, 4),
                Position.of(2, 6),
                Position.of(3, 4),
                Position.of(3, 5),
                Position.of(3, 6)
        );

        // when
        List<Position> positions = new GuardMoveStrategy().calculateMovablePositions(from, Map.of());

        // then
        assertThat(positions).containsAll(expected);
    }
}
