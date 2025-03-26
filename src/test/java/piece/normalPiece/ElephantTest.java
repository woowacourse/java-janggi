package piece.normalPiece;

import static janggi.piece.Team.CHO;
import static janggi.piece.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;
import static position.PositionFixtures.A1;
import static position.PositionFixtures.A5;
import static position.PositionFixtures.B0;
import static position.PositionFixtures.B2;
import static position.PositionFixtures.B6;
import static position.PositionFixtures.D3;
import static position.PositionFixtures.E5;
import static position.PositionFixtures.F0;
import static position.PositionFixtures.F6;
import static position.PositionFixtures.G1;
import static position.PositionFixtures.G5;

import janggi.piece.Piece;
import janggi.piece.normalPiece.Elephant;
import janggi.piece.normalPiece.Horse;
import janggi.position.Board;
import janggi.position.Position;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ElephantTest {

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
        Set<Position> positions = elephant.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(B6, A5, A1, B0, F0, G1, G5, F6);
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
        Piece horse1 = new Horse(CHO, E5);
        Piece horse2 = new Horse(HAN, B2);
        Board board = new Board(HAN, Set.of(elephant, horse1, horse2));

        // when
        Set<Position> positions = elephant.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(B6, A5, B0, F0, G1, G5);
    }
}
