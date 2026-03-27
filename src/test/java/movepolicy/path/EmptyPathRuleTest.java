package movepolicy.path;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import pieces.Gung;
import pieces.Piece;
import pieces.Side;

class EmptyPathRuleTest {

    @Test
    void 이동_경로가_비었으면_TRUE를_반환한다() {
        // given
        List<Piece> pathPieces = List.of();
        PathRule emptyPathRule = new EmptyPathRule();
        // when & then
        assertThatCode(() -> emptyPathRule.validatePathPieces(pathPieces))
            .doesNotThrowAnyException();
    }

    @Test
    void 이동_경로에_기물이_있으면_예외를_던진다() {
        // given
        List<Piece> pathPieces = List.of(new Gung(Side.HAN));
        PathRule emptyPathRule = new EmptyPathRule();
        // when & then
        assertThatThrownBy(() -> emptyPathRule.validatePathPieces(pathPieces))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
