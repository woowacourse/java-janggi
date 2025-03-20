package domain.piece;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PieceTest {

    @Test
    void 기물을_잡으면_잡힌_기물의_상태가_바뀐다() {
        // given
        Piece piece = new 마(Side.CHO);

        // when
        piece.captureIfNotMySide(Side.HAN);

        // then
        Assertions.assertThat(piece.getStatus())
                .isEqualTo(PieceStatus.CAPTURED);
    }
}
