package domain.piece;

import domain.PieceType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ByeongTest {

    @Test
    void 병은_병_타입이다() {
        // given
        Piece piece = new Byeong(Team.CHO);
        // when & then
        Assertions.assertThat(piece.type()).isEqualTo(PieceType.BYEONG);
    }
}