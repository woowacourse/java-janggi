package domain.piece.strategy;

import domain.board.Position;
import domain.path.PathInfo;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

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

    @Test
    void 상의_이동_제약_검증_시_도착지_외의_경로에_다른_기물이_존재하지_않으면_이동할_수_있다() {
        Position to = new Position(8, 1);

        List<PathInfo> pathInfos = List.of(
                new PathInfo(to, Piece.of(Camp.HAN, PieceType.SOLDIER))
        );

        assertThatCode(() -> elephantMoveStrategy.validateBlockingPiece(pathInfos, to))
                .doesNotThrowAnyException();
    }

    @Test
    void 상의_이동_제약_검증_시_도착지_외의_경로에_다른_기물이_존재하면_예외를_던진다() {
        Position to = new Position(8, 1);

        List<PathInfo> pathInfos = new ArrayList<>();
        pathInfos.add(new PathInfo(new Position(8, 0), Piece.of(Camp.CHO, PieceType.CHARIOT)));
        pathInfos.add(new PathInfo(to, Piece.of(Camp.HAN, PieceType.CHARIOT)));

        assertThatThrownBy(() -> elephantMoveStrategy.validateBlockingPiece(pathInfos, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
