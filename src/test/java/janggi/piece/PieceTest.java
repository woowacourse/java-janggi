package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.position.Position;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTest {

    @DisplayName("같은 팀의 기물을 잡는 경우 예외가 발생한다.")
    @Test
    void sameNation() {
        //given
        Piece piece = new StubPiece(new PieceProfile("테스트 말", Nation.HAN), new Position(0, 0));
        Piece other = new StubPiece(new PieceProfile("테스트 말", Nation.HAN), new Position(1, 0));

        //when //then
        assertThatThrownBy(() -> piece.validateSameNation(other))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    static class StubPiece extends Piece {

        protected StubPiece(final PieceProfile pieceProfile, final Position position) {
            super(pieceProfile, position);
        }

        @Override
        public void updatePiecePositionBy(final Position position) {

        }

        @Override
        public void checkObstacle(final Position futurePosition, final Map<Position, Piece> janggiBoard) {

        }

        @Override
        public List<Position> makeRoute(final Position position) {
            return List.of();
        }

        @Override
        public boolean isMove(final Position position) {
            return false;
        }
    }

}
