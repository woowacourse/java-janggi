package piece.normalPiece;

import static janggi.piece.Team.CHO;
import static janggi.piece.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;
import static position.PositionFixtures.D0;
import static position.PositionFixtures.D1;
import static position.PositionFixtures.E0;
import static position.PositionFixtures.E1;
import static position.PositionFixtures.F0;
import static position.PositionFixtures.F1;

import janggi.piece.Piece;
import janggi.piece.pawnPiece.HanPawn;
import janggi.piece.normalPiece.Horse;
import janggi.position.Board;
import janggi.position.Position;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HanPawnTest {

    /*
    0 * * *
    1 * 병 *
    2 ＿ ＿ ＿
    3 d e f
    */
    @Test
    @DisplayName("병의 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_1() {
        // given
        Piece hanPawn = new HanPawn(E1);
        Board board = new Board(HAN, Set.of(hanPawn));

        // when
        Set<Position> positions = hanPawn.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(E0, D1, F1, D0, F0);
    }

    /*
    0 * * *
    1 마 병 마
    2 ＿ ＿ ＿
    3 d e f
    */
    @Test
    @DisplayName("병의 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_2() {
        // given
        Piece hanPawn = new HanPawn(E1);
        Piece horse1 = new Horse(HAN, D1);
        Piece horse2 = new Horse(CHO, F1);
        Board board = new Board(HAN, Set.of(hanPawn, horse1, horse2));

        // when
        Set<Position> positions = hanPawn.possibleRoutes(board);

        // then
        assertThat(positions).containsOnly(E0, F1, D0, F0);
    }
}
