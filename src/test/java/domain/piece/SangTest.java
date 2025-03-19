package domain.piece;

import domain.PieceType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SangTest {

    @Test
    void 상은_상_타입이다() {
        // given
        Piece piece = new Sang(Team.CHO);
        // when & then
        Assertions.assertThat(piece.type()).isEqualTo(PieceType.SANG);
    }
}