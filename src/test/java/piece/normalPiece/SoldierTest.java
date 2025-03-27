package piece.normalPiece;

import static janggi.piece.Team.CHO;
import static janggi.piece.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;
import static position.PositionFixtures.D0;
import static position.PositionFixtures.D1;
import static position.PositionFixtures.E0;
import static position.PositionFixtures.E1;
import static position.PositionFixtures.E2;
import static position.PositionFixtures.F0;
import static position.PositionFixtures.F1;
import static position.PositionFixtures.H0;
import static position.PositionFixtures.I0;
import static position.PositionFixtures.I1;

import janggi.piece.Piece;
import janggi.piece.normalPiece.Horse;
import janggi.piece.normalPiece.Soldier;
import janggi.position.Board;
import janggi.position.Position;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SoldierTest {

    /*
    0 ＿ * ＿
    1 * 사 *
    2 ＿ * ＿
    3 d e f
    */
    @Test
    @DisplayName("사가 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_1() {
        // given
        Piece soldier = new Soldier(HAN, E1);
        Board board = new Board(HAN, Set.of(soldier));

        // when
        Set<Position> positions = soldier.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(E0, E2, D1, F1);
    }

    /*
    0 * 사 *
    1 ＿ * ＿
    2 ＿ ＿ ＿
    3 d e f
    */
    @Test
    @DisplayName("사가 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_2() {
        // given
        Piece soldier = new Soldier(HAN, E0);
        Board board = new Board(HAN, Set.of(soldier));

        // when
        Set<Position> positions = soldier.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(E1, D0, F0);
    }

    /*
    0 ＿ * 사
    1 ＿ ＿ *
    2 ＿ ＿ ＿
    3 g h  i
    */
    @Test
    @DisplayName("사가 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_3() {
        // given
        Piece soldier = new Soldier(HAN, I0);
        Board board = new Board(HAN, Set.of(soldier));

        // when
        Set<Position> positions = soldier.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(H0, I1);
    }

    /*
    0 ＿ * ＿
    1 마 사 *
    2 ＿ 마 ＿
    3 d e f
    */
    @Test
    @DisplayName("사가 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_4() {
        // given
        Piece soldier = new Soldier(HAN, E1);
        Piece horse1 = new Horse(HAN, D1);
        Piece horse2 = new Horse(HAN, E2);
        Board board = new Board(HAN, Set.of(soldier, horse1, horse2));

        // when
        Set<Position> positions = soldier.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(E0, F1);
    }

    /*
    0 ＿ 마 ＿
    1 마 사 마
    2 ＿ 마 ＿
    3 d e f
    */
    @Test
    @DisplayName("사가 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_5() {
        // given
        Piece soldier = new Soldier(HAN, E1);
        Piece horse1 = new Horse(HAN, D1);
        Piece horse2 = new Horse(HAN, E2);
        Piece horse3 = new Horse(HAN, E0);
        Piece horse4 = new Horse(HAN, F1);
        Board board = new Board(HAN, Set.of(soldier, horse1, horse2, horse3, horse4));

        // when
        Set<Position> positions = soldier.possibleRoutes(board);

        // then
        assertThat(positions).isEmpty();
    }
}
