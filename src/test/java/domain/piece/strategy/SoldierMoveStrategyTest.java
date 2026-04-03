package domain.piece.strategy;

import domain.board.Position;
import domain.piece.Camp;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SoldierMoveStrategyTest {
    private final MoveStrategy soldierMoveStrategy = new SoldierMoveStrategy(Camp.CHO.getForwardDirection());

    @Test
    void 졸은_두_칸_이상_이동할_수_없다() {
        Position from = new Position(8, 0);
        Position to = new Position(8, 2);

        assertThatThrownBy(() -> soldierMoveStrategy.getPath(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 졸은_세로_직선_방향으로_한_칸_이동하는_경로를_가진다() {
        Position from = new Position(8, 0);
        Position to = new Position(8, 1);

        List<Position> path = soldierMoveStrategy.getPath(from, to);

        assertThat(path).containsExactly(new Position(8, 1));
    }

    @Test
    void 졸은_가로_직선_방향으로_한_칸_이동하는_경로를_가진다() {
        Position from = new Position(8, 0);
        Position to = new Position(7, 0);

        List<Position> path = soldierMoveStrategy.getPath(from, to);

        assertThat(path).containsExactly(new Position(7, 0));
    }

    @Test
    void 졸은_대각선_방향으로_이동할_수_없다() {
        Position from = new Position(8, 0);
        Position to = new Position(7, 1);

        assertThatThrownBy(() -> soldierMoveStrategy.getPath(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 졸은_후퇴할_수_없다() {
        Position from = new Position(7, 2);
        Position to = new Position(7, 1);

        assertThatThrownBy(() -> soldierMoveStrategy.getPath(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
