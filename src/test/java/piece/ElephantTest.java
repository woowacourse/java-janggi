package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static piece.Team.HAN;
import static position.PositionFixtures.B2;
import static position.PositionFixtures.B9;
import static position.PositionFixtures.D3;
import static position.PositionFixtures.E5;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import position.Board;
import route.Routes;

public class ElephantTest {
    @Test
    @DisplayName("장기 말의 종류에는 상이 있다.")
    void createHorseTest() {
        Piece elephant = new Elephant(HAN, B9);
    }

    /*
    0 ＿ * ＿ ＿ ＿ * ＿
    1 * ＿ ＿ ＿ ＿ ＿ *
    2 ＿ ＿ ＿ ＿ ＿ ＿ ＿
    3 ＿ ＿ ＿ 상 ＿ ＿ ＿
    4 ＿ ＿ ＿ ＿ ＿ ＿ ＿
    5 * ＿ ＿ ＿ ＿ ＿ *
    6 ＿ * ＿ ＿ ＿ * ＿
      a b  c d  e f  g
    */
    @Test
    @DisplayName("상이 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_1() {
        // given
        Piece elephant = new Elephant(HAN, D3);
        Board board = new Board(HAN, Set.of(elephant));

        // when
        Routes elephantRoutes = elephant.possibleRoutes(board);

        // then
        assertThat(elephantRoutes.routes().size()).isEqualTo(8);
    }

    /*
    0 ＿ * ＿ ＿ ＿ * ＿
    1 ＿ ＿ ＿ ＿ ＿ ＿ *
    2 ＿ 마 ＿ ＿ ＿ ＿ ＿
    3 ＿ ＿ ＿ 상 ＿ ＿ ＿
    4 ＿ ＿ ＿ ＿ ＿ ＿ ＿
    5 * ＿ ＿ ＿ 마 ＿ *
    6 ＿ * ＿ ＿ ＿ ＿ ＿
      a b  c d  e f  g
    */
    @Test
    @DisplayName("상이 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_2() {
        // given
        Piece elephant = new Elephant(HAN, D3);
        Piece horse1 = new Horse(HAN, E5);
        Piece horse2 = new Horse(HAN, B2);
        Board board = new Board(HAN, Set.of(elephant, horse1, horse2));

        // when
        Routes elephantRoutes = elephant.possibleRoutes(board);

        // then
        assertThat(elephantRoutes.routes().size()).isEqualTo(6);
    }
}
