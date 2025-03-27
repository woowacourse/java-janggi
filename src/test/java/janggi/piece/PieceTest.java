package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
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
        final Piece piece = new StubPiece(Team.HAN, new Position(0, 0));
        final Piece other = new StubPiece(Team.HAN, new Position(1, 0));

        //when //then
        assertThatThrownBy(() -> piece.validateTeam(other))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("다른 팀의 기물을 선택하면 예외가 발생한다.")
    @Test
    void nonSameTeam() {
        //given
        final Piece piece = new StubPiece(Team.HAN, new Position(0, 0));
        final Piece other = new StubPiece(Team.CHU, new Position(1, 0));

        //when //then
        assertThatThrownBy(() -> piece.validateTeam(other.getPieceProfile().getNation()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("위치를 변경할 수 있다.")
    @Test
    void updatePosition() {
        //given
        final Piece piece = new StubPiece(Team.HAN, new Position(0, 0));

        final Position position = new Position(1, 1);

        //when
        piece.updatePiecePositionBy(position);

        //then
        assertThat(piece).isEqualTo(new StubPiece(Team.HAN, new Position(1, 1)));
    }

    static class StubPiece extends Piece {

        protected StubPiece(final Team team, final Position position) {
            super(new PieceProfile(null, team), position);
        }

        @Override
        public List<Position> makeRoute(final Position position) {
            return List.of();
        }

        @Override
        public void canMoveBy(final Position position) {
        }
    }
}
