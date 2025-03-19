package domain.piece;

import domain.PieceType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SaTest {

    @Test
    void 사는_사_타입이다() {
        // given
        Piece piece = new Sa(Team.CHO);
        // when & then
        Assertions.assertThat(piece.type()).isEqualTo(PieceType.SA);
    }
}