package piece.jumpingPiece;

import static org.assertj.core.api.Assertions.assertThat;
import static piece.Team.CHO;
import static piece.Team.HAN;
import static position.PositionFixtures.H0;
import static position.PositionFixtures.H2;
import static position.PositionFixtures.H4;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.normalPiece.Horse;
import position.Board;
import route.Routes;

public class CannonTest {

    /*
    0  ＿ ＿ ＿ ＿ ＿ ＿ ＿ 포 ＿
    1  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    2  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    3  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    4  ＿ ＿ ＿ ＿ ＿ ＿ ＿ 마 ＿
    5  ＿ ＿ ＿ ＿ ＿ ＿ ＿ * ＿
    6  ＿ ＿ ＿ ＿ ＿ ＿ ＿ * ＿
    7  ＿ ＿ ＿ ＿ ＿ ＿ ＿ * ＿
    8  ＿ ＿ ＿ ＿ ＿ ＿ ＿ * ＿
    9  ＿ ＿ ＿ ＿ ＿ ＿ ＿ * ＿
       a  b c  d e f g  h i
    */
    @Test
    @DisplayName("포의 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_1() {
        // given
        Piece cannon = new Cannon(HAN, H0);
        Piece horse = new Horse(HAN, H4);
        Board board = new Board(HAN, Set.of(cannon, horse));

        // when
        Routes cannonRoutes = cannon.possibleRoutes(board);

        // then
        assertThat(cannonRoutes.routes().size()).isEqualTo(5);
    }

    /*
    0  ＿ ＿ ＿ ＿ ＿ ＿ ＿ 포 ＿
    1  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    2  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    3  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    4  ＿ ＿ ＿ ＿ ＿ ＿ ＿ 포 ＿
    5  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    6  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    7  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    8  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    9  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
       a  b c  d e f g  h i
    */
    @Test
    @DisplayName("포는 포를 넘을 수 없다.")
    void possibleRoutesTest_2() {
        // given
        Piece cannon1 = new Cannon(HAN, H0);
        Piece cannon2 = new Cannon(CHO, H4);
        Board board = new Board(HAN, Set.of(cannon1, cannon2));

        // when
        Routes cannonRoutes = cannon1.possibleRoutes(board);

        // then
        assertThat(cannonRoutes.routes()).isEmpty();
    }

    /*
    0  ＿ ＿ ＿ ＿ ＿ ＿ ＿ 포 ＿
    1  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    2  ＿ ＿ ＿ ＿ ＿ ＿ ＿ 마 ＿
    3  ＿ ＿ ＿ ＿ ＿ ＿ ＿ * ＿
    4  ＿ ＿ ＿ ＿ ＿ ＿ ＿ 포 ＿
    5  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    6  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    7  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    8  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    9  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
       a  b c  d e f g  h i
    */
    @Test
    @DisplayName("포는 포를 잡을 수 없다.")
    void possibleRoutesTest_3() {
        // given
        Piece cannon1 = new Cannon(HAN, H0);
        Piece horse = new Horse(HAN, H2);
        Piece cannon2 = new Cannon(CHO, H4);
        Board board = new Board(HAN, Set.of(cannon1, horse, cannon2));

        // when
        Routes cannonRoutes = cannon1.possibleRoutes(board);

        // then
        assertThat(cannonRoutes.routes().size()).isEqualTo(1);
    }
}
