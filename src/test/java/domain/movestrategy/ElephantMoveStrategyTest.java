package domain.movestrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Position;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElephantMoveStrategyTest {

    @Test
    @DisplayName("코끼리는 상하좌우 1칸 + 대각선 2칸 이동 가능하다.")
    public void moveTest() {
        // given
        Position from = Position.of(5, 5);

        List<Position> expected = List.of(
                Position.of(2, 3),
                Position.of(2, 7),
                Position.of(3, 8),
                Position.of(7, 8),
                Position.of(8, 3),
                Position.of(8, 7),
                Position.of(3, 2),
                Position.of(7, 2)
        );

        // when
        List<Position> positions = new ElephantMoveStrategy().calculateMovablePositions(from, Map.of());

        // then
        assertThat(positions).containsAll(expected);
    }
}
