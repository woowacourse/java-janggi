package movepolicy.rule;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import pieces.Cha;
import pieces.Piece;
import pieces.Gung;
import pieces.Po;
import pieces.Sa;
import pieces.Side;

class PoMoveRuleTest {

    private static final Piece PO = new Po(Side.CHO);
    private static final Optional<Piece> TARGET_PIECE = Optional.of(new Cha(Side.HAN));
    private static final List<Piece> INTERVENING_PIECES = List.of(new Cha(Side.HAN));

    private final MoveRule moveRule = new PoMoveRule();

    @Test
    void 이동경로에_기물이_없으면_예외를_던진다() {
        // given
        List<Piece> emptyInterveningPieces = List.of();
        MoveTrace moveTrace = new MoveTrace(PO, emptyInterveningPieces, TARGET_PIECE);
        // when & then
        assertThatThrownBy(() -> moveRule.validate(moveTrace))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이동경로에_기물이_1개가_아닐_경우_예외를_던진다() {
        // given
        List<Piece> interveningPieces = List.of(
            new Gung(Side.HAN),
            new Sa(Side.HAN));
        MoveTrace moveTrace = new MoveTrace(PO, interveningPieces, TARGET_PIECE);
        MoveRule moveRule = new PoMoveRule();
        // when & then
        assertThatThrownBy(() -> moveRule.validate(moveTrace))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이동경로에_포가_있을_경우_예외를_던진다() {
        // given
        List<Piece> interveningPieces = List.of(new Po(Side.HAN));
        MoveTrace moveTrace = new MoveTrace(PO, interveningPieces, TARGET_PIECE);
        MoveRule moveRule = new PoMoveRule();
        // when & then
        assertThatThrownBy(() -> moveRule.validate(moveTrace))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이동경로에_포가_아닌_기물이_1개_있으면_이동할_수_있다() {
        // given
        List<Piece> interveningPieces = List.of(new Gung(Side.HAN));
        MoveTrace moveTrace = new MoveTrace(PO, interveningPieces, TARGET_PIECE);
        MoveRule moveRule = new PoMoveRule();
        // when & then
        assertThatCode(() -> moveRule.validate(moveTrace))
            .doesNotThrowAnyException();
    }

    @Test
    void 출발지_기물과_도착지_기물이_같은_진영이면_예외를_던진다() {
        // given
        Piece movingPiece = new Po(Side.HAN);
        Optional<Piece> targetPiece = Optional.of(new Gung(Side.HAN));
        MoveTrace moveTrace = new MoveTrace(movingPiece, INTERVENING_PIECES, targetPiece);
        MoveRule moveRule = new PoMoveRule();
        // when & then
        assertThatThrownBy(() -> moveRule.validate(moveTrace))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 출발지_기물과_도착지_기물이_모두_포인_경우_예외를_던진다() {
        // given
        Piece movingPiece = new Po(Side.HAN);
        Optional<Piece> targetPiece = Optional.of(new Po(Side.CHO));
        MoveTrace moveTrace = new MoveTrace(movingPiece, INTERVENING_PIECES, targetPiece);
        MoveRule moveRule = new PoMoveRule();
        // when & then
        assertThatThrownBy(
            () -> moveRule.validate(moveTrace))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 출발지_기물과_도착지_기물이_다른_진영이고_도착지의_기물이_포가_아닌_경우_이동할_수_있다() {
        // given
        MoveTrace moveTrace = new MoveTrace(PO, INTERVENING_PIECES, TARGET_PIECE);
        MoveRule moveRule = new PoMoveRule();
        // when & then
        assertThatCode(() -> moveRule.validate(moveTrace))
            .doesNotThrowAnyException();
    }
}
