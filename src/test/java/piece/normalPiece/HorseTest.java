package piece.normalPiece;

import static janggi.piece.Team.CHO;
import static janggi.piece.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;
import static position.PositionFixtures.A1;
import static position.PositionFixtures.A3;
import static position.PositionFixtures.B0;
import static position.PositionFixtures.B2;
import static position.PositionFixtures.B4;
import static position.PositionFixtures.C2;
import static position.PositionFixtures.D0;
import static position.PositionFixtures.D4;
import static position.PositionFixtures.E1;
import static position.PositionFixtures.E3;

import janggi.piece.Piece;
import janggi.piece.normalPiece.Horse;
import janggi.piece.palacePiece.King;
import janggi.position.Board;
import janggi.position.Position;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HorseTest {

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
        Piece horse = new Horse(HAN, C2);
        Board board = new Board(HAN, Set.of(horse));

        // when
        Set<Position> positions = horse.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(B4, A3, A1, B0, D0, E1, E3, D4);
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
        Piece horse = new Horse(HAN, C2);
        Piece king = new King(HAN, B2);
        Board board = new Board(HAN, Set.of(king, horse));

        // when
        Set<Position> positions = horse.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(B4, B0, D0, E1, E3, D4);
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
        Piece horse = new Horse(HAN, C2);
        Piece king = new King(HAN, B0);
        Board board = new Board(HAN, Set.of(king, horse));

        // when
        Set<Position> positions = horse.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(B4, A3, A1, D0, E1, E3, D4);
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
    void possibleRoutesTest_4() {
        // given
        Piece horse = new Horse(HAN, C2);
        Piece king = new King(CHO, B0);
        Board board = new Board(HAN, Set.of(king, horse));

        // when
        Set<Position> positions = horse.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(B4, A3, A1, B0, D0, E1, E3, D4);
    }
}
