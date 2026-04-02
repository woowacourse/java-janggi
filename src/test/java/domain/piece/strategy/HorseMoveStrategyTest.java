package domain.piece.strategy;

import domain.board.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HorseMoveStrategyTest {
    private final MoveStrategy horseMoveStrategy = new HorseMoveStrategy();

    @Test
    void 마는_직선으로만_이동할_수_없다() {
        Position from = new Position(8, 0);
        Position to = new Position(8, 2);

        assertThatThrownBy(() -> horseMoveStrategy.getPath(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 마는_직선으로_한_칸_이동_후_대각선으로_한_칸_이동하는_경로를_가진다() {
        Position from = new Position(8, 0);
        Position to = new Position(7, 2);

        List<Position> path = horseMoveStrategy.getPath(from, to);

        assertThat(path).containsExactly(
                new Position(8, 1),
                new Position(7, 2));
    }
}
