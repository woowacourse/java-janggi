package piece.jumpingPiece;

import static org.assertj.core.api.Assertions.assertThat;
import static piece.Team.HAN;
import static position.PositionFixtures.E1;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.normalPiece.Palace;
import position.Board;
import route.Routes;

public class CannonTest {

    /*
    0 ＿ * ＿
    1 * 궁 *
    2 ＿ * ＿
    3 d e f
    */
    @Test
    @DisplayName("궁의 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_1() {
        // given
        Piece palace = new Palace(HAN, E1);
        Board board = new Board(HAN, Set.of(palace));

        // when
        Routes palaceRoutes = palace.possibleRoutes(board);

        // then
        assertThat(palaceRoutes.routes().size()).isEqualTo(4);
    }
}
