package domain.piece;

import domain.PieceType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MaTest {

    @Test
    void 마는_마_타입이다() {
        // given
        Piece piece = new Ma(Team.CHO);
        // when & then
        Assertions.assertThat(piece.type()).isEqualTo(PieceType.MA);
    }
}