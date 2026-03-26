package domain.activePiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import domain.Position;
import domain.piece.Piece;
import domain.piece.Team;
import org.junit.jupiter.api.Test;

class GeneralTest {

    @Test
    void 정상_범위_입력() {
        Piece general = new General(Team.HAN);
        assertThat(general.canMove(new Position(5,5), new Position(5,6))).isTrue();
    }
    @Test
    void 정상_범위가_아니면_거짓() {
        Piece general = new General(Team.HAN);
        assertThat(general.canMove(new Position(5,5), new Position(6,6))).isFalse();
    }


}
