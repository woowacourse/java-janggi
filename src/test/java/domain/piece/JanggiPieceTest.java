package domain.piece;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class JanggiPieceTest {

    @Test
    void 기물을_잡으면_잡힌_기물의_상태가_바뀐다() {
        // given
        JanggiPiece piece = new 마(JanggiSide.CHO);

        // when
        piece.captureIfNotMySide(new 졸병(JanggiSide.HAN));

        // then
        Assertions.assertThat(piece.getStatus())
                .isEqualTo(JanggiPieceStatus.CAPTURED);
    }
}
