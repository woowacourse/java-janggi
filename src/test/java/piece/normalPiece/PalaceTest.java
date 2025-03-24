package piece.normalPiece;

import static org.assertj.core.api.Assertions.assertThat;
import static piece.Team.HAN;
import static position.PositionFixtures.D1;
import static position.PositionFixtures.E0;
import static position.PositionFixtures.E1;
import static position.PositionFixtures.E2;
import static position.PositionFixtures.I0;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Piece;
import position.Board;
import route.Routes;

public class PalaceTest {

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

    /*
    0 * 궁 *
    1 ＿ * ＿
    2 ＿ ＿ ＿
    3 d e f
    */
    @Test
    @DisplayName("궁의 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_2() {
        // given
        Piece palace = new Palace(HAN, E0);
        Board board = new Board(HAN, Set.of(palace));

        // when
        Routes palaceRoutes = palace.possibleRoutes(board);

        // then
        assertThat(palaceRoutes.routes().size()).isEqualTo(3);
    }

    /*
    0 ＿ * 궁
    1 ＿ ＿ *
    2 ＿ ＿ ＿
    3 g h  i
    */
    @Test
    @DisplayName("궁의 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_3() {
        // given
        Piece palace = new Palace(HAN, I0);
        Board board = new Board(HAN, Set.of(palace));

        // when
        Routes palaceRoutes = palace.possibleRoutes(board);

        // then
        assertThat(palaceRoutes.routes().size()).isEqualTo(2);
    }

    /*
    0 ＿ * ＿
    1 사 궁 *
    2 ＿ 사 ＿
    3 d e f
    */
    @Test
    @DisplayName("궁의 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_4() {
        // given
        Piece palace = new Palace(HAN, E1);
        Piece soldier1 = new Soldier(HAN, D1);
        Piece soldier2 = new Soldier(HAN, E2);
        Board board = new Board(HAN, Set.of(palace, soldier1, soldier2));

        // when
        Routes palaceRoutes = palace.possibleRoutes(board);

        // then
        assertThat(palaceRoutes.routes().size()).isEqualTo(2);
    }

}
