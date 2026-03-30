package movepolicy.rule;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import pieces.Cha;
import pieces.EmptyPiece;
import pieces.FullPiece;
import pieces.Gung;
import pieces.Piece;
import pieces.Sang;
import pieces.Side;

class BasicMoveRuleTest {

    @Test
    void 이동_경로가_비었으면_TRUE를_반환한다() {
        // given
        FullPiece movingPiece = new Cha(Side.HAN);
        List<Piece> emptyInterveningPieces = List.of();
        Piece targetPiece = EmptyPiece.getInstance();
        MovePath movePath = new MovePath(movingPiece, emptyInterveningPieces, targetPiece);
        // when & then
        MoveRule moveRule = new BasicMoveRule();
        assertThatCode(() -> moveRule.validatePathPieces(movePath))
            .doesNotThrowAnyException();
    }

    @Test
    void 이동_경로에_기물이_있으면_예외를_던진다() {
        // given
        FullPiece movingPiece = new Cha(Side.HAN);
        List<Piece> interveningPieces = List.of(new Gung(Side.HAN));
        Piece targetPiece = EmptyPiece.getInstance();
        MovePath movePath = new MovePath(movingPiece, interveningPieces, targetPiece);
        // when & then
        MoveRule moveRule = new BasicMoveRule();
        assertThatThrownBy(() -> moveRule.validatePathPieces(movePath))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 출발지_기물과_도착지_기물이_같은_진영이면_예외를_던진다() {
        FullPiece movingPiece = new Cha(Side.HAN);
        List<Piece> emptyInterveningPieces = List.of();
        Piece targetPiece = new Sang(Side.HAN);
        MovePath movePath = new MovePath(movingPiece, emptyInterveningPieces, targetPiece);
        // when & then
        MoveRule moveRule = new BasicMoveRule();
        assertThatThrownBy(() -> moveRule.validatePathPieces(movePath))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 중간에_기물이_없고_출발지_기물과_도착지_기물이_다른_진영이면_이동할_수_있다() {
        FullPiece movingPiece = new Cha(Side.HAN);
        List<Piece> emptyInterveningPieces = List.of();
        Piece targetPiece = new Sang(Side.CHO);
        MovePath movePath = new MovePath(movingPiece, emptyInterveningPieces, targetPiece);
        // when & then
        MoveRule moveRule = new BasicMoveRule();
        assertThatCode(() -> moveRule.validatePathPieces(movePath))
            .doesNotThrowAnyException();
    }
}
