package domain.activePiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.Team;
import domain.piece.ActivePiece;
import domain.piece.PalacePiece;
import domain.piece.Piece;
import domain.position.Column;
import domain.position.Position;
import domain.position.Row;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class GeneralTest {

    @Test
    void 궁은_직선_범위로_이동_한다() {
        Piece general = PalacePiece.general(Team.HAN);
        assertThat(general.canMove(new Position(5, 5), new Position(5, 6))).isTrue();
    }

    @Test
    void 궁_이동_범위가_직선이_아니면_거짓() {
        Piece general = PalacePiece.general(Team.HAN);
        assertThat(general.canMove(new Position(5, 5), new Position(6, 6))).isFalse();
    }

    @Test
    void 궁은_인접_이동_시_중간_경로_위치가_없다() {
        ActivePiece general = PalacePiece.general(Team.HAN);

        Position src = new Position(new Row(1), new Column(3));
        Position dest = new Position(new Row(2), new Column(3));
        List<Position> routes = new ArrayList<>();

        assertThat(general.searchRoute(src, dest)).isEqualTo(routes);
    }
}
