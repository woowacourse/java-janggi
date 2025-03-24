package piece;

import static org.assertj.core.api.Assertions.assertThat;
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
        Piece soldier = new Soldier(D0);
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
        Piece soldier = new Soldier(E1);
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
        Piece soldier = new Soldier(E0);
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
        Piece soldier = new Soldier(I0);
        Board board = new Board(Set.of(soldier));

        // when
        Routes soldierRoutes = soldier.possibleRoutes(board);

        // then
        assertThat(soldierRoutes.routes().size()).isEqualTo(2);
    }

    /*
    0 ＿ * ＿
    1 사 사 *
    2 ＿ 사 ＿
    3 d e f
    */
    @Test
    @DisplayName("사가 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_4() {
        // given
        Piece soldier1 = new Soldier(E1);
        Piece soldier2 = new Soldier(D1);
        Piece soldier3 = new Soldier(E2);
        Board board = new Board(Set.of(soldier1, soldier2, soldier3));

        // when
        Routes soldierRoutes = soldier1.possibleRoutes(board);

        // then
        assertThat(soldierRoutes.routes().size()).isEqualTo(2);
    }
}
