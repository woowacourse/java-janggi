package domain.activePiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import domain.Position;
import domain.piece.Piece;
import domain.piece.Team;
import org.junit.jupiter.api.Test;

class HorseTest {

    @Test
    void 정상_범위_입력() {
        Piece horse = new Horse(Team.HAN);
        assertThat(horse.canMove(new Position(5,5), new Position(6,7))).isTrue();
    }
    @Test
    void 정상_범위가_아니면_거짓() {
        Piece horse = new Horse(Team.HAN);
        assertThat(horse.canMove(new Position(5,5), new Position(8,8))).isFalse();
    }


}
