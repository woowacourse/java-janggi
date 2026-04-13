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

class ElephantMoveStrategyTest {
    private final MoveStrategy elephantMoveStrategy = new ElephantMoveStrategy();

    @Nested
    class 상_이동_방향_테스트 {
        @Test
        void 상은_직선으로만_이동할_수_없다() {
            Position from = new Position(8, 0);
            Position to = new Position(8, 2);

            assertThatThrownBy(() -> elephantMoveStrategy.getPath(from, to))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 상은_위쪽으로_한_칸_이동_후_북서쪽으로_두_칸_이동하는_경로를_가진다() {
            Position from = new Position(4, 4);
            Position to = new Position(2, 7);

            List<Position> path = elephantMoveStrategy.getPath(from, to);

            assertThat(path).containsExactly(
                    new Position(4, 5),
                    new Position(3, 6),
                    new Position(2, 7)
            );
        }

        @Test
        void 상은_위쪽으로_한_칸_이동_후_북동쪽으로_두_칸_이동하는_경로를_가진다() {
            Position from = new Position(4, 4);
            Position to = new Position(6, 7);

            List<Position> path = elephantMoveStrategy.getPath(from, to);

            assertThat(path).containsExactly(
                    new Position(4, 5),
                    new Position(5, 6),
                    new Position(6, 7)
            );
        }

        @Test
        void 상은_아래쪽으로_한_칸_이동_후_남서쪽으로_두_칸_이동하는_경로를_가진다() {
            Position from = new Position(4, 4);
            Position to = new Position(2, 1);

            List<Position> path = elephantMoveStrategy.getPath(from, to);

            assertThat(path).containsExactly(
                    new Position(4, 3),
                    new Position(3, 2),
                    new Position(2, 1)
            );
        }

        @Test
        void 상은_아래쪽으로_한_칸_이동_후_남동쪽으로_두_칸_이동하는_경로를_가진다() {
            Position from = new Position(4, 4);
            Position to = new Position(6, 1);

            List<Position> path = elephantMoveStrategy.getPath(from, to);

            assertThat(path).containsExactly(
                    new Position(4, 3),
                    new Position(5, 2),
                    new Position(6, 1)
            );
        }

        @Test
        void 상은_오른쪽으로_한_칸_이동_후_북동쪽으로_두_칸_이동하는_경로를_가진다() {
            Position from = new Position(4, 4);
            Position to = new Position(7, 6);

            List<Position> path = elephantMoveStrategy.getPath(from, to);

            assertThat(path).containsExactly(
                    new Position(5, 4),
                    new Position(6, 5),
                    new Position(7, 6)
            );
        }

        @Test
        void 상은_오른쪽으로_한_칸_이동_후_남동쪽으로_두_칸_이동하는_경로를_가진다() {
            Position from = new Position(4, 4);
            Position to = new Position(7, 2);

            List<Position> path = elephantMoveStrategy.getPath(from, to);

            assertThat(path).containsExactly(
                    new Position(5, 4),
                    new Position(6, 3),
                    new Position(7, 2)
            );
        }

        @Test
        void 상은_왼쪽으로_한_칸_이동_후_북서쪽으로_두_칸_이동하는_경로를_가진다() {
            Position from = new Position(4, 4);
            Position to = new Position(1, 6);

            List<Position> path = elephantMoveStrategy.getPath(from, to);

            assertThat(path).containsExactly(
                    new Position(3, 4),
                    new Position(2, 5),
                    new Position(1, 6)
            );
        }

        @Test
        void 상은_왼쪽으로_한_칸_이동_후_남서쪽으로_두_칸_이동하는_경로를_가진다() {
            Position from = new Position(4, 4);
            Position to = new Position(1, 2);

            List<Position> path = elephantMoveStrategy.getPath(from, to);

            assertThat(path).containsExactly(
                    new Position(3, 4),
                    new Position(2, 3),
                    new Position(1, 2)
            );
        }
    }

    @Test
    void 상의_이동_제약_검증_시_도착지_외의_경로에_다른_기물이_존재하지_않으면_이동할_수_있다() {
        Position to = new Position(8, 1);

        List<PathInfo> pathInfos = List.of(
                new PathInfo(to, Piece.of(Camp.HAN, PieceType.SOLDIER))
        );

        assertThatCode(() -> elephantMoveStrategy.validateBlockingPiece(new PathInfos(pathInfos), to))
                .doesNotThrowAnyException();
    }

    @Test
    void 상의_이동_제약_검증_시_도착지_외의_경로에_다른_기물이_존재하면_예외를_던진다() {
        Position to = new Position(8, 1);

        List<PathInfo> pathInfos = new ArrayList<>();
        pathInfos.add(new PathInfo(new Position(8, 0), Piece.of(Camp.CHO, PieceType.CHARIOT)));
        pathInfos.add(new PathInfo(to, Piece.of(Camp.HAN, PieceType.CHARIOT)));

        assertThatThrownBy(() -> elephantMoveStrategy.validateBlockingPiece(new PathInfos(pathInfos), to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
