package domain.piece;

import domain.board.Position;
import domain.piece.strategy.GeneralMoveStrategy;
import domain.piece.strategy.MoveStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GeneralMoveStrategyTest {
    private final MoveStrategy generalMoveStrategy = new GeneralMoveStrategy();

    @Test
    void 궁과_사는_두_칸_이상_이동할_수_없다() {
        Position from = new Position(8, 0);
        Position to = new Position(8, 2);

        assertThatThrownBy(() -> generalMoveStrategy.getPath(from, to)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 궁과_사는_세로_직선_방향으로_한_칸_이동하는_경로를_가진다() {
        Position from = new Position(8, 0);
        Position to = new Position(8, 1);

        List<Position> path = generalMoveStrategy.getPath(from, to);

        assertThat(path).containsExactly(new Position(8, 1));
    }

    @Test
    void 궁과_사는_가로_직선_방향으로_한_칸_이동하는_경로를_가진다() {
        Position from = new Position(8, 0);
        Position to = new Position(7, 0);

        List<Position> path = generalMoveStrategy.getPath(from, to);

        assertThat(path).containsExactly(new Position(7, 0));
    }

    @Test
    void 궁과_사는_대각선_방향으로_이동할_수_없다() {
        Position from = new Position(8, 0);
        Position to = new Position(7, 1);

        assertThatThrownBy(() -> generalMoveStrategy.getPath(from, to)).isInstanceOf(IllegalArgumentException.class);
    }
}
