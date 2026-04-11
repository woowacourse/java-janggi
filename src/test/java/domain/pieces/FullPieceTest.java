package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class FullPieceTest {

    @Test
    void 같은_진영인_경우_TURE를_반환한다() {
        // given
        Piece gung = new Gung(Side.CHO);
        Piece po = new Po(Side.CHO);
        // when
        boolean isSameSide = gung.isSameSide(po);
        // then
        assertThat(isSameSide).isTrue();
    }

    @Test
    void 같은_진영이_아닌_경우_FALSE를_반환한다() {
        // given
        Piece gung = new Gung(Side.CHO);
        Piece po = new Po(Side.HAN);
        // when
        boolean isSameSide = gung.isSameSide(po);
        // then
        assertThat(isSameSide).isFalse();
    }
}
