package domain.piece.strategy;

import domain.board.Position;
import domain.path.PathInfo;
import domain.path.PathInfos;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class GeneralMoveStrategyTest {
    private final MoveStrategy generalMoveStrategy = new GeneralMoveStrategy();

    @Test
    void 궁과_사는_두_칸_이상_이동할_수_없다() {
        Position from = new Position(8, 0);
        Position to = new Position(8, 2);

        assertThatThrownBy(() -> generalMoveStrategy.getPath(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Nested
    class 궁과_사_이동_방향_테스트 {
        @Test
        void 궁과_사는_위쪽_직선_방향으로_한칸_이동하는_경로를_가진다() {
            Position from = new Position(4, 8);
            Position to = new Position(4, 9);

            List<Position> path = generalMoveStrategy.getPath(from, to);

            assertThat(path).containsExactly(new Position(4, 9));
        }

        @Test
        void 궁과_사는_아래쪽_직선_방향으로_한칸_이동하는_경로를_가진다() {
            Position from = new Position(4, 8);
            Position to = new Position(4, 7);

            List<Position> path = generalMoveStrategy.getPath(from, to);

            assertThat(path).containsExactly(new Position(4, 7));
        }

        @Test
        void 궁과_사는_왼쪽_직선_방향으로_한_칸_이동하는_경로를_가진다() {
            Position from = new Position(4, 8);
            Position to = new Position(3, 8);

            List<Position> path = generalMoveStrategy.getPath(from, to);

            assertThat(path).containsExactly(new Position(3, 8));
        }

        @Test
        void 궁과_사는_오른쪽_직선_방향으로_한_칸_이동하는_경로를_가진다() {
            Position from = new Position(4, 8);
            Position to = new Position(5, 8);

            List<Position> path = generalMoveStrategy.getPath(from, to);

            assertThat(path).containsExactly(new Position(5, 8));
        }

        @Test
        void 궁과_사는_궁성_내에서_지정된_대각선_경로가_아니면_대각선_방향으로_이동할_수_없다(){
            Position from = new Position(4,7);
            Position to = new Position(3, 8);

            assertThatThrownBy(() -> generalMoveStrategy.getPath(from, to))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 궁과_사는_궁성_내에서_지정된_대각선_경로를_통해_대각선_방향으로_이동할_수_있다() {
            Position from = new Position(4, 8);
            Position to = new Position(3, 7);

            List<Position> path = generalMoveStrategy.getPath(from, to);

            assertThat(path).containsExactly(new Position(3, 7));
        }

        @Test
        void 궁과_사는_궁성_외부로_이동할_수_없다(){
            Position from = new Position(5, 7);
            Position to = new Position(6, 7);

            assertThatThrownBy(() -> generalMoveStrategy.getPath(from, to))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void 궁과_사의_이동_제약_검증_시_경로에_도착지_외에_경로가_포함되어_있지_않으면_이동할_수_있다() {
        Position to = new Position(7, 0);

        List<PathInfo> pathInfos = List.of(
                new PathInfo(to, Piece.of(Camp.CHO, PieceType.HORSE)));

        assertThatCode(() -> generalMoveStrategy.validateBlockingPiece(new PathInfos(pathInfos), to))
                .doesNotThrowAnyException();
    }

    @Test
    void 궁과_사의_이동_제약_검증_시_도착지_외의_경로가_포함되어_있으면_예외를_던진다() {
        Position to = new Position(7, 0);

        List<PathInfo> pathInfos = List.of(
                new PathInfo(new Position(8, 0), Piece.of(Camp.CHO, PieceType.CHARIOT)),
                new PathInfo(to, Piece.of(Camp.CHO, PieceType.SOLDIER))
        );

        assertThatThrownBy(() -> generalMoveStrategy.validateBlockingPiece(new PathInfos(pathInfos), to))
                .isInstanceOf(IllegalStateException.class);
    }
}
