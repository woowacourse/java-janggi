package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static position.PositionFixtures.B0;
import static position.PositionFixtures.B2;
import static position.PositionFixtures.B9;
import static position.PositionFixtures.C2;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import position.Board;
import route.Routes;

public class HorseTest {
    @Test
    @DisplayName("장기 말의 종류에는 마가 있다.")
    void createHorseTest() {
        Piece horse = new Horse(B9);
    }

    /*
    0 ＿ * ＿ * ＿
    1 * ＿ ＿ ＿ *
    2 ＿ ＿ 마 ＿ ＿
    3 * ＿ ＿ ＿ *
    4 ＿ * ＿ * ＿
      a b  c d  e
    */
    @Test
    @DisplayName("마가 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_1() {
        // given
        Piece horse = new Horse(C2);
        Board board = new Board(Set.of(horse));

        // when
        Routes horseRoutes = horse.possibleRoutes(board);

        // then
        assertThat(horseRoutes.routes().size()).isEqualTo(8);
    }

    /*
    0 ＿ * ＿ * ＿
    1 ＿ ＿ ＿ ＿ *
    2 ＿ 궁 마 ＿ ＿
    3 ＿ ＿ ＿ ＿ *
    4 ＿ * ＿ * ＿
      a b  c d  e
    */
    @Test
    @DisplayName("마가 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_2() {
        // given
        Piece horse = new Horse(C2);
        Piece palace = new Palace(B2);
        Board board = new Board(Set.of(palace, horse));

        // when
        Routes horseRoutes = horse.possibleRoutes(board);

        // then
        assertThat(horseRoutes.routes().size()).isEqualTo(6);
    }

    /*
    0 ＿ 궁 ＿ * ＿
    1 * ＿ ＿ ＿ *
    2 ＿ ＿ 마 ＿ ＿
    3 * ＿ ＿ ＿ *
    4 ＿ * ＿ * ＿
      a b  c d  e
    */
    @Test
    @DisplayName("마가 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_3() {
        // given
        Piece horse = new Horse(C2);
        Piece palace = new Palace(B0);
        Board board = new Board(Set.of(palace, horse));

        // when
        Routes horseRoutes = horse.possibleRoutes(board);

        // then
        assertThat(horseRoutes.routes().size()).isEqualTo(7);
    }
}
