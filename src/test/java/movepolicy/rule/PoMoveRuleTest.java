package movepolicy.rule;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import pieces.Cha;
import pieces.FullPiece;
import pieces.Gung;
import pieces.Piece;
import pieces.Po;
import pieces.Sa;
import pieces.Side;

class PoMoveRuleTest {

    private static final FullPiece PO = new Po(Side.CHO);
    private static final Piece TARGET_PIECE = new Cha(Side.HAN);
    private static final List<Piece> INTERVENING_PIECES = List.of(new Cha(Side.HAN));

    private final MoveRule moveRule = new PoMoveRule();

    @Test
    void 이동경로에_기물이_없으면_예외를_던진다() {
        // given
        List<Piece> emptyInterveningPieces = List.of();
        MovePath movePath = new MovePath(PO, emptyInterveningPieces, TARGET_PIECE);
        // when & then
        assertThatThrownBy(() -> moveRule.validatePathPieces(movePath))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이동경로에_기물이_1개가_아닐_경우_예외를_던진다() {
        // given
        List<Piece> interveningPieces = List.of(
            new Gung(Side.HAN),
            new Sa(Side.HAN));
        MovePath movePath = new MovePath(PO, interveningPieces, TARGET_PIECE);
        MoveRule moveRule = new PoMoveRule();
        // when & then
        assertThatThrownBy(() -> moveRule.validatePathPieces(movePath))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이동경로에_포가_있을_경우_예외를_던진다() {
        // given
        List<Piece> interveningPieces = List.of(new Po(Side.HAN));
        MovePath movePath = new MovePath(PO, interveningPieces, TARGET_PIECE);
        MoveRule moveRule = new PoMoveRule();
        // when & then
        assertThatThrownBy(() -> moveRule.validatePathPieces(movePath))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이동경로에_포가_아닌_기물이_1개_있으면_이동할_수_있다() {
        // given
        List<Piece> interveningPieces = List.of(new Gung(Side.HAN));
        MovePath movePath = new MovePath(PO, interveningPieces, TARGET_PIECE);
        MoveRule moveRule = new PoMoveRule();
        // when & then
        assertThatCode(() -> moveRule.validatePathPieces(movePath))
            .doesNotThrowAnyException();
    }

    @Test
    void 출발지_기물과_도착지_기물이_같은_진영이면_예외를_던진다() {
        // given
        FullPiece movingPiece = new Po(Side.HAN);
        FullPiece targetPiece = new Gung(Side.HAN);
        MovePath movePath = new MovePath(movingPiece, INTERVENING_PIECES, targetPiece);
        MoveRule moveRule = new PoMoveRule();
        // when & then
        assertThatThrownBy(() -> moveRule.validatePathPieces(movePath))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 출발지_기물과_도착지_기물이_모두_포인_경우_예외를_던진다() {
        // given
        FullPiece movingPiece = new Po(Side.HAN);
        FullPiece targetPiece = new Po(Side.CHO);
        MovePath movePath = new MovePath(movingPiece, INTERVENING_PIECES, targetPiece);
        MoveRule moveRule = new PoMoveRule();
        // when & then
        assertThatThrownBy(
            () -> moveRule.validatePathPieces(movePath))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 출발지_기물과_도착지_기물이_다른_진영이고_도착지의_기물이_포가_아닌_경우_이동할_수_있다() {
        // given
        MovePath movePath = new MovePath(PO, INTERVENING_PIECES, TARGET_PIECE);
        MoveRule moveRule = new PoMoveRule();
        // when & then
        assertThatCode(() -> moveRule.validatePathPieces(movePath))
            .doesNotThrowAnyException();
    }
}
