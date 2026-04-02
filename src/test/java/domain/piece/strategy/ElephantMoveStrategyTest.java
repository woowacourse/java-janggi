package domain.piece.strategy;

import domain.board.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ElephantMoveStrategyTest {
    private final MoveStrategy elephantMoveStrategy = new ElephantMoveStrategy();

    @Test
    void 상은_직선으로만_이동할_수_없다() {
        Position from = new Position(8, 0);
        Position to = new Position(8, 2);

        assertThatThrownBy(() -> elephantMoveStrategy.getPath(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상은_직선으로_한_칸_이동_후_대각선으로_두_칸_이동하는_경로를_가진다() {
        Position from = new Position(8, 0);
        Position to = new Position(6, 3);

        List<Position> path = elephantMoveStrategy.getPath(from, to);

        assertThat(path).containsExactly(
                new Position(8, 1),
                new Position(7, 2),
                new Position(6, 3));
    }
}
