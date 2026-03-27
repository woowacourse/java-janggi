package movepolicy.path;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import pieces.Gung;
import pieces.Piece;
import pieces.Po;
import pieces.Side;

class PoPathRuleTest {

    @Test
    void 이동경로에_기물이_없으면_예외를_던진다() {
        // given
        List<Piece> pathPieces = List.of();
        PathRule poPathRule = new PoPathRule();
        // when & then
        assertThatThrownBy(() -> poPathRule.validatePathPieces(pathPieces))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이동경로에_기물이_1개가_아닐_경우_예외를_던진다() {
        // given
        List<Piece> pathPieces = List.of(new Gung(Side.HAN), new Gung(Side.CHO));
        PathRule poPathRule = new PoPathRule();
        // when & then
        assertThatThrownBy(() -> poPathRule.validatePathPieces(pathPieces))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이동경로에_포가_있을_경우_예외를_던진다() {
        // given
        List<Piece> pathPieces = List.of(new Po(Side.HAN));
        PathRule poPathRule = new PoPathRule();
        // when & then
        assertThatThrownBy(() -> poPathRule.validatePathPieces(pathPieces))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이동경로에_포가_아닌_기물이_1개_있으면_이동할_수_있다() {
        // given
        List<Piece> pathPieces = List.of(new Gung(Side.HAN));
        PathRule poPathRule = new PoPathRule();
        // when & then
        assertThatCode(() -> poPathRule.validatePathPieces(pathPieces))
            .doesNotThrowAnyException();
    }
}
