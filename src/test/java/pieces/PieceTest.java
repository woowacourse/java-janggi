package pieces;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PieceTest {

    @Test
    void 포인지_여부를_반환한다() {
        // given
        Piece piece = new Piece(Side.CHO, PieceType.PO);
        // when
        boolean isPo = piece.isPo();
        // then
        assertThat(isPo).isTrue();
    }

    @Test
    void 같은_진영인_경우_TURE를_반환한다() {
        // given
        Piece gung = new Piece(Side.CHO, PieceType.GUNG);
        Piece po = new Piece(Side.CHO, PieceType.PO);
        // when
        boolean isSameSide = gung.isSameSide(po);
        // then
        assertThat(isSameSide).isTrue();
    }

    @Test
    void 같은_진영이_아닌_경우_FALSE를_반환한다() {
        // given
        Piece gung = new Piece(Side.CHO, PieceType.GUNG);
        Piece po = new Piece(Side.HAN, PieceType.PO);
        // when
        boolean isSameSide = gung.isSameSide(po);
        // then
        assertThat(isSameSide).isFalse();
    }
}