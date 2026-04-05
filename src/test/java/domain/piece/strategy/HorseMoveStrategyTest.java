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

    @Test
    void 마의_이동_제약_검증_시_도착지_외의_경로에_다른_기물이_존재하지_않으면_이동할_수_있다() {
        Position to = new Position(7, 2);

        List<PathInfo> pathInfos = List.of(
                new PathInfo(to, Piece.of(Camp.HAN, PieceType.CHARIOT))
        );

        assertThatCode(() -> horseMoveStrategy.validateBlockingPiece(pathInfos, to))
                .doesNotThrowAnyException();
    }

    @Test
    void 마의_이동_제약_검증_시_도착지_외의_경로에_다른_기물이_존재하면_예외를_던진다() {
        Position to = new Position(7, 2);

        List<PathInfo> pathInfos = new ArrayList<>();
        pathInfos.add(new PathInfo(new Position(8, 1), Piece.of(Camp.HAN, PieceType.CHARIOT)));
        pathInfos.add(new PathInfo(to, Piece.of(Camp.CHO, PieceType.CHARIOT)));

        assertThatThrownBy(() -> horseMoveStrategy.validateBlockingPiece(pathInfos, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
