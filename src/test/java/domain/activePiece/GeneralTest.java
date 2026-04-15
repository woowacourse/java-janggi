package domain.activePiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.Team;
import domain.piece.PalacePiece;
import domain.piece.Piece;
import domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

class GeneralTest {

    @Test
    void 궁_안에서_직선_이동_가능() {
        Piece general = PalacePiece.general(Team.HAN);
        assertThat(general.canMove(new Position(9, 5), new Position(9, 6))).isTrue();
    }

    @Test
    void 궁_안에서_위아래_직선_이동_가능() {
        Piece general = PalacePiece.general(Team.HAN);
        assertThat(general.canMove(new Position(9, 5), new Position(8, 5))).isTrue();
    }

    @Test
    void 궁_코너에서_대각선_이동_가능() {
        Piece general = PalacePiece.general(Team.HAN);
        assertThat(general.canMove(new Position(8, 4), new Position(9, 5))).isTrue();
    }

    @Test
    void 궁_변에서_대각선_이동_불가() {
        Piece general = PalacePiece.general(Team.HAN);
        assertThat(general.canMove(new Position(8, 5), new Position(9, 6))).isFalse();
    }

    @Test
    void 궁_밖으로_이동_불가() {
        Piece general = PalacePiece.general(Team.HAN);
        assertThat(general.canMove(new Position(9, 5), new Position(7, 5))).isFalse();
    }

    @Test
    void 두_칸_직선_이동_불가() {
        Piece general = PalacePiece.general(Team.HAN);
        assertThat(general.canMove(new Position(8, 5), new Position(10, 5))).isFalse();
    }

    @Test
    void 이동_시_중간_경로_위치가_없다() {
        Piece general = PalacePiece.general(Team.HAN);
        assertThat(general.searchRoute(new Position(9, 5), new Position(9, 6))).isEqualTo(List.of());
    }
}
