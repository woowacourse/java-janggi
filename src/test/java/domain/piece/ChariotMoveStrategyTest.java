package domain.piece;

import domain.board.Position;
import domain.path.PathInfo;
import domain.piece.strategy.ChariotMoveStrategy;
import domain.piece.strategy.MoveStrategy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChariotMoveStrategyTest {
    private final MoveStrategy chariotMoveStrategy = new ChariotMoveStrategy();

    @Test
    void 차는_세로_직선_방향으로_이동할_수_있다() {
        Position from = new Position(8, 0);
        Position to = new Position(8, 2);

        List<Position> path = chariotMoveStrategy.getPath(from, to);

        assertThat(path).containsExactly(new Position(8, 1), new Position(8, 2));
    }

    @Test
    void 차는_가로_직선_방향으로_이동할_수_있다() {
        Position from = new Position(8, 0);
        Position to = new Position(6, 0);

        List<Position> path = chariotMoveStrategy.getPath(from, to);

        assertThat(path).containsExactly(new Position(7, 0), new Position(6, 0));
    }

    @Test
    void 차는_대각선_방향으로_이동할_수_없다() {
        Position from = new Position(8, 0);
        Position to = new Position(7, 1);

        assertThatThrownBy(() -> chariotMoveStrategy.getPath(from, to)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 차는_경로에_다른_기물이_있으면_이동할_수_없다() {
        Position from = new Position(8, 0);
        Position to = new Position(8, 2);

        List<PathInfo> pathInfos = new ArrayList<>();
        pathInfos.add(new PathInfo(new Position(8, 1), Piece.of(Camp.CHO, PieceType.CHARIOT)));

        assertThatThrownBy(() -> chariotMoveStrategy.validateBlockingPiece(pathInfos, to, from)).isInstanceOf(IllegalArgumentException.class);
    }
}
