package movepolicy.rule;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import pieces.Piece;
import pieces.PieceType;
import pieces.Side;

class EmptyPathMoveRuleTest {

    private static final Piece PIECE = new Piece(Side.HAN, PieceType.CHA);
    private static final List<Piece> EMPTY_PATH_PIECES = List.of();
    private static final Piece EMPTY_TARGET = null;

    private final MoveRule moveRule = EmptyPathMoveRule.withOtherSideTargetRule();

    @Test
    void 이동_경로가_비었으면_TRUE를_반환한다() {
        // given
        MoveTrace moveTrace = new MoveTrace(PIECE, EMPTY_PATH_PIECES, EMPTY_TARGET);
        // when & then
        assertThatCode(() -> moveRule.validate(moveTrace))
            .doesNotThrowAnyException();
    }

    @Test
    void 이동_경로에_기물이_있으면_예외를_던진다() {
        // given
        List<Piece> pathPieces = List.of(
            new Piece(Side.HAN, PieceType.GUNG)
        );
        MoveTrace moveTrace = new MoveTrace(PIECE, pathPieces, EMPTY_TARGET);
        // when & then
        assertThatThrownBy(() -> moveRule.validate(moveTrace))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 출발지_기물과_도착지_기물이_같은_진영이면_예외를_던진다() {
        Piece movingPiece = new Piece(Side.HAN, PieceType.CHA);
        Piece targetPiece = new Piece(Side.HAN, PieceType.SANG);
        MoveTrace moveTrace = new MoveTrace(movingPiece, EMPTY_PATH_PIECES, targetPiece);
        // when & then
        assertThatThrownBy(() -> moveRule.validate(moveTrace))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 중간에_기물이_없고_출발지_기물과_도착지_기물이_다른_진영이면_이동할_수_있다() {
        Piece movingPiece = new Piece(Side.HAN, PieceType.CHA);
        Piece targetPiece = new Piece(Side.CHO, PieceType.SANG);
        MoveTrace moveTrace = new MoveTrace(movingPiece, EMPTY_PATH_PIECES, targetPiece);
        // when & then
        assertThatCode(() -> moveRule.validate(moveTrace))
            .doesNotThrowAnyException();
    }
}
