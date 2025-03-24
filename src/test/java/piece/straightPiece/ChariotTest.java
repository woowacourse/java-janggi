package piece.straightPiece;

import static org.assertj.core.api.Assertions.assertThat;
import static piece.Team.CHO;
import static piece.Team.HAN;
import static position.PositionFixtures.B5;
import static position.PositionFixtures.E1;
import static position.PositionFixtures.E5;
import static position.PositionFixtures.E7;
import static position.PositionFixtures.I9;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.normalPiece.HanPawn;
import piece.normalPiece.Horse;
import position.Board;
import route.Routes;

public class ChariotTest {
    @Test
    @DisplayName("장기 말의 종류에는 차이 있다.")
    void createPalaceTest() {
        Piece chariot = new Chariot(HAN, E1);
    }

    /*
    0  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ *
    1  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ *
    2  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ *
    3  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ *
    4  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ *
    5  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ *
    6  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ *
    7  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ *
    8  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ *
    9  *  * *  * *  * * * 차
       a  b c  d e f g h i
    */
    @Test
    @DisplayName("궁의 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_1() {
        // given
        Piece chariot = new Chariot(HAN, I9);
        Board board = new Board(HAN, Set.of(chariot));

        // when
        Routes palaceRoutes = chariot.possibleRoutes(board);

        // then
        assertThat(palaceRoutes.routes().size()).isEqualTo(17);
    }

    /*
    0  ＿ ＿ ＿ ＿ * ＿ ＿ ＿ ＿
    1  ＿ ＿ ＿ ＿ * ＿ ＿ ＿ ＿
    2  ＿ ＿ ＿ ＿ * ＿ ＿ ＿ ＿
    3  ＿ ＿ ＿ ＿ * ＿ ＿ ＿ ＿
    4  ＿ ＿ ＿ ＿ * ＿ ＿ ＿ ＿
    5  ＿ 마 *  * 차 * *  * *
    6  ＿ ＿ ＿ ＿ * ＿ ＿ ＿ ＿
    7  ＿ ＿ ＿ ＿ 병 ＿ ＿ ＿ ＿
    8  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    9  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
       a  b c  d e f g h i
    */
    @Test
    @DisplayName("궁의 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_2() {
        // given
        Piece chariot = new Chariot(HAN, E5);
        Piece hanPawn = new HanPawn(E7);
        Piece horse = new Horse(CHO, B5);
        Board board = new Board(HAN, Set.of(chariot, hanPawn, horse));

        // when
        Routes palaceRoutes = chariot.possibleRoutes(board);

        // then
        assertThat(palaceRoutes.routes().size()).isEqualTo(13);
    }
}
