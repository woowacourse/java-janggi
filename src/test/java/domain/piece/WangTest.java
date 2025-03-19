package domain.piece;

import domain.PieceType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class WangTest {

    @Test
    void 왕은_왕_타입이다() {
        // given
        Piece piece = new Wang(Team.CHO);
        // when & then
        Assertions.assertThat(piece.type()).isEqualTo(PieceType.WANG);
    }
}