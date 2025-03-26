package piece.straightPiece;

import static janggi.piece.Team.CHO;
import static janggi.piece.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;
import static position.PositionFixtures.A9;
import static position.PositionFixtures.B5;
import static position.PositionFixtures.B9;
import static position.PositionFixtures.C5;
import static position.PositionFixtures.C9;
import static position.PositionFixtures.D5;
import static position.PositionFixtures.D9;
import static position.PositionFixtures.E0;
import static position.PositionFixtures.E1;
import static position.PositionFixtures.E2;
import static position.PositionFixtures.E3;
import static position.PositionFixtures.E4;
import static position.PositionFixtures.E5;
import static position.PositionFixtures.E6;
import static position.PositionFixtures.E7;
import static position.PositionFixtures.E9;
import static position.PositionFixtures.F5;
import static position.PositionFixtures.F9;
import static position.PositionFixtures.G5;
import static position.PositionFixtures.G9;
import static position.PositionFixtures.H5;
import static position.PositionFixtures.H9;
import static position.PositionFixtures.I0;
import static position.PositionFixtures.I1;
import static position.PositionFixtures.I2;
import static position.PositionFixtures.I3;
import static position.PositionFixtures.I4;
import static position.PositionFixtures.I5;
import static position.PositionFixtures.I6;
import static position.PositionFixtures.I7;
import static position.PositionFixtures.I8;
import static position.PositionFixtures.I9;

import janggi.piece.Piece;
import janggi.piece.normalPiece.HanPawn;
import janggi.piece.normalPiece.Horse;
import janggi.piece.straightPiece.Chariot;
import janggi.position.Board;
import janggi.position.Position;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChariotTest {

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
    @DisplayName("차가 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_1() {
        // given
        Piece chariot = new Chariot(HAN, I9);
        Board board = new Board(HAN, Set.of(chariot));

        // when
        Set<Position> positions = chariot.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(A9, B9, C9, D9, E9, F9, G9, H9, H9, I0, I1, I2, I3, I4, I5, I6, I7, I8);
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
    @DisplayName("차가 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_2() {
        // given
        Piece chariot = new Chariot(HAN, E5);
        Piece hanPawn = new HanPawn(E7);
        Piece horse = new Horse(CHO, B5);
        Board board = new Board(HAN, Set.of(chariot, hanPawn, horse));

        // when
        Set<Position> positions = chariot.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(E0, E1, E2, E3, E4, D5, C5, B5, E6, F5, G5, H5, I5);
    }
}
