package movepolicy.rule;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import pieces.Cha;
import pieces.Piece;
import pieces.Gung;
import pieces.Sang;
import pieces.Side;

class BasicMoveRuleTest {

    private static final Piece PIECE = new Cha(Side.HAN);
    private static final List<Piece> EMPTY_PATH_PIECES = List.of();
    private static final Optional<Piece> EMPTY_TARGET = Optional.empty();

    @Test
    void 이동_경로가_비었으면_TRUE를_반환한다() {
        // given
        MoveTrace moveTrace = new MoveTrace(PIECE, EMPTY_PATH_PIECES, EMPTY_TARGET);
        // when & then
        MoveRule moveRule = new BasicMoveRule();
        assertThatCode(() -> moveRule.validate(moveTrace))
            .doesNotThrowAnyException();
    }

    @Test
    void 이동_경로에_기물이_있으면_예외를_던진다() {
        // given
        List<Piece> pathPieces = List.of(new Gung(Side.HAN));
        MoveTrace moveTrace = new MoveTrace(PIECE, pathPieces, EMPTY_TARGET);
        // when & then
        MoveRule moveRule = new BasicMoveRule();
        assertThatThrownBy(() -> moveRule.validate(moveTrace))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 출발지_기물과_도착지_기물이_같은_진영이면_예외를_던진다() {
        Piece movingPiece = new Cha(Side.HAN);
        Optional<Piece> targetPiece = Optional.of(new Sang(Side.HAN));
        MoveTrace moveTrace = new MoveTrace(movingPiece, EMPTY_PATH_PIECES, targetPiece);
        MoveRule moveRule = new BasicMoveRule();
        // when & then
        assertThatThrownBy(() -> moveRule.validate(moveTrace))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 중간에_기물이_없고_출발지_기물과_도착지_기물이_다른_진영이면_이동할_수_있다() {
        Piece movingPiece = new Cha(Side.HAN);
        Optional<Piece> targetPiece = Optional.of(new Sang(Side.CHO));
        MoveTrace moveTrace = new MoveTrace(movingPiece, EMPTY_PATH_PIECES, targetPiece);
        // when & then
        MoveRule moveRule = new BasicMoveRule();
        assertThatCode(() -> moveRule.validate(moveTrace))
            .doesNotThrowAnyException();
    }
}
