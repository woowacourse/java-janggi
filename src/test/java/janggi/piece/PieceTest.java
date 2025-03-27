package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.position.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTest {

    @DisplayName("같은 팀의 기물을 잡는 경우 예외가 발생한다.")
    @Test
    void sameNation() {
        //given
        final Piece piece = new StubPiece(Team.HAN);
        final Piece other = new StubPiece(Team.HAN);

        //when //then
        assertThatThrownBy(() -> piece.validateTeam(other))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("다른 팀의 기물을 선택하면 예외가 발생한다.")
    @Test
    void nonSameTeam() {
        //given
        final Piece piece = new StubPiece(Team.HAN);
        final Piece other = new StubPiece(Team.CHU);

        //when //then
        assertThatThrownBy(() -> piece.validateTeam(other.getTeam()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    static class StubPiece extends Piece {

        protected StubPiece(final Team team) {
            super(null, team);
        }

        @Override
        public List<Position> makeRoute(final Position currentPosition, final Position targetPosition) {
            return List.of();
        }

        @Override
        protected void canMoveBy(final Position currentPosition, final Position targetPosition) {

        }
    }
}
