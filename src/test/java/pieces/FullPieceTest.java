package pieces;

import static org.assertj.core.api.Assertions.assertThat;

import domain.pieces.Gung;
import domain.pieces.Piece;
import domain.pieces.Po;
import domain.pieces.Side;
import org.junit.jupiter.api.Test;

class FullPieceTest {

    @Test
    void 포인지_여부를_반환한다() {
        // given
        Piece po = new Po(Side.CHO);
        // when
        boolean isPo = po.isPo();
        // then
        assertThat(isPo).isTrue();
    }

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
