package domain.piece.strategy;

import domain.board.Position;
import domain.path.PathInfo;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CannonMoveStrategyTest {
    private final MoveStrategy chariotMoveStrategy = new ChariotMoveStrategy();

    @Test
    void 포는_세로_직선_방향의_이동_경로를_가진다() {
        Position from = new Position(8, 0);
        Position to = new Position(8, 2);

        List<Position> path = chariotMoveStrategy.getPath(from, to);

        assertThat(path).containsExactly(new Position(8, 1), new Position(8, 2));
    }

    @Test
    void 포는_가로_직선_방향의_이동_경로를_가진다() {
        Position from = new Position(8, 0);
        Position to = new Position(6, 0);

        List<Position> path = chariotMoveStrategy.getPath(from, to);

        assertThat(path).containsExactly(new Position(7, 0), new Position(6, 0));
    }

    @Test
    void 포는_대각선_방향으로_이동할_수_없다() {
        Position from = new Position(8, 0);
        Position to = new Position(7, 1);

        assertThatThrownBy(() -> chariotMoveStrategy.getPath(from, to)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포는_경로에_다른_기물이_없으면_이동할_수_없다() {
        Position from = new Position(8, 0);
        Position to = new Position(8, 2);

        List<PathInfo> pathInfos = new ArrayList<>();
        pathInfos.add(new PathInfo(new Position(8, 2), Piece.of(Camp.CHO, PieceType.CHARIOT)));

        assertThatThrownBy(() -> chariotMoveStrategy.validateBlockingPiece(pathInfos, to, from)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포는_포를_잡을_수_없다() {
        Position from = new Position(8, 0);
        Position to = new Position(8, 2);

        List<PathInfo> pathInfos = new ArrayList<>();
        pathInfos.add(new PathInfo(new Position(8, 2), Piece.of(Camp.CHO, PieceType.CANNON)));

        assertThatThrownBy(() -> chariotMoveStrategy.validateBlockingPiece(pathInfos, to, from)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포는_포를_넘을_수_없다() {
        Position from = new Position(8, 0);
        Position to = new Position(8, 2);

        List<PathInfo> pathInfos = new ArrayList<>();
        pathInfos.add(new PathInfo(new Position(8, 1), Piece.of(Camp.CHO, PieceType.CANNON)));
        pathInfos.add(new PathInfo(new Position(8, 2), Piece.of(Camp.CHO, PieceType.CHARIOT)));

        assertThatThrownBy(() -> chariotMoveStrategy.validateBlockingPiece(pathInfos, to, from)).isInstanceOf(IllegalArgumentException.class);
    }
}
