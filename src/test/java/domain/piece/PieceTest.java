package domain.piece;

import domain.Position;
import domain.Side;
import domain.strategy.PathMovement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class PieceTest {

    static class TestPiece extends Piece {

        public TestPiece() {
            super(Side.CHO, new PathMovement());
        }

        @Override
        public List<Position> findRoute(Position sourcePosition) {
            return List.of();
        }

        @Override
        public List<Position> findPathTo(Position source, Position target) {
            return List.of();
        }

        @Override
        public String getName() {
            return "";
        }

        @Override
        public double getScore() {
            return 0;
        }
    }


    @DisplayName("기물 이동 경로는 모두 비어있어야한다.")
    @Test
    void 기물_이동_경로는_모두_비어있어야한다() {
        // given
        List<Piece> pieces = List.of(new Empty(), new Empty());
        // when
        Piece piece = new TestPiece();
        Assertions.assertThatCode(() -> piece.checkRoute(pieces))
                .doesNotThrowAnyException();
        // then
    }
}
