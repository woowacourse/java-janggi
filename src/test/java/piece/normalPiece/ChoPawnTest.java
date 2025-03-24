package piece.normalPiece;

import static org.assertj.core.api.Assertions.assertThat;
import static piece.Team.CHO;
import static piece.Team.HAN;
import static position.PositionFixtures.D1;
import static position.PositionFixtures.E1;
import static position.PositionFixtures.E6;
import static position.PositionFixtures.F1;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Piece;
import position.Board;
import route.Routes;

public class ChoPawnTest {
    @Test
    @DisplayName("장기 말의 종류에는 병이 있다.")
    void createPalaceTest() {
        Piece pawn = new ChoPawn(E6);
    }

    /*
    0 ＿ ＿ ＿
    1 * 병 *
    2 ＿ * ＿
    3 d e f
    */
    @Test
    @DisplayName("병의 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_1() {
        // given
        Piece choPawn = new ChoPawn(E1);
        Board board = new Board(CHO, Set.of(choPawn));

        // when
        Routes choPawnRoutes = choPawn.possibleRoutes(board);

        // then
        assertThat(choPawnRoutes.routes().size()).isEqualTo(3);
    }

    /*
    0 ＿ ＿ ＿
    1 마 병 마
    2 ＿ * ＿
    3 d e f
    */
    @Test
    @DisplayName("병의 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_2() {
        // given
        Piece choPawn = new ChoPawn(E1);
        Piece horse1 = new Horse(HAN, D1);
        Piece horse2 = new Horse(CHO, F1);
        Board board = new Board(CHO, Set.of(choPawn, horse1, horse2));

        // when
        Routes hanPawnRoutes = choPawn.possibleRoutes(board);

        // then
        assertThat(hanPawnRoutes.routes().size()).isEqualTo(2);
    }
}
