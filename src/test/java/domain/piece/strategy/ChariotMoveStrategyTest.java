package domain.piece.strategy;

import domain.board.Position;
import domain.path.PathInfo;
import domain.path.PathInfos;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ChariotMoveStrategyTest {
    private final MoveStrategy chariotMoveStrategy = new ChariotMoveStrategy();

    @Nested
    class 차_이동_방향_테스트 {
        @Test
        void 차는_위쪽_직선_방향의_이동_경로를_가진다() {
            Position from = new Position(8, 0);
            Position to = new Position(8, 2);

            List<Position> path = chariotMoveStrategy.getPath(from, to);

            assertThat(path).containsExactly(
                    new Position(8, 1),
                    new Position(8, 2));
        }

        @Test
        void 차는_아래쪽_직선_방향의_이동_경로를_가진다() {
            Position from = new Position(8, 2);
            Position to = new Position(8, 0);

            List<Position> path = chariotMoveStrategy.getPath(from, to);

            assertThat(path).containsExactly(
                    new Position(8, 1),
                    new Position(8, 0));
        }

        @Test
        void 차는_왼쪽_직선_방향의_이동_경로를_가진다() {
            Position from = new Position(8, 0);
            Position to = new Position(6, 0);

            List<Position> path = chariotMoveStrategy.getPath(from, to);

            assertThat(path).containsExactly(
                    new Position(7, 0),
                    new Position(6, 0));
        }

        @Test
        void 차는_오른쪽_직선_방향의_이동_경로를_가진다() {
            Position from = new Position(6, 0);
            Position to = new Position(8, 0);

            List<Position> path = chariotMoveStrategy.getPath(from, to);

            assertThat(path).containsExactly(
                    new Position(7, 0),
                    new Position(8, 0));
        }

        @Test
        void 차는_대각선_방향으로_이동할_수_없다() {
            Position from = new Position(8, 0);
            Position to = new Position(7, 1);

            assertThatThrownBy(() -> chariotMoveStrategy.getPath(from, to))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 차는_궁성_내에서_지정된_대각선_경로를_통해_대각선_방향으로_이동할_수_있다() {
            Position from = new Position(3, 9);
            Position to = new Position(4, 8);

            List<Position> path = chariotMoveStrategy.getPath(from, to);

            assertThat(path).containsExactly(
                    new Position(4, 8));
        }
    }

    @Test
    void 차의_이동_제약_검증_시_도착지_외의_경로에_다른_기물이_존재하지_않으면_이동할_수_있다() {
        Position to = new Position(8, 2);

        List<PathInfo> pathInfos = new ArrayList<>();
        pathInfos.add(new PathInfo(to, Piece.of(Camp.CHO, PieceType.HORSE)));

        assertThatCode(() -> chariotMoveStrategy.validateBlockingPiece(new PathInfos(pathInfos), to))
                .doesNotThrowAnyException();
    }

    @Test
    void 차의_이동_제약_검증_시_도착지_외의_경로에_다른_기물이_존재하면_예외를_던진다() {
        Position to = new Position(8, 2);

        List<PathInfo> pathInfos = new ArrayList<>();
        pathInfos.add(new PathInfo(new Position(8, 1), Piece.of(Camp.CHO, PieceType.CHARIOT)));
        pathInfos.add(new PathInfo(to, Piece.of(Camp.CHO, PieceType.HORSE)));

        assertThatThrownBy(() -> chariotMoveStrategy.validateBlockingPiece(new PathInfos(pathInfos), to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
