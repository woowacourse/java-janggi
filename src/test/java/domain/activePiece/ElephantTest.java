package domain.activePiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import domain.Position;
import domain.piece.Piece;
import domain.piece.Team;
import org.junit.jupiter.api.Test;

class ElephantTest {

    @Test
    void 정상_범위_입력() {
        Piece elephant = new Elephant(Team.HAN);
        assertThat(elephant.canMove(new Position(5,5), new Position(7,8))).isTrue();
    }
    @Test
    void 정상_범위가_아니면_거짓() {
        Piece elephant = new Elephant(Team.HAN);
        assertThat(elephant.canMove(new Position(5,5), new Position(8,8))).isFalse();
    }

}
