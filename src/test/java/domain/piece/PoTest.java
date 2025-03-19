package domain.piece;

import domain.PieceType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PoTest {

    @Test
    void 포는_포_타입이다() {
        // given
        Piece piece = new Po(Team.CHO);
        // when & then
        Assertions.assertThat(piece.type()).isEqualTo(PieceType.PO);
    }


}