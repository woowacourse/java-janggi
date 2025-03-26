package piece.jumpingPiece;

import static janggi.piece.Team.CHO;
import static janggi.piece.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;
import static position.PositionFixtures.A5;
import static position.PositionFixtures.E2;
import static position.PositionFixtures.E5;
import static position.PositionFixtures.E6;
import static position.PositionFixtures.E7;
import static position.PositionFixtures.E8;
import static position.PositionFixtures.E9;
import static position.PositionFixtures.H0;
import static position.PositionFixtures.H1;
import static position.PositionFixtures.H2;
import static position.PositionFixtures.H3;
import static position.PositionFixtures.H4;
import static position.PositionFixtures.H5;
import static position.PositionFixtures.H6;
import static position.PositionFixtures.H7;
import static position.PositionFixtures.H8;
import static position.PositionFixtures.H9;
import static position.PositionFixtures.I5;

import janggi.piece.Piece;
import janggi.piece.jumpingPiece.Cannon;
import janggi.piece.normalPiece.Elephant;
import janggi.piece.normalPiece.Horse;
import janggi.piece.straightPiece.Chariot;
import janggi.position.Board;
import janggi.position.Position;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        Set<Position> positions = cannon.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(H5, H6, H7, H8, H9);
    }

    /*
    0  ＿ ＿ ＿ ＿ ＿ ＿ ＿ 포 ＿
    1  ＿ ＿ ＿ ＿ ＿ ＿ ＿ 마 ＿
    2  ＿ ＿ ＿ ＿ ＿ ＿ ＿ * ＿
    3  ＿ ＿ ＿ ＿ ＿ ＿ ＿ * ＿
    4  ＿ ＿ ＿ ＿ ＿ ＿ ＿ * ＿
    5  ＿ ＿ ＿ ＿ ＿ ＿ ＿ * ＿
    6  ＿ ＿ ＿ ＿ ＿ ＿ ＿ * ＿
    7  ＿ ＿ ＿ ＿ ＿ ＿ ＿ * ＿
    8  ＿ ＿ ＿ ＿ ＿ ＿ ＿ * ＿
    9  ＿ ＿ ＿ ＿ ＿ ＿ ＿ * ＿
       a  b c  d e f g  h i
    */
    @Test
    @DisplayName("포의 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_2() {
        // given
        Piece cannon = new Cannon(HAN, H0);
        Piece horse = new Horse(HAN, H1);
        Board board = new Board(HAN, Set.of(cannon, horse));

        // when
        Set<Position> positions = cannon.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(H2, H3, H4, H5, H6, H7, H8, H9);
    }

    /*
    0  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    1  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    2  ＿ ＿ ＿ ＿ 포 ＿ ＿ ＿ ＿
    3  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    4  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    5  차 ＿ ＿ ＿ 포 ＿ ＿ 마 *
    6  ＿ ＿ ＿ ＿ 상 ＿ ＿ ＿ ＿
    7  ＿ ＿ ＿ ＿ * ＿ ＿ ＿ ＿
    8  ＿ ＿ ＿ ＿ * ＿ ＿ ＿ ＿
    9  ＿ ＿ ＿ ＿ * ＿ ＿ ＿ ＿
       a  b c  d e f g  h i
    */
    @Test
    @DisplayName("포의 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_3() {
        // given
        Piece cannon1 = new Cannon(HAN, E5);
        Piece cannon2 = new Cannon(CHO, E2);
        Piece horse = new Horse(HAN, H5);
        Piece elephant = new Elephant(CHO, E6);
        Piece chariot = new Chariot(CHO, A5);
        Board board = new Board(HAN, Set.of(cannon1, cannon2, horse, elephant, chariot));

        // when
        Set<Position> positions = cannon1.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(E7, E8, E9, I5);
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
    void possibleRoutesTest_4() {
        // given
        Piece cannon1 = new Cannon(HAN, H0);
        Piece cannon2 = new Cannon(CHO, H4);
        Board board = new Board(HAN, Set.of(cannon1, cannon2));

        // when
        Set<Position> positions = cannon1.possibleRoutes(board);

        // then
        assertThat(positions).isEmpty();
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
    void possibleRoutesTest_5() {
        // given
        Piece cannon1 = new Cannon(HAN, H0);
        Piece horse = new Horse(HAN, H2);
        Piece cannon2 = new Cannon(CHO, H4);
        Board board = new Board(HAN, Set.of(cannon1, horse, cannon2));

        // when
        Set<Position> positions = cannon1.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(H3);
    }
}
