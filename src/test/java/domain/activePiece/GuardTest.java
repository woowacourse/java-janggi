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

class GuardTest {

    @Test
    void 사_이동_범위는_직선이어야한다() {
        Piece guard = PalacePiece.guard(Team.HAN);
        assertThat(guard.canMove(new Position(5, 5), new Position(6, 5))).isTrue();
    }

    @Test
    void 사_이동_범위가_직선이_아니면_거짓() {
        Piece guard = PalacePiece.guard(Team.HAN);
        assertThat(guard.canMove(new Position(5, 5), new Position(8, 8))).isFalse();
    }

    @Test
    void 사는_인접_이동_시_중간_경로_위치가_없다() {
        ActivePiece guard = PalacePiece.guard(Team.HAN);

        Position src = new Position(new Row(1), new Column(3));
        Position dest = new Position(new Row(2), new Column(3));
        List<Position> routes = new ArrayList<>();

        assertThat(guard.searchRoute(src, dest)).isEqualTo(routes);
    }
}
