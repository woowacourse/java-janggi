package movepolicy.rule;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import pieces.Piece;
import pieces.PieceType;
import pieces.Side;

class PoMoveRuleTest {

    private static final Piece PO = new Piece(Side.CHO, PieceType.PO);
    private static final Piece TARGET_PIECE = PieceType.CHA.create(Side.HAN);
    private static final List<Piece> INTERVENING_PIECES = List.of(PieceType.CHA.create(Side.HAN));

    private final MoveRule moveRule = PoMoveRule.withOtherSideTargetRule();

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
            new Piece(Side.HAN, PieceType.GUNG),
            new Piece(Side.HAN, PieceType.SA));
        MoveTrace moveTrace = new MoveTrace(PO, interveningPieces, TARGET_PIECE);
        // when & then
        assertThatThrownBy(() -> moveRule.validate(moveTrace))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이동경로에_포가_있을_경우_예외를_던진다() {
        // given
        List<Piece> interveningPieces = List.of(
            new Piece(Side.HAN, PieceType.PO)
        );
        MoveTrace moveTrace = new MoveTrace(PO, interveningPieces, TARGET_PIECE);
        // when & then
        assertThatThrownBy(() -> moveRule.validate(moveTrace))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이동경로에_포가_아닌_기물이_1개_있으면_이동할_수_있다() {
        // given
        List<Piece> interveningPieces = List.of(
            new Piece(Side.HAN, PieceType.GUNG)
        );
        MoveTrace moveTrace = new MoveTrace(PO, interveningPieces, TARGET_PIECE);
        // when & then
        assertThatCode(() -> moveRule.validate(moveTrace))
            .doesNotThrowAnyException();
    }

    @Test
    void 출발지_기물과_도착지_기물이_같은_진영이면_예외를_던진다() {
        // given
        Piece movingPiece = new Piece(Side.HAN, PieceType.PO);
        Piece targetPiece = PieceType.GUNG.create(Side.HAN);
        MoveTrace moveTrace = new MoveTrace(movingPiece, INTERVENING_PIECES, targetPiece);
        // when & then
        assertThatThrownBy(() -> moveRule.validate(moveTrace))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 출발지_기물과_도착지_기물이_모두_포인_경우_예외를_던진다() {
        // given
        Piece movingPiece = new Piece(Side.HAN, PieceType.PO);
        Piece targetPiece = PieceType.PO.create(Side.CHO);
        MoveTrace moveTrace = new MoveTrace(movingPiece, INTERVENING_PIECES, targetPiece);
        // when & then
        assertThatThrownBy(
            () -> moveRule.validate(moveTrace))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 출발지_기물과_도착지_기물이_다른_진영이고_도착지의_기물이_포가_아닌_경우_이동할_수_있다() {
        // given
        MoveTrace moveTrace = new MoveTrace(PO, INTERVENING_PIECES, TARGET_PIECE);
        // when & then
        assertThatCode(() -> moveRule.validate(moveTrace))
            .doesNotThrowAnyException();
    }
}
