package position;

import static janggi.piece.Team.HAN;
import static janggi.route.Direction.EAST;
import static org.assertj.core.api.Assertions.assertThat;
import static janggi.position.Column.I;
import static position.PositionFixtures.A0;
import static position.PositionFixtures.B0;
import static position.PositionFixtures.D1;
import static position.PositionFixtures.E1;
import static janggi.position.Row.ZERO;

import janggi.piece.Piece;
import janggi.piece.jumpingPiece.Cannon;
import janggi.piece.normalPiece.Palace;
import janggi.position.Board;
import janggi.position.Position;
import janggi.route.Route;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import janggi.route.Direction;

public class PositionTest {

    @Test
    @DisplayName("기물의 위치는 row와 column으로 나타낼 수 있다.")
    void createPalaceTest() {
        Position position = new Position(I, ZERO);
    }

    @Test
    @DisplayName("해당 경로로 이동하는 것이 가능한지 검사할 수 있다.")
    void canMoveTest() {
        // given
        Board board = new Board(Set.of());

        // when - then
        assertThat(E1.canMove(Direction.NORTH, board)).isTrue();
    }

    @Test
    @DisplayName("좌표를 이동시킬 수 있다.")
    void moveTest() {
        assertThat(A0.move(Direction.EAST)).isEqualTo(B0);
    }

    /*
    0 ＿ ＿ ＿
    1 포 궁 *
    2 ＿ ＿ ＿
    3 d e f
    */
    @Test
    @DisplayName("기물을 뛰어넘는 것이 가능한지 검사할 수 있다.")
    void canJumpTest_1(){
        // given
        Piece cannon = new Cannon(HAN, D1);
        Piece palace = new Palace(HAN, E1);
        Board board = new Board(Set.of(cannon, palace));

        // when - then
        assertThat(D1.canJump(EAST, board)).isTrue();
    }

    /*
    0 ＿ ＿ ＿
    1 포 ＿ ＿
    2 ＿ ＿ ＿
    3 d e f
    */
    @Test
    @DisplayName("기물을 뛰어넘는 것이 가능한지 검사할 수 있다.")
    void canJumpTest_2(){
        // given
        Piece cannon = new Cannon(HAN, D1);
        Board board = new Board(Set.of(cannon));

        // when - then
        assertThat(D1.canJump(EAST, board)).isFalse();
    }

    /*
    0 ＿ ＿ ＿
    1 포 포 *
    2 ＿ ＿ ＿
    3 d e f
    */
    @Test
    @DisplayName("포는 포를 넘을 수 없다.")
    void canJumpTest_3(){
        // given
        Piece cannon1 = new Cannon(HAN, D1);
        Piece cannon2 = new Cannon(HAN, E1);
        Board board = new Board(Set.of(cannon1, cannon2));

        // when - then
        assertThat(D1.canJump(EAST, board)).isFalse();
    }
}
