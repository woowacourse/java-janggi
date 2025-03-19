package domain.piece;

import domain.PieceType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ChaTest {

    @Test
    void 차는_차_타입이다() {
        // given
        Piece piece = new Cha(Team.CHO);
        // when & then
        Assertions.assertThat(piece.type()).isEqualTo(PieceType.CHA);
    }
}