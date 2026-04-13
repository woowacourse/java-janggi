package domain.movepolicy.path;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.movepolicy.exception.InvalidPathRuleException;
import domain.movepolicy.exception.MovePolicyErrorMessage;
import java.util.List;
import org.junit.jupiter.api.Test;
import domain.pieces.Gung;
import domain.pieces.Piece;
import domain.pieces.Po;
import domain.pieces.Side;

class PoPathRuleTest {

    @Test
    void 이동경로에_기물이_없으면_예외를_던진다() {
        // given
        List<Piece> pathPieces = List.of();
        PathRule poPathRule = new PoPathRule();
        // when & then
        assertThatThrownBy(() -> poPathRule.validatePathPieces(pathPieces))
            .isInstanceOf(InvalidPathRuleException.class)
            .hasMessage(MovePolicyErrorMessage.PATH_MUST_CONTAIN_PIECE.message());
    }

    @Test
    void 이동경로에_기물이_1개가_아닐_경우_예외를_던진다() {
        // given
        List<Piece> pathPieces = List.of(new Gung(Side.HAN), new Gung(Side.CHO));
        PathRule poPathRule = new PoPathRule();
        // when & then
        assertThatThrownBy(() -> poPathRule.validatePathPieces(pathPieces))
            .isInstanceOf(InvalidPathRuleException.class)
            .hasMessage(MovePolicyErrorMessage.PATH_MUST_CONTAIN_ONE_PIECE.message());
    }

    @Test
    void 이동경로에_포가_있을_경우_예외를_던진다() {
        // given
        List<Piece> pathPieces = List.of(new Po(Side.HAN));
        PathRule poPathRule = new PoPathRule();
        // when & then
        assertThatThrownBy(() -> poPathRule.validatePathPieces(pathPieces))
            .isInstanceOf(InvalidPathRuleException.class)
            .hasMessage(MovePolicyErrorMessage.PO_CANNOT_JUMP_OVER_PO.message());
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
