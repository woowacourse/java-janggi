package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static piece.Team.HAN;
import static position.PositionFixtures.D0;
import static position.PositionFixtures.D1;
import static position.PositionFixtures.E0;
import static position.PositionFixtures.E1;
import static position.PositionFixtures.E2;
import static position.PositionFixtures.I0;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import position.Board;
import route.Routes;

public class SoldierTest {
    @Test
    @DisplayName("장기 말의 종류에는 사가 있다.")
    void createPalaceTest() {
        Piece soldier = new Soldier(HAN, D0);
    }

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
        Board board = new Board(Set.of(soldier));

        // when
        Routes soldierRoutes = soldier.possibleRoutes(board);

        // then
        assertThat(soldierRoutes.routes().size()).isEqualTo(4);
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
        Board board = new Board(Set.of(soldier));

        // when
        Routes soldierRoutes = soldier.possibleRoutes(board);

        // then
        assertThat(soldierRoutes.routes().size()).isEqualTo(3);
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
        Board board = new Board(Set.of(soldier));

        // when
        Routes soldierRoutes = soldier.possibleRoutes(board);

        // then
        assertThat(soldierRoutes.routes().size()).isEqualTo(2);
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
        Board board = new Board(Set.of(soldier, horse1, horse2));

        // when
        Routes soldierRoutes = soldier.possibleRoutes(board);

        // then
        assertThat(soldierRoutes.routes().size()).isEqualTo(2);
    }
}
