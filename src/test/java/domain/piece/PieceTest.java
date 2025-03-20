package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class PieceTest {

    @Test
    void 기물을_잡으면_잡힌_기물의_상태가_바뀐다() {
        // given
        Piece piece = new 마(Side.CHO);

        // when
        piece.captureIfNotMySide(Side.HAN);

        // then
        assertThat(piece.getStatus())
                .isEqualTo(PieceStatus.CAPTURED);
    }

    @Test
    void 목적지에_같은_팀의_기물이_있는_경우_이동할_수_없다() {
        // given
        Piece piece = new 마(Side.CHO);

        // when
        boolean isSameSide = piece.isSameSide(Side.CHO);

        // then
        assertThat(isSameSide).isTrue();
    }
}
