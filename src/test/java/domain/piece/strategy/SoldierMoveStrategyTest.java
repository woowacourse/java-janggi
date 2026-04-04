package domain.piece.strategy;

import domain.board.Position;
import domain.path.PathInfo;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
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

    @Test
    void 졸의_이동_제약_검증_시_도착지_외의_경로가_포함되어_있으면_예외를_던진다() {
        Position to = new Position(7, 0);

        List<PathInfo> pathInfos = List.of(
                new PathInfo(new Position(8,0), Piece.of(Camp.CHO, PieceType.CHARIOT)),
                new PathInfo(to, Piece.of(Camp.CHO, PieceType.HORSE)));

        assertThatThrownBy(() -> soldierMoveStrategy.validateBlockingPiece(pathInfos, to))
                .isInstanceOf(IllegalStateException.class);
    }
}
