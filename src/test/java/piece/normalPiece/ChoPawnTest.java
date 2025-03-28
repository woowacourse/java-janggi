package piece.normalPiece;

import static janggi.piece.Team.CHO;
import static org.assertj.core.api.Assertions.assertThat;
import static position.PositionFixtures.D1;
import static position.PositionFixtures.E1;
import static position.PositionFixtures.E2;
import static position.PositionFixtures.F1;

import janggi.piece.Piece;
import janggi.piece.pawnPiece.ChoPawn;
import janggi.piece.normalPiece.Horse;
import janggi.position.Board;
import janggi.position.Position;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChoPawnTest {

    /*
    0 ＿ ＿ ＿
    1 * 병 *
    2 ＿ * ＿
    3 d e f
    */
    @Test
    @DisplayName("병의 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_1() {
        // given
        Piece choPawn = new ChoPawn(E1);
        Board board = new Board(CHO, Set.of(choPawn));

        // when
        Set<Position> positions = choPawn.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(E2, D1, F1);
    }

    /*
    0 ＿ ＿ ＿
    1 마 병 *
    2 ＿ * ＿
    3 d e f
    */
    @Test
    @DisplayName("병의 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_2() {
        // given
        Piece choPawn = new ChoPawn(E1);
        Piece horse = new Horse(CHO, D1);
        Board board = new Board(CHO, Set.of(choPawn, horse));

        // when
        Set<Position> positions = choPawn.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(E2, F1);
    }
}
