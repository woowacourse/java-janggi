package domain.movestrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HorseMoveStrategyTest {

    @Test
    @DisplayName("말은 상하좌우 1칸 + 대각선 1칸 이동 가능하다.")
    public void moveTest() {
        // given
        Position from = Position.of(5, 5);

        List<Position> expected = List.of(
                Position.of(3, 4),
                Position.of(3, 6),
                Position.of(4, 7),
                Position.of(6, 7),
                Position.of(7, 4),
                Position.of(7, 6),
                Position.of(4, 3),
                Position.of(6, 3)
        );

        // when
        List<Position> positions = new HorseMoveStrategy().calculateMovablePositions(from);

        // then
        assertThat(positions).containsAll(expected);
    }
}
