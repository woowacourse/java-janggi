package piece.palacePiece;

import static janggi.piece.Team.CHO;
import static org.assertj.core.api.Assertions.assertThat;
import static position.PositionFixtures.D0;
import static position.PositionFixtures.D1;
import static position.PositionFixtures.D2;
import static position.PositionFixtures.E0;
import static position.PositionFixtures.E1;
import static position.PositionFixtures.E2;
import static position.PositionFixtures.F0;
import static position.PositionFixtures.F1;
import static position.PositionFixtures.F2;

import janggi.piece.Piece;
import janggi.piece.palacePiece.King;
import janggi.piece.palacePiece.Soldier;
import janggi.position.Board;
import janggi.position.Position;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class KingTest {

    /*
    0 * * *
    1 * 궁 *
    2 * * *
    3 d e f
    */
    @Test
    @DisplayName("궁의 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_1() {
        // given
        Piece king = new King(CHO, E1);
        Board board = new Board(CHO, Set.of(king));

        // when
        Set<Position> positions = king.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(D0, D1, D2, E0, E2, F0, F1, F2);
    }

    /*
    0 * 궁 *
    1 ＿ * ＿
    2 ＿ ＿ ＿
    3 d e f
    */
    @Test
    @DisplayName("궁의 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_2() {
        // given
        Piece king = new King(CHO, E0);
        Board board = new Board(CHO, Set.of(king));

        // when
        Set<Position> positions = king.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(D0, E1, F0);
    }

    /*
    0 * * *
    1 사 궁 *
    2 * 사 *
    3 d e f
    */
    @Test
    @DisplayName("궁의 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_4() {
        // given
        Piece king = new King(CHO, E1);
        Piece soldier1 = new Soldier(CHO, D1);
        Piece soldier2 = new Soldier(CHO, E2);
        Board board = new Board(CHO, Set.of(king, soldier1, soldier2));

        // when
        Set<Position> positions = king.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(D0, D2, E0, F0, F1, F2);
    }

    /*
    0 ＿ ＿ ＿
    1 ＿ * ＿
    2 * 궁 *
    3 d e f
    */
    @Test
    @DisplayName("궁의 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_5() {
        // given
        Piece king = new King(CHO, E2);
        Board board = new Board(CHO, Set.of(king));

        // when
        Set<Position> positions = king.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(D2, E1, F2);
    }
}
